package com.example.yummyplanner.data.meals.remote;

import com.example.yummyplanner.data.meals.model.Area;
import com.example.yummyplanner.data.meals.model.Category;
import com.example.yummyplanner.data.meals.model.IngredientApiItem;
import com.example.yummyplanner.data.meals.model.MealItemModel;
import com.example.yummyplanner.data.meals.model.MealdetailsItemModel;

import java.util.List;
import io.reactivex.rxjava3.core.Single;

public interface MealRemoteDataSource {

    Single<MealItemModel> getRandomMeal();

    Single<List<Category>> getCategories();

    Single<List<Area>> getAreas();

    Single<List<IngredientApiItem>> getIngredients();

    Single<List<MealItemModel>> getPopularMeals();

    Single<MealdetailsItemModel> getMealById(String id);

    Single<List<MealItemModel>> searchMealsByName(String name);

    Single<List<MealItemModel>> filterByCategory(String category);

    Single<List<MealItemModel>> filterByArea(String area);

    Single<List<MealItemModel>> filterByIngredient(String ingredient);
}
