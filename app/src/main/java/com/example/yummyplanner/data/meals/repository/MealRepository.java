package com.example.yummyplanner.data.repository;

import com.example.yummyplanner.data.meals.model.Area;
import com.example.yummyplanner.data.meals.model.Category;
import com.example.yummyplanner.data.meals.model.Ingredient;
import com.example.yummyplanner.data.meals.model.MealItemModel;
import com.example.yummyplanner.data.meals.repository.MealsDataCallback;

import java.util.List;

public interface MealRepository {

    void getRandomMeal(MealsDataCallback<MealItemModel> callback);

    void getCategories(MealsDataCallback<List<Category>> callback);

    void getAreas(MealsDataCallback<List<Area>> callback);

    void getIngredients(MealsDataCallback<List<Ingredient>> callback);

    void getPopularMeals(MealsDataCallback<List<MealItemModel>> callback);
}
