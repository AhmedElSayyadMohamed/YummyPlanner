package com.example.yummyplanner.data.model;

import com.google.gson.annotations.SerializedName;

public class Meal {

    @SerializedName("idMeal")
    private String id;

    @SerializedName("strMeal")
    private String name;

    @SerializedName("strCategory")
    private String category;

    @SerializedName("strArea")
    private String area;

    @SerializedName("strInstructions")
    private String instructions;

    @SerializedName("strMealThumb")
    private String imageUrl;

    @SerializedName("strYoutube")
    private String youtubeUrl;


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

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
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

    public String getInstructions() {
        return instructions;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public String getCountryFlagUrl() {
        if (area == null) {
            return null;
        }
        return "https://flagcdn.com/w40/" + area.toLowerCase() + ".png";
    }
}
