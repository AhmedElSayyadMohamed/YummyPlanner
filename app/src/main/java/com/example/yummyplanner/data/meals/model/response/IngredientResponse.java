package com.example.yummyplanner.data.meals.model.response;

import com.example.yummyplanner.data.meals.model.IngredientApiItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientResponse {
    @SerializedName("meals")
    private List<IngredientApiItem> ingredients;

    public List<IngredientApiItem> getIngredients(){

        return ingredients;
    }
}
