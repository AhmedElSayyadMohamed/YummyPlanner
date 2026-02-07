package com.example.yummyplanner.ui.home.presenter;

import com.example.yummyplanner.data.model.Category;
import com.example.yummyplanner.data.model.Meal;

import java.util.List;

public interface HomeContract {

    interface View {
        void showLoading();
        void hideLoading();
        void showNoInternet();
        void showError(String message);
        void showMealOfTheDay(Meal meal);
        void showCategories(List<Category> categories);
        void showPopularMeals(List<Meal> meals);
    }

    interface Presenter{
        void getMealOfTheDay();
        void getCategories();
        void getPopularMeals();
        void onDestroy();
    }
}
