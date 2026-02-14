package com.example.yummyplanner.ui.search;

import com.example.yummyplanner.data.meals.model.MealItemModel;
import com.example.yummyplanner.data.meals.repository.MealRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View view;
    private final MealRepository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private List<MealItemModel> allResultsFromFilter = new ArrayList<>();
    private boolean isFilterActive = false;

    public SearchPresenter(SearchContract.View view, MealRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void attachView(SearchContract.View view) {
        this.view = view;
    }

    @Override
    public void searchByName(String query) {
        if (query == null || query.trim().isEmpty()) {
            if (isFilterActive) {
                if (view != null) view.showResults(allResultsFromFilter);
            } else {
                if (view != null) view.showResults(new ArrayList<>());
            }
            return;
        }

        if (isFilterActive) {
            filterLocally(query);
        } else {
            performRemoteSearch(query);
        }
    }

    private void filterLocally(String query) {
        List<MealItemModel> filtered = new ArrayList<>();
        String lowerQuery = query.toLowerCase().trim();
        for (MealItemModel meal : allResultsFromFilter) {
            if (meal.getName().toLowerCase().contains(lowerQuery)) {
                filtered.add(meal);
            }
        }
        if (view != null) {
            if (filtered.isEmpty()) view.showEmptyState();
            else view.showResults(filtered);
        }
    }

    private void performRemoteSearch(String query) {
        if (view != null) view.showLoading();
        disposables.add(
                repository.searchMealsByName(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meals -> {
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
    public void filterByCategory(String category) {
        isFilterActive = true;
        fetchFullMeals(repository.filterByCategory(category));
    }

    @Override
    public void filterByArea(String area) {
        isFilterActive = true;
        fetchFullMeals(repository.filterByArea(area));
    }

    @Override
    public void filterByIngredient(String ingredient) {
        isFilterActive = true;
        fetchFullMeals(repository.filterByIngredient(ingredient));
    }

    private void fetchFullMeals(Single<List<MealItemModel>> filterSource) {
        if (view != null) view.showLoading();
        
        disposables.add(
                filterSource
                        .flatMap(meals -> {
                            if (meals.isEmpty()) return Single.just(new ArrayList<MealItemModel>());
                            
                            List<Single<MealItemModel>> detailSingles = new ArrayList<>();
                            for (MealItemModel meal : meals) {
                                detailSingles.add(
                                        repository.getMealDetails(meal.getId())
                                                .map(details -> {
                                                    MealItemModel fullMeal = new MealItemModel();
                                                    fullMeal.setId(details.getId());
                                                    fullMeal.setName(details.getName());
                                                    fullMeal.setImageUrl(details.getThumbNail());
                                                    fullMeal.setCategory(details.getCategory());
                                                    fullMeal.setArea(details.getArea());
                                                    return fullMeal;
                                                })
                                );
                            }
                            
                            return Single.zip(detailSingles, args -> {
                                List<MealItemModel> fullMeals = new ArrayList<>();
                                for (Object obj : args) {
                                    fullMeals.add((MealItemModel) obj);
                                }
                                return fullMeals;
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
