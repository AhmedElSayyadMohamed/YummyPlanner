package com.example.yummyplanner.ui.details.presenter;

import com.example.yummyplanner.data.meals.model.MealItemModel;

public interface MealDetailsContract {

    interface View {
        void showLoading();
        void hideLoading();
        void noInternet();
        void showError(String message);
        void showMealDetails(MealItemModel meal);
    }

    interface Presnter {


    }
}
