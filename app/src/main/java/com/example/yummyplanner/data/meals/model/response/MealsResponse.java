package com.example.yummyplanner.data.meals.model.response;

import com.example.yummyplanner.data.meals.model.MealItemModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealsResponse {

    @SerializedName("meals")
    private List<MealItemModel> meals;

    public List<MealItemModel> getMeals() {
        return meals;
    }
}