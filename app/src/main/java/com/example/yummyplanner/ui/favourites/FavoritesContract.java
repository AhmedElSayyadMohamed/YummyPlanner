package com.example.yummyplanner.ui.favourites;

import com.example.yummyplanner.data.meals.model.MealdetailsItemModel;

import java.util.List;

public interface FavoritesContract {

    interface View {
        void showLoading();
        void hideLoading();
        void displayFavorites(List<MealdetailsItemModel> favorites);
        void showEmptyState();
        void showError(String message);
    }

    interface Presenter {
        void getFavorites();
        void deleteFromFavorites(MealdetailsItemModel meal);
    }
}