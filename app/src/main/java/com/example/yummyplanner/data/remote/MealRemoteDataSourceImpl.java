package com.example.yummyplanner.data.remote;


import com.example.yummyplanner.data.model.Category;
import com.example.yummyplanner.data.model.Meal;
import com.example.yummyplanner.data.model.response.CategoryResponse;
import com.example.yummyplanner.data.model.response.FailureCallback;
import com.example.yummyplanner.data.model.response.MealOfTheDayCallback;
import com.example.yummyplanner.data.model.response.MealResponse;
import com.example.yummyplanner.data.model.response.OnSuccessCallback;
import com.example.yummyplanner.data.network.Network;

import java.io.IOException;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealRemoteDataSourceImpl implements MealRemoteDataSource {

    private final MealService mealService;

    public MealRemoteDataSourceImpl() {
        this.mealService = Network.getInstance().create(MealService.class);
    }

    @Override
    public void getMealOfTheDay(
            MealOfTheDayCallback callback) {

        mealService.getMealOfTheDay()
                .enqueue(new Callback<MealResponse>() {

                    @Override
                    public void onResponse(
                            Call<MealResponse> call,
                            Response<MealResponse> response) {

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().getMeals() != null
                                && !response.body().getMeals().isEmpty()) {

                            Meal meal =
                                    response.body()
                                            .getMeals()
                                            .get(0);

                            callback.onSuccess(meal);

                        } else {
                            callback.onFailure("Server error Empty response");
                        }
                    }

                    @Override
                    public void onFailure(Call<MealResponse> call, Throwable t) {

                        if (t instanceof IOException) {
                            callback.noInternet();
                        } else {
                            callback.onFailure( t.getMessage() != null ? t.getMessage() : "Unknown error");
                        }
                    }
                });
    }

    @Override
    public void getCategories(OnSuccessCallback<List<Category>> successCallback, FailureCallback failureCallback) {
        mealService.getCategories().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    successCallback.onSuccess(response.body().getCategories());
                } else {
                    failureCallback.onFailure("Error fetching categories");
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                failureCallback.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void getPopularMeals(OnSuccessCallback<List<Meal>> successCallback, FailureCallback failureCallback) {
        mealService.getPopularMeals("Seafood").enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    successCallback.onSuccess(response.body().getMeals());
                } else {
                    failureCallback.onFailure("Error fetching popular meals");
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                failureCallback.onFailure(t.getMessage());
            }
        });
    }
}
