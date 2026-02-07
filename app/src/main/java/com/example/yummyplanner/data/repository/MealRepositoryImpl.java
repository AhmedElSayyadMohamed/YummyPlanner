package com.example.yummyplanner.data.repository;

import com.example.yummyplanner.data.model.Category;
import com.example.yummyplanner.data.model.Meal;
import com.example.yummyplanner.data.model.response.FailureCallback;
import com.example.yummyplanner.data.model.response.MealOfTheDayCallback;
import com.example.yummyplanner.data.model.response.OnSuccessCallback;
import com.example.yummyplanner.data.remote.MealRemoteDataSource;
import com.example.yummyplanner.data.remote.MealRemoteDataSourceImpl;

import java.util.List;


public class MealRepositoryImpl implements MealRepository {

    private static MealRepositoryImpl instance;
    private final MealRemoteDataSource remoteDataSource;

    private MealRepositoryImpl() {
        remoteDataSource = new MealRemoteDataSourceImpl();
    }

    public static MealRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new MealRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void getMealOfTheDay(MealOfTheDayCallback callback) {
        remoteDataSource.getMealOfTheDay(callback);
    }

    @Override
    public void getCategories(OnSuccessCallback<List<Category>> successCallback, FailureCallback failureCallback) {
        remoteDataSource.getCategories(successCallback, failureCallback);
    }

    @Override
    public void getPopularMeals(OnSuccessCallback<List<Meal>> successCallback, FailureCallback failureCallback) {
        remoteDataSource.getPopularMeals(successCallback, failureCallback);
    }
}

