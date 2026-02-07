package com.example.yummyplanner.data.remote;

import com.example.yummyplanner.data.model.response.CategoryResponse;
import com.example.yummyplanner.data.model.response.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {

    @GET("random.php")
    Call<MealResponse> getMealOfTheDay();

    @GET("categories.php")
    Call<CategoryResponse> getCategories();

    @GET("filter.php")
    Call<MealResponse> getPopularMeals(@Query("c") String category);
}
