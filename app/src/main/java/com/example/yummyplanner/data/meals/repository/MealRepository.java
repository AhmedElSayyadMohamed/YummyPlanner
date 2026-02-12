package com.example.yummyplanner.data.repository;

import com.example.yummyplanner.data.meals.model.Area;
import com.example.yummyplanner.data.meals.model.Category;
import com.example.yummyplanner.data.meals.model.Ingredient;
import com.example.yummyplanner.data.meals.model.MealItemModel;

import java.util.List;
import io.reactivex.rxjava3.core.Single;

public interface MealRepository {

    Single<MealItemModel> getRandomMeal();

    Single<List<Category>> getCategories();

    Single<List<Area>> getAreas();

    Single<List<Ingredient>> getIngredients();

    Single<List<MealItemModel>> getPopularMeals();

    Single<MealItemModel> getMealDetails(String id);
}