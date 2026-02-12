package com.example.yummyplanner.ui.home.presenter;

import com.example.yummyplanner.data.meals.model.Area;
import com.example.yummyplanner.data.meals.model.Category;
import com.example.yummyplanner.data.meals.model.MealItemModel;

import java.util.List;

public interface HomeContract {


    interface View {
        void showLoading();
        void hideLoading();
        void showError(String message);

        void showMealOfTheDay(MealItemModel meal);
        void showCategories(List<Category> categories);
        void showPopularMeals(List<MealItemModel> meals);
        void showCuisines(List<Area> areas);

        void navigateToMealDetails(MealItemModel meal);
        void navigateToMealsList(String filter, String type);
        void setUserName(String name);

    }

    interface Presenter {
        void attachView(View view);
        void detachView();

        void loadHomeData();
        void onMealOfTheDayClicked(MealItemModel meal);
        void onCategoryClicked(String category);
        void onCuisineClicked(String area);
        void onPopularMealClicked(MealItemModel meal);
    }
}
