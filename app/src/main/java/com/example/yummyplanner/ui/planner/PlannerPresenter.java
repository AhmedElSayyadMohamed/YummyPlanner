package com.example.yummyplanner.ui.planner;

import com.example.yummyplanner.data.meals.local.entity.PlannedMealEntity;
import com.example.yummyplanner.data.meals.repository.MealRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlannerPresenter implements PlannerContract.Presenter {

    private PlannerContract.View view;
    private final MealRepository repository;
    private final CompositeDisposable disposable = new CompositeDisposable();

    public PlannerPresenter(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public void attachView(PlannerContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
        disposable.clear();
    }

    @Override
    public void getPlannedMealsByDate(String date) {
        if (view != null) view.showLoading();

        disposable.add(
                repository.getPlannedMealsByDate(date)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meals -> {
                                    if (view != null) {
                                        view.hideLoading();
                                        view.showPlannedMeals(meals);
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
    public void deletePlannedMeal(PlannedMealEntity meal) {
        disposable.add(
                repository.deletePlannedMeal(meal)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    if (view != null) {
                                        view.showMealDeleted();
                                    }
                                },
                                throwable -> {
                                    if (view != null) {
                                        view.showError(throwable.getMessage());
                                    }
                                }
                        )
        );
    }
}
