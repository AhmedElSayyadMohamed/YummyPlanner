package com.example.yummyplanner.ui.search;

import com.example.yummyplanner.data.meals.model.MealItemModel;
import com.example.yummyplanner.data.meals.repository.MealRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View view;
    private final MealRepository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private List<MealItemModel> allResultsFromFilter = new ArrayList<>();
    private boolean isFilterActive = false;
    private final PublishSubject<String> searchSubject = PublishSubject.create();
    private SearchContract.SearchType currentSearchType = SearchContract.SearchType.CATEGORY;

    public SearchPresenter(SearchContract.View view, MealRepository repository) {
        this.view = view;
        this.repository = repository;
        setupSearchDebounce();
    }

    private void setupSearchDebounce() {
        disposables.add(
                searchSubject
                        .debounce(500, TimeUnit.MILLISECONDS)
                        .distinctUntilChanged()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::performSearch)
        );
    }

    @Override
    public void attachView(SearchContract.View view) {
        this.view = view;
    }

    @Override
    public void onSearchQueryChanged(String query, SearchContract.SearchType type) {
        this.currentSearchType = type;
        if (query == null || query.trim().isEmpty()) {
            loadDefaultData();
        } else {
            searchSubject.onNext(query.trim());
        }
    }

    @Override
    public void onFilterTypeChanged(SearchContract.SearchType type, String currentQuery) {
        this.currentSearchType = type;
        if (type == SearchContract.SearchType.NAME) {
            isFilterActive = false;
            allResultsFromFilter.clear();
        }
        if (currentQuery != null && !currentQuery.trim().isEmpty()) {
            performSearch(currentQuery.trim());
        } else {
            loadDefaultData();
        }
    }

    private void loadDefaultData() {
        switch (currentSearchType) {
            case CATEGORY:
                filterByCategory("Seafood");
                break;
            case AREA:
                filterByArea("Egyptian");
                break;
            case INGREDIENT:
                filterByIngredient("Chicken");
                break;
            case NAME:
                isFilterActive = false;
                searchByName("a");
                break;
        }
    }

    private void performSearch(String query) {
        switch (currentSearchType) {
            case CATEGORY:
                filterByCategory(query);
                break;
            case AREA:
                filterByArea(query);
                break;
            case INGREDIENT:
                filterByIngredient(query);
                break;
            case NAME:
                searchByName(query);
                break;
        }
    }

    private void searchByName(String query) {
        if (view != null) view.showLoading();
        disposables.add(
                repository.searchMealsByName(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meals -> {
                                    if (view != null) {
                                        view.hideLoading();
                                        if (meals == null || meals.isEmpty()) view.showEmptyState();
                                        else view.showResults(meals);
                                    }
                                },
                                throwable -> {
                                    if (view != null) {
                                        view.hideLoading();
                                        view.showError(throwable.getMessage());
                                    }
                                }
                        )
        );
    }

    private void filterByCategory(String category) {
        isFilterActive = true;
        fetchFullMeals(repository.filterByCategory(category));
    }

    private void filterByArea(String area) {
        isFilterActive = true;
        fetchFullMeals(repository.filterByArea(area));
    }

    private void filterByIngredient(String ingredient) {
        isFilterActive = true;
        fetchFullMeals(repository.filterByIngredient(ingredient));
    }

    private void fetchFullMeals(Single<List<MealItemModel>> filterSource) {
        if (view != null) view.showLoading();
        allResultsFromFilter.clear();
        disposables.add(
                filterSource
                        .flatMap(meals -> {
                            if (meals == null || meals.isEmpty()) return Single.just(new ArrayList<MealItemModel>());
                            List<Single<MealItemModel>> detailSingles = new ArrayList<>();
                            for (MealItemModel meal : meals) {
                                detailSingles.add(repository.getMealDetails(meal.getId())
                                        .map(details -> {
                                            MealItemModel m = new MealItemModel();
                                            m.setId(details.getId());
                                            m.setName(details.getName());
                                            m.setImageUrl(details.getThumbNail());
                                            m.setCategory(details.getCategory());
                                            m.setArea(details.getArea());
                                            return m;
                                        }));
                            }
                            return Single.zip(detailSingles, args -> {
                                List<MealItemModel> list = new ArrayList<>();
                                for (Object o : args) list.add((MealItemModel) o);
                                return list;
                            });
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meals -> {
                                    allResultsFromFilter = meals;
                                    if (view != null) {
                                        view.hideLoading();
                                        if (meals.isEmpty()) view.showEmptyState();
                                        else view.showResults(meals);
                                    }
                                },
                                throwable -> {
                                    if (view != null) {
                                        view.hideLoading();
                                        view.showError(throwable.getMessage());
                                    }
                                }
                        )
        );
    }

    @Override
    public void detachView() {
        disposables.clear();
        view = null;
    }
}
