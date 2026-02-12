package com.example.yummyplanner.data.meals.remote;

import com.example.yummyplanner.data.meals.model.MealItemModel;
import com.example.yummyplanner.data.meals.model.response.AreaReposnse;
import com.example.yummyplanner.data.meals.model.response.CategoryResponse;
import com.example.yummyplanner.data.meals.model.response.IngredientResponse;
import com.example.yummyplanner.data.meals.model.response.MealsResponse;
import com.example.yummyplanner.data.meals.model.response.MealdetailsResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {

    @GET("random.php")
    Single<MealsResponse<MealItemModel>> getMealOfTheDay();

    @GET("categories.php")
    Single<CategoryResponse> getCategories();

    @GET("search.php")
    Single<MealsResponse<MealItemModel>> getPopularMeals(@Query("f") String category);

    @GET("list.php?a=list")
    Single<AreaReposnse> getAreas();

    @GET("list.php?i=list")
    Single<IngredientResponse> getIngredients();

    @GET("search.php")
    Single<MealsResponse<MealItemModel>> searchMealsByName(@Query("s") String name);

    @GET("filter.php")
    Single<MealsResponse<MealItemModel>> filterByCategory(@Query("c") String category);

    @GET("filter.php")
    Single<MealsResponse<MealItemModel>> filterByArea(@Query("a") String area);

    @GET("filter.php")
    Single<MealsResponse<MealItemModel>> filterByIngredient(@Query("i") String ingredient);

    @GET("lookup.php")
    Single<MealdetailsResponse> getMealById(@Query("i") String id);
}
