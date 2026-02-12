package com.example.yummyplanner.data.meals.model.response;

import com.example.yummyplanner.data.meals.model.MealdetailsItemModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealdetailsResponse {
    @SerializedName("meals")
    private List<MealdetailsItemModel> meals;

    public List<MealdetailsItemModel> getMeals() {
        return meals;
    }

}
