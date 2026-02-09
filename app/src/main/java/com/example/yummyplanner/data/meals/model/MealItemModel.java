package com.example.yummyplanner.data.meals.model;

import com.google.gson.annotations.SerializedName;

public class MealItemModel {

    @SerializedName("idMeal")
    private String id;

    @SerializedName("strMeal")
    private String name;

    @SerializedName("strCategory")
    private String category;

    @SerializedName("strArea")
    private String area;

    @SerializedName("strMealThumb")
    private String imageUrl;


    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getArea() {
        return area;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCountryFlagUrl() {
        if (area == null) {
            return null;
        }
        return "https://flagcdn.com/w40/" + area.toLowerCase() + ".png";
    }
}
