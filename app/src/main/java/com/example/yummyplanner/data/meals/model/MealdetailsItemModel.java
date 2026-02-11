package com.example.yummyplanner.data.meals.model;

import android.net.Uri;

import com.example.yummyplanner.data.meals.model.response.Ingredient;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MealdetailsItemModel {

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
    private String thumbNail;

    @SerializedName("strYoutube")
    private String youtubeUrl;

    @SerializedName("strIngredient1")
    private String ingredient1;
    @SerializedName("strIngredient2")
    private String ingredient2;
    @SerializedName("strIngredient3")
    private String ingredient3;
    @SerializedName("strIngredient4")
    private String ingredient4;
    @SerializedName("strIngredient5")
    private String ingredient5;
    @SerializedName("strIngredient6")
    private String ingredient6;
    @SerializedName("strIngredient7")
    private String ingredient7;
    @SerializedName("strIngredient8")
    private String ingredient8;
    @SerializedName("strIngredient9")
    private String ingredient9;
    @SerializedName("strIngredient10")
    private String ingredient10;
    @SerializedName("strIngredient11")
    private String ingredient11;
    @SerializedName("strIngredient12")
    private String ingredient12;
    @SerializedName("strIngredient13")
    private String ingredient13;
    @SerializedName("strIngredient14")
    private String ingredient14;
    @SerializedName("strIngredient15")
    private String ingredient15;
    @SerializedName("strIngredient16")
    private String ingredient16;
    @SerializedName("strIngredient17")
    private String ingredient17;
    @SerializedName("strIngredient18")
    private String ingredient18;
    @SerializedName("strIngredient19")
    private String ingredient19;
    @SerializedName("strIngredient20")
    private String ingredient20;

    @SerializedName("strMeasure1")
    private String measure1;
    @SerializedName("strMeasure2")
    private String measure2;
    @SerializedName("strMeasure3")
    private String measure3;
    @SerializedName("strMeasure4")
    private String measure4;
    @SerializedName("strMeasure5")
    private String measure5;
    @SerializedName("strMeasure6")
    private String measure6;
    @SerializedName("strMeasure7")
    private String measure7;
    @SerializedName("strMeasure8")
    private String measure8;
    @SerializedName("strMeasure9")
    private String measure9;
    @SerializedName("strMeasure10")
    private String measure10;
    @SerializedName("strMeasure11")
    private String measure11;
    @SerializedName("strMeasure12")
    private String measure12;
    @SerializedName("strMeasure13")
    private String measure13;
    @SerializedName("strMeasure14")
    private String measure14;
    @SerializedName("strMeasure15")
    private String measure15;
    @SerializedName("strMeasure16")
    private String measure16;
    @SerializedName("strMeasure17")
    private String measure17;
    @SerializedName("strMeasure18")
    private String measure18;
    @SerializedName("strMeasure19")
    private String measure19;
    @SerializedName("strMeasure20")
    private String measure20;

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

    public String getThumbNail() {
        return thumbNail;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public String getVideoId() {
        return Uri.parse(youtubeUrl).getQueryParameter("v");
    }
    public List<Ingredient> getIngredientsList() {
        List<Ingredient> ingredients = new ArrayList<>();

        if (ingredient1 != null && !ingredient1.isEmpty())
            ingredients.add(new Ingredient(ingredient1, measure1));
        if (ingredient2 != null && !ingredient2.isEmpty())
            ingredients.add(new Ingredient(ingredient2, measure2));
        if (ingredient3 != null && !ingredient3.isEmpty())
            ingredients.add(new Ingredient(ingredient3, measure3));
        if (ingredient4 != null && !ingredient4.isEmpty())
            ingredients.add(new Ingredient(ingredient4, measure4));
        if (ingredient5 != null && !ingredient5.isEmpty())
            ingredients.add(new Ingredient(ingredient5, measure5));
        if (ingredient6 != null && !ingredient6.isEmpty())
            ingredients.add(new Ingredient(ingredient6, measure6));
        if (ingredient7 != null && !ingredient7.isEmpty())
            ingredients.add(new Ingredient(ingredient7, measure7));
        if (ingredient8 != null && !ingredient8.isEmpty())
            ingredients.add(new Ingredient(ingredient8, measure8));
        if (ingredient9 != null && !ingredient9.isEmpty())
            ingredients.add(new Ingredient(ingredient9, measure9));
        if (ingredient10 != null && !ingredient10.isEmpty())
            ingredients.add(new Ingredient(ingredient10, measure10));
        if (ingredient11 != null && !ingredient11.isEmpty())
            ingredients.add(new Ingredient(ingredient11, measure11));
        if (ingredient12 != null && !ingredient12.isEmpty())
            ingredients.add(new Ingredient(ingredient12, measure12));
        if (ingredient13 != null && !ingredient13.isEmpty())
            ingredients.add(new Ingredient(ingredient13, measure13));
        if (ingredient14 != null && !ingredient14.isEmpty())
            ingredients.add(new Ingredient(ingredient14, measure14));
        if (ingredient15 != null && !ingredient15.isEmpty())
            ingredients.add(new Ingredient(ingredient15, measure15));
        if (ingredient16 != null && !ingredient16.isEmpty())
            ingredients.add(new Ingredient(ingredient16, measure16));
        if (ingredient17 != null && !ingredient17.isEmpty())
            ingredients.add(new Ingredient(ingredient17, measure17));
        if (ingredient18 != null && !ingredient18.isEmpty())
            ingredients.add(new Ingredient(ingredient18, measure18));
        if (ingredient19 != null && !ingredient19.isEmpty())
            ingredients.add(new Ingredient(ingredient19, measure19));
        if (ingredient20 != null && !ingredient20.isEmpty())
            ingredients.add(new Ingredient(ingredient20, measure20));

        return ingredients;
    }
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
    public String getCountryFlagUrl() {
        if (area == null || area.isEmpty()) return null;
        String code = AREA_TO_COUNTRY_CODE.getOrDefault(area, "un");
        return "https://flagcdn.com/w40/" + code + ".png";
    }
}
