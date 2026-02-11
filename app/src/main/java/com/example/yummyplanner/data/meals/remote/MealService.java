package com.example.yummyplanner.data.remote;

import com.example.yummyplanner.data.meals.model.MealItemModel;
import com.example.yummyplanner.data.meals.model.MealdetailsItemModel;
import com.example.yummyplanner.data.meals.model.response.AreaReposnse;
import com.example.yummyplanner.data.meals.model.response.CategoryResponse;
import com.example.yummyplanner.data.meals.model.response.IngredientResponse;
import com.example.yummyplanner.data.meals.model.response.MealsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {

    @GET("random.php")
    Call<MealsResponse<MealItemModel>> getMealOfTheDay();

    @GET("categories.php")
    Call<CategoryResponse> getCategories();

    @GET("search.php")
    Call<MealsResponse<MealItemModel>> getPopularMeals(@Query("f") String category);


    @GET("list.php?a=list")
    Call<AreaReposnse> getAreas();

    @GET("list.php?i=list")
    Call<IngredientResponse> getIngredients();

    @GET("search.php")
    Call<MealsResponse<MealItemModel>> searchMealsByName(@Query("s") String name);

    @GET("filter.php")
    Call<MealsResponse<MealItemModel>> filterByCategory(@Query("c") String category);

    @GET("filter.php")
    Call<MealsResponse<MealItemModel>> filterByArea(@Query("a") String area);

    @GET("filter.php")
    Call<MealsResponse<MealItemModel>> filterByIngredient(@Query("i") String ingredient);

    @GET("lookup.php")
    Call<MealsResponse<MealdetailsItemModel>> getMealById(@Query("i") String id);
}
