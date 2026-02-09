package com.example.yummyplanner.data.meals.model.response;

import com.example.yummyplanner.data.meals.model.Category;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryResponse {

    @SerializedName("categories")
    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }
}
