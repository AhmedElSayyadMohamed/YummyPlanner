package com.example.yummyplanner.ui.home.presenter;

import com.example.yummyplanner.data.model.Meal;
import com.example.yummyplanner.data.model.response.MealOfTheDayCallback;
import com.example.yummyplanner.data.repository.MealRepository;
import com.example.yummyplanner.data.repository.MealRepositoryImpl;

public class HomePresenter implements HomeContract.Presenter, MealOfTheDayCallback {

    private HomeContract.View view;
    private final MealRepository mealRepository;
    private Meal cachedMeal;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
        this.mealRepository = MealRepositoryImpl.getInstance();
    }

    @Override
    public void getMealOfTheDay() {
        if (cachedMeal != null) {
            if (view != null) {
                view.showMealOfTheDay(cachedMeal);
            }
            return;
        }

        if (view != null) {
            view.showLoading();
        }

        mealRepository.getMealOfTheDay(this);
    }

    @Override
    public void getCategories() {
        mealRepository.getCategories(
                categories -> {
                    if (view != null) {
                        view.showCategories(categories);
                    }
                },
                error -> {
                    if (view != null) {
                        view.showError(error);
                    }
                }
        );
    }

    @Override
    public void getPopularMeals() {
        mealRepository.getPopularMeals(
                meals -> {
                    if (view != null) {
                        view.showPopularMeals(meals);
                    }
                },
                error -> {
                    if (view != null) {
                        view.showError(error);
                    }
                }
        );
    }

    @Override
    public void onSuccess(Meal meal) {
        cachedMeal = meal;
        if (view != null) {
            view.hideLoading();
            view.showMealOfTheDay(meal);
        }
    }

    @Override
    public void noInternet() {
        if (view != null) {
            view.hideLoading();
            view.showNoInternet();
        }
    }

    @Override
    public void onFailure(String message) {
        if (view != null) {
            view.hideLoading();
            view.showError(message);
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
