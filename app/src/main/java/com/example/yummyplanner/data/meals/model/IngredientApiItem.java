package com.example.yummyplanner.data.meals.model;

import com.google.gson.annotations.SerializedName;

public class IngredientApiItem {

    @SerializedName("idIngredient")
    private String id;

    @SerializedName("strIngredient")
    private String name;

    @SerializedName("strDescription")
    private String description;


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnail() {
        if (name == null || name.isEmpty()) return null;
        return "https://www.themealdb.com/images/ingredients/" + name + "-Small.png";
    }
}
