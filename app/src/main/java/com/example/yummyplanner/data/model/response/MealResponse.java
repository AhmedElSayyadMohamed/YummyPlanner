package com.example.yummyplanner.data.model.response;

import com.example.yummyplanner.data.model.Meal;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealResponse {

    @SerializedName("meals")
    private List<Meal> meals;

    public List<Meal> getMeals() {
        return meals;
    }
}