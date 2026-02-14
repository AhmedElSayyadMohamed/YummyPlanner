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
        void attachView(View view);
        void onSearchQueryChanged(String query, SearchType type);
        void onFilterTypeChanged(SearchType type, String currentQuery);
        void detachView();
    }

    enum SearchType {
        NAME, CATEGORY, AREA, INGREDIENT
    }
}
