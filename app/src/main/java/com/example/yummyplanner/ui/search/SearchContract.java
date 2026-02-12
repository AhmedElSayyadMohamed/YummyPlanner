package com.example.yummyplanner.ui.search;

import com.example.yummyplanner.data.meals.model.MealItemModel;
import java.util.List;

public interface SearchContract {
    interface View {
        void showResults(List<MealItemModel> meals);
        void showError(String message);
        void showLoading();
        void hideLoading();
        void showEmptyState();
    }

    interface Presenter {
        void searchByName(String name);
        void filterByCategory(String category);
        void filterByArea(String area);
        void filterByIngredient(String ingredient);
        void detachView();
    }
}
