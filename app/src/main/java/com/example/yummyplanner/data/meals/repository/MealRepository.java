package com.example.yummyplanner.data.repository;

import com.example.yummyplanner.data.meals.local.entity.FavouriteMealEntity;
import com.example.yummyplanner.data.meals.model.Area;
import com.example.yummyplanner.data.meals.model.Category;
import com.example.yummyplanner.data.meals.model.IngredientApiItem;
import com.example.yummyplanner.data.meals.model.MealItemModel;
import com.example.yummyplanner.data.meals.model.MealdetailsItemModel;
import com.example.yummyplanner.data.meals.repository.MealsDataCallback;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealRepository {

    void getRandomMeal(MealsDataCallback<MealItemModel> callback);

    void getCategories(MealsDataCallback<List<Category>> callback);

    void getAreas(MealsDataCallback<List<Area>> callback);

    void getIngredients(MealsDataCallback<List<IngredientApiItem>> callback);

    void getPopularMeals(MealsDataCallback<List<MealItemModel>> callback);

    void getMeadDetails(String id, MealsDataCallback<MealdetailsItemModel> callback);


    // fav

    Flowable<List<FavouriteMealEntity>> getAllFavorites();

    Completable insertFavorite(FavouriteMealEntity meal);

    Completable deleteFavorite(FavouriteMealEntity meal);

    Completable deleteFavoriteById(String mealId);

    Single<Boolean> isFavorite(String mealId);

    Single<FavouriteMealEntity> getFavoriteById(String mealId);
}
