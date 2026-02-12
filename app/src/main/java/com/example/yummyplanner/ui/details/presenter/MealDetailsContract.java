package com.example.yummyplanner.ui.details.presenter;

import com.example.yummyplanner.data.meals.model.MealdetailsItemModel;
import com.example.yummyplanner.data.meals.model.response.Ingredient;

import java.util.List;

public interface MealDetailsContract {

    interface View {

        // UI States
        void showLoading();
        void hideLoading();
        void showError(String message);

        // Meal Data
        void showMealDetails(MealdetailsItemModel meal);
        void showIngredients(List<Ingredient> ingredients);
        void showInstructions(String instructions);

        // Favorite
        void updateFavoriteState(boolean isFavorite);
        void showFavoriteAdded();
        void showFavoriteRemoved();

        // Meal Planner
        void showMealAddedToPlanner(String date);
    }

    interface Presenter {

        void attachView(View view);
        void detachView();

        void getMealDetails(String mealId);

        void onFavoriteClicked(MealdetailsItemModel meal);

        void onAddToPlannerClicked(MealdetailsItemModel meal, String date);
    }
}
