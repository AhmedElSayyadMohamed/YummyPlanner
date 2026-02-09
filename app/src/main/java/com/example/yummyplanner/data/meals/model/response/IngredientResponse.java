package com.example.yummyplanner.data.meals.model.response;

import com.example.yummyplanner.data.meals.model.Ingredient;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientResponse {
    @SerializedName("meals")
    private List<Ingredient> ingredients;

    public List<Ingredient> getIngredients(){

        return ingredients;
    }
}
