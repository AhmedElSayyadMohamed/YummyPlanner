package com.example.yummyplanner.data.meals.repository;

import com.example.yummyplanner.data.meals.local.entity.FavouriteMealEntity;
import com.example.yummyplanner.data.meals.model.Area;
import com.example.yummyplanner.data.meals.model.Category;
import com.example.yummyplanner.data.meals.model.Ingredient;
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



    // fav

    Flowable<List<FavouriteMealEntity>> getAllFavorites();

    Completable insertFavorite(FavouriteMealEntity meal);

    Completable deleteFavorite(FavouriteMealEntity meal);

    Completable deleteFavoriteById(String mealId);

    Single<Boolean> isFavorite(String mealId);

    Single<FavouriteMealEntity> getFavoriteById(String mealId);
}
