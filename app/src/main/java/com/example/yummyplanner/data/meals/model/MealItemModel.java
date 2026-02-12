package com.example.yummyplanner.data.meals.model;

import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Map;

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

    private static final Map<String, String> AREA_TO_COUNTRY_CODE = new HashMap<>();
    static {
        AREA_TO_COUNTRY_CODE.put("American", "us");
        AREA_TO_COUNTRY_CODE.put("British", "gb");
        AREA_TO_COUNTRY_CODE.put("Canadian", "ca");
        AREA_TO_COUNTRY_CODE.put("Chinese", "cn");
        AREA_TO_COUNTRY_CODE.put("Dutch", "nl");
        AREA_TO_COUNTRY_CODE.put("Egyptian", "eg");
        AREA_TO_COUNTRY_CODE.put("French", "fr");
        AREA_TO_COUNTRY_CODE.put("Greek", "gr");
        AREA_TO_COUNTRY_CODE.put("Indian", "in");
        AREA_TO_COUNTRY_CODE.put("Irish", "ie");
        AREA_TO_COUNTRY_CODE.put("Italian", "it");
        AREA_TO_COUNTRY_CODE.put("Jamaican", "jm");
        AREA_TO_COUNTRY_CODE.put("Japanese", "jp");
        AREA_TO_COUNTRY_CODE.put("Kenyan", "ke");
        AREA_TO_COUNTRY_CODE.put("Malaysian", "my");
        AREA_TO_COUNTRY_CODE.put("Mexican", "mx");
        AREA_TO_COUNTRY_CODE.put("Moroccan", "ma");
        AREA_TO_COUNTRY_CODE.put("Russian", "ru");
        AREA_TO_COUNTRY_CODE.put("Spanish", "es");
        AREA_TO_COUNTRY_CODE.put("Thai", "th");
        AREA_TO_COUNTRY_CODE.put("Tunisian", "tn");
        AREA_TO_COUNTRY_CODE.put("Turkish", "tr");
        AREA_TO_COUNTRY_CODE.put("Unknown", "un");
    }
    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getArea() { return area; }
    public String getImageUrl() { return imageUrl; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setArea(String area) { this.area = area; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getCountryFlagUrl() {
        if (area == null || area.isEmpty()) return null;
        String code = AREA_TO_COUNTRY_CODE.getOrDefault(area, "un");
        return "https://flagcdn.com/w40/" + code + ".png";
    }
}
