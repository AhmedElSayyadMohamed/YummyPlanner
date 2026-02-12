package com.example.yummyplanner.data.remote;

import com.example.yummyplanner.data.meals.model.Area;
import com.example.yummyplanner.data.meals.model.Category;
import com.example.yummyplanner.data.meals.model.Ingredient;
import com.example.yummyplanner.data.meals.model.MealItemModel;

import java.util.List;
import io.reactivex.rxjava3.core.Single;

public interface MealRemoteDataSource {

    Single<MealItemModel> getRandomMeal();

    Single<List<Category>> getCategories();

    Single<List<Area>> getAreas();

    Single<List<Ingredient>> getIngredients();

    Single<List<MealItemModel>> getPopularMeals();

    Single<MealItemModel> getMealById(String id);
}