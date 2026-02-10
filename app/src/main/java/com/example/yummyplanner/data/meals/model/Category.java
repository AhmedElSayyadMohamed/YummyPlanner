package com.example.yummyplanner.data.meals.model;

import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("idCategory")
    private String idCategory;

    @SerializedName("strCategory")
    private String name;

    @SerializedName("strCategoryThumb")
    private String thumbnail;

    @SerializedName("strCategoryDescription")
    private String description;

    public String getIdCategory() {
        return idCategory;
    }

    public String getName() {
        return name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getDescription() {
        return description;
    }
}
