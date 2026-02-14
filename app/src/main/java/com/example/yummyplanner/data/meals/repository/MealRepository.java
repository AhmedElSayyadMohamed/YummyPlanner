package com.example.yummyplanner.data.meals.repository;

import com.example.yummyplanner.data.meals.local.entity.FavouriteMealEntity;
import com.example.yummyplanner.data.meals.local.entity.PlannedMealEntity;
import com.example.yummyplanner.data.meals.model.Area;
import com.example.yummyplanner.data.meals.model.Category;
import com.example.yummyplanner.data.meals.model.IngredientApiItem;
import com.example.yummyplanner.data.meals.model.MealItemModel;
import com.example.yummyplanner.data.meals.model.MealdetailsItemModel;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealRepository {
    Single<MealItemModel> getRandomMeal();
    Single<List<Category>> getCategories();
    Single<List<Area>> getAreas();
    Single<List<IngredientApiItem>> getIngredients();
    Single<List<MealItemModel>> getPopularMeals();
    Single<MealdetailsItemModel> getMealDetails(String id);

    Single<List<MealItemModel>> searchMealsByName(String name);
    Single<List<MealItemModel>> filterByCategory(String category);
    Single<List<MealItemModel>> filterByArea(String area);
    Single<List<MealItemModel>> filterByIngredient(String ingredient);

    // fav
    Completable insertFavorite(FavouriteMealEntity meal);
    Flowable<List<FavouriteMealEntity>> getAllFavorites();
    Completable deleteFavoriteById(String mealId);
    Single<Boolean> isFavorite(String mealId);

    // Planner
    Completable insertPlannedMeal(PlannedMealEntity meal);
    Flowable<List<PlannedMealEntity>> getPlannedMealsByDate(String date);
    Flowable<List<PlannedMealEntity>> getAllPlannedMeals();
    Completable deletePlannedMeal(PlannedMealEntity meal);

    Completable clearAllData();
    Completable syncDataFromCloud(String UId);
}
