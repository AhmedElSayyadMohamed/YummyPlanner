package com.example.yummyplanner.data.meals.local;

import com.example.yummyplanner.data.meals.local.entity.FavouriteMealEntity;
import com.example.yummyplanner.data.meals.local.entity.PlannedMealEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealLocalDataSource {

    Flowable<List<FavouriteMealEntity>> getAllFavorites();
    Completable insertFavorite(FavouriteMealEntity meal);
    Completable deleteFavorite(FavouriteMealEntity meal);

    Completable deleteFavoriteById(String mealId);
    Single<Boolean> isFavorite(String mealId);
    Single<FavouriteMealEntity> getFavoriteById(String mealId);

    Flowable<List<PlannedMealEntity>> getPlannedMealsByDate(String date);
    Flowable<List<PlannedMealEntity>> getAllPlannedMeals();
    Completable insertPlannedMeal(PlannedMealEntity meal);
    Completable deletePlannedMeal(PlannedMealEntity meal);

    Completable insertAllFavorites(List<FavouriteMealEntity> meals);
    Completable insertAllPlannedMeals(List<PlannedMealEntity> meals);
    Completable deleteAllFavorites();
    Completable deleteAllPlannedMeals();
}
