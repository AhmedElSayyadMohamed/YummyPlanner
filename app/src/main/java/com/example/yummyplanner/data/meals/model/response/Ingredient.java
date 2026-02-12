package com.example.yummyplanner.data.meals.model.response;

public class Ingredient {
    private String name;
    private String measure;

    public Ingredient(String name, String measure) {
        this.name = name;
        this.measure = measure;
    }

    public String getName() { return name; }
    public String getMeasure() { return measure; }

    public String getQuantity() {
        return measure;
    }

    public String getThumbnail() {
        if (name == null || name.isEmpty()) return null;
        return "https://www.themealdb.com/images/ingredients/" + name + "-Small.png";
    }

    public String getDisplayText() {
        return (measure != null ? measure : "") + " " + (name != null ? name : "");
    }
}
