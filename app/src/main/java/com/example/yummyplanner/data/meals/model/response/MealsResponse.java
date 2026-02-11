package com.example.yummyplanner.data.meals.model.response;

import com.example.yummyplanner.data.meals.model.MealItemModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealsResponse <T>{

    @SerializedName("meals")
    private List<T> meals;

    public List<T> getMeals() {
        return meals;
    }
}