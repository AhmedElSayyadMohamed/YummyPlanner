package com.example.yummyplanner.ui.planner;

import com.example.yummyplanner.data.meals.local.entity.PlannedMealEntity;

import java.util.List;

public interface PlannerContract {

    interface View {
        void showPlannedMeals(List<PlannedMealEntity> meals);
        void showLoading();
        void hideLoading();
        void showError(String message);
        void showMealDeleted();
    }

    interface Presenter {
        void attachView(View view);
        void detachView();
        void getPlannedMealsByDate(String date);
        void deletePlannedMeal(PlannedMealEntity meal);
    }
}
