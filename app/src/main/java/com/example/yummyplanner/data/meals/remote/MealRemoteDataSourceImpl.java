package com.example.yummyplanner.data.remote;


import android.util.Log;

import com.example.yummyplanner.data.meals.model.Area;
import com.example.yummyplanner.data.meals.model.Category;
import com.example.yummyplanner.data.meals.model.Ingredient;
import com.example.yummyplanner.data.meals.model.MealItemModel;
import com.example.yummyplanner.data.meals.model.response.AreaReposnse;
import com.example.yummyplanner.data.meals.model.response.CategoryResponse;
import com.example.yummyplanner.data.meals.model.response.IngredientResponse;
import com.example.yummyplanner.data.meals.model.response.MealsResponse;
import com.example.yummyplanner.data.meals.repository.MealsDataCallback;
import com.example.yummyplanner.data.network.Network;
import com.example.yummyplanner.data.remote.MealService;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealRemoteDataSourceImpl implements com.example.yummyplanner.data.remote.MealRemoteDataSource {

    private final MealService mealService;
    private static MealRemoteDataSourceImpl instance;

    private MealRemoteDataSourceImpl() {
        this.mealService = Network.getInstance().create(MealService.class);
    }

    public static synchronized MealRemoteDataSourceImpl getInstance(){
        if(instance == null){
            instance = new MealRemoteDataSourceImpl();
        }
        return  instance;
    }
    @Override
    public void getRandomMeal(MealsDataCallback<MealItemModel> callback) {
        mealService.getMealOfTheDay().enqueue(

                new Callback<MealsResponse>() {

                    @Override
                    public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            List<MealItemModel> meals = response.body().getMeals();
                            if (meals != null && !meals.isEmpty()) {
                                callback.onSuccess(meals.get(0));
                            } else {
                                callback.onFailure("There is No Meal To Day Go Away");
                            }
                        } else {
                            callback.onFailure("Server Error When try To Get Meal of The Day");
                        }
                    }

                    @Override
                    public void onFailure(Call<MealsResponse> call, Throwable t) {

                        callback.onFailure(t.getMessage() != null ? t.getMessage() : " ERROR Check Your Network Please");
                    }

                });
    }

    @Override
    public void getCategories(MealsDataCallback<List<Category>> callback) {
        mealService.getCategories().enqueue(new Callback<CategoryResponse>() {

            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {

                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCategories() != null && !response.body().getCategories().isEmpty()) {
                        List<Category> categoryList = response.body().getCategories();
                        callback.onSuccess(categoryList);
                    } else {
                        callback.onFailure("There is no Category");
                    }

                } else {
                    callback.onFailure("Server Error Categories Not Found");
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                callback.onFailure(t.getMessage() != null ? t.getMessage() : "Network Error Please Check Your Network");

            }
        });

    }

    @Override
    public void getAreas(MealsDataCallback<List<Area>> callback) {
        mealService.getAreas().enqueue(new Callback<AreaReposnse>() {

            @Override
            public void onResponse(Call<AreaReposnse> call, Response<AreaReposnse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getAreas() != null && !response.body().getAreas().isEmpty()) {
                        List<Area> areas = response.body().getAreas();
                        callback.onSuccess(areas);
                    } else {
                        callback.onFailure("There is no areas data");
                    }

                } else {
                    callback.onFailure("Server Error areas Not Found");
                }
            }

            @Override
            public void onFailure(Call<AreaReposnse> call, Throwable t) {
                callback.onFailure(t.getMessage() != null ? t.getMessage() : "Network Error Please Check Your Network");
            }
        });

    }

    @Override
    public void getIngredients(MealsDataCallback<List<Ingredient>> callback) {
        mealService.getIngredients().enqueue(new Callback<IngredientResponse>() {
            @Override
            public void onResponse(Call<IngredientResponse> call, Response<IngredientResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getIngredients() != null && !response.body().getIngredients().isEmpty()) {
                        List<Ingredient> ingredients = response.body().getIngredients();
                        callback.onSuccess(ingredients);
                    } else {
                        callback.onFailure("There is no ingredients data");
                    }

                } else {
                    callback.onFailure("Server Error Ingredients Not Found");
                }
            }

            @Override
            public void onFailure(Call<IngredientResponse> call, Throwable t) {
                callback.onFailure(t.getMessage() != null ? t.getMessage() : "Network Error Please Check Your Network");
            }
        });
    }

    @Override
    public void getPopularMeals(MealsDataCallback<List<MealItemModel>> callback) {
        mealService.getPopularMeals("a")
                .enqueue(new Callback<MealsResponse>() {
                    @Override
                    public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            List<MealItemModel> allMeals = response.body().getMeals();

                            Log.d("DEBUG_MEAL", "Name: " + allMeals.get(0).getName() +
                                    ", Category: " + allMeals.get(0).getCategory() +
                                    ", Area: " + allMeals.get(0).getArea() +
                                    ", Flag URL: " + allMeals.get(0).getCountryFlagUrl());

                            List<MealItemModel> popularMeals = allMeals.stream()
                                    .limit(10)
                                    .collect(Collectors.toList());

                            callback.onSuccess(popularMeals);
                        } else {
                            callback.onFailure("Error fetching meals");
                        }
                    }

                    @Override
                    public void onFailure(Call<MealsResponse> call, Throwable t) {
                        callback.onFailure(t.getMessage() != null ? t.getMessage() : "Network Error Please Check Your Network");
                    }
                });

    }
}
