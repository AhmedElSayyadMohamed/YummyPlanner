package com.example.yummyplanner.data.repository;

import com.example.yummyplanner.data.model.Category;
import com.example.yummyplanner.data.model.Meal;
import com.example.yummyplanner.data.model.response.FailureCallback;
import com.example.yummyplanner.data.model.response.MealOfTheDayCallback;
import com.example.yummyplanner.data.model.response.OnSuccessCallback;

import java.util.List;

public interface MealRepository {

    void getMealOfTheDay(MealOfTheDayCallback callback);

    void getCategories(OnSuccessCallback<List<Category>> successCallback, FailureCallback failureCallback);

    void getPopularMeals(OnSuccessCallback<List<Meal>> successCallback, FailureCallback failureCallback);

}
