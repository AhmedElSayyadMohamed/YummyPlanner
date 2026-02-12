package com.example.yummyplanner.data.remote;

import com.example.yummyplanner.data.meals.model.response.AreaReposnse;
import com.example.yummyplanner.data.meals.model.response.CategoryResponse;
import com.example.yummyplanner.data.meals.model.response.IngredientResponse;
import com.example.yummyplanner.data.meals.model.response.MealsResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {

    @GET("random.php")
    Single<MealsResponse> getMealOfTheDay();

    @GET("categories.php")
    Single<CategoryResponse> getCategories();

    @GET("search.php")
    Single<MealsResponse> getPopularMeals(@Query("f") String category);

    @GET("list.php?a=list")
    Single<AreaReposnse> getAreas();

    @GET("list.php?i=list")
    Single<IngredientResponse> getIngredients();

    @GET("search.php")
    Single<MealsResponse> searchMealsByName(@Query("s") String name);

    @GET("filter.php")
    Single<MealsResponse> filterByCategory(@Query("c") String category);

    @GET("filter.php")
    Single<MealsResponse> filterByArea(@Query("a") String area);

    @GET("filter.php")
    Single<MealsResponse> filterByIngredient(@Query("i") String ingredient);

    @GET("lookup.php")
    Single<MealsResponse> getMealById(@Query("i") String id);
}