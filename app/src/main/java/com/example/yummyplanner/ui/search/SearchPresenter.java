package com.example.yummyplanner.ui.search;

import com.example.yummyplanner.data.meals.model.MealItemModel;
import com.example.yummyplanner.data.meals.repository.MealRepository;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View view;
    private final MealRepository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public SearchPresenter(SearchContract.View view, MealRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void searchByName(String name) {
        if (name.isEmpty()) {
            view.showEmptyState();
            return;
        }
        view.showLoading();
        disposables.add(
                repository.searchMealsByName(name)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meals -> {
                                    view.hideLoading();
                                    if (meals.isEmpty()) {
                                        view.showEmptyState();
                                    } else {
                                        view.showResults(meals);
                                    }
                                },
                                throwable -> {
                                    view.hideLoading();
                                    view.showError(throwable.getMessage());
                                }
                        )
        );
    }

    @Override
    public void filterByCategory(String category) {
        view.showLoading();
        disposables.add(
                repository.filterByCategory(category)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meals -> {
                                    view.hideLoading();
                                    handleResults(meals);
                                },
                                throwable -> {
                                    view.hideLoading();
                                    view.showError(throwable.getMessage());
                                }
                        )
        );
    }

    @Override
    public void filterByArea(String area) {
        view.showLoading();
        disposables.add(
                repository.filterByArea(area)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meals -> {
                                    view.hideLoading();
                                    handleResults(meals);
                                },
                                throwable -> {
                                    view.hideLoading();
                                    view.showError(throwable.getMessage());
                                }
                        )
        );
    }

    @Override
    public void filterByIngredient(String ingredient) {
        view.showLoading();
        disposables.add(
                repository.filterByIngredient(ingredient)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meals -> {
                                    view.hideLoading();
                                    handleResults(meals);
                                },
                                throwable -> {
                                    view.hideLoading();
                                    view.showError(throwable.getMessage());
                                }
                        )
        );
    }

    private void handleResults(List<MealItemModel> meals) {
        if (meals.isEmpty()) {
            view.showEmptyState();
        } else {
            List<MealItemModel> limitedList = meals.stream()
                    .limit(10)
                    .collect(Collectors.toList());
            view.showResults(limitedList);
        }
    }

    @Override
    public void detachView() {
        disposables.clear();
        view = null;
    }
}
