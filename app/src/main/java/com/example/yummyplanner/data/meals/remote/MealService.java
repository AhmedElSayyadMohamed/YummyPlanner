package com.example.yummyplanner.data.remote;

import com.example.yummyplanner.data.meals.model.response.AreaReposnse;
import com.example.yummyplanner.data.meals.model.response.CategoryResponse;
import com.example.yummyplanner.data.meals.model.response.IngredientResponse;
import com.example.yummyplanner.data.meals.model.response.MealsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {

    @GET("random.php")
    Call<MealsResponse> getMealOfTheDay();

    @GET("categories.php")
    Call<CategoryResponse> getCategories();

    @GET("search.php")
    Call<MealsResponse> getPopularMeals(@Query("f") String category);


    @GET("list.php?a=list")
    Call<AreaReposnse> getAreas();

    @GET("list.php?i=list")
    Call<IngredientResponse> getIngredients();

    @GET("search.php")
    Call<MealsResponse> searchMealsByName(@Query("s") String name);

    @GET("filter.php")
    Call<MealsResponse> filterByCategory(@Query("c") String category);

    @GET("filter.php")
    Call<MealsResponse> filterByArea(@Query("a") String area);

    @GET("filter.php")
    Call<MealsResponse> filterByIngredient(@Query("i") String ingredient);

    @GET("lookup.php")
    Call<MealsResponse> getMealById(@Query("i") String id);
}
