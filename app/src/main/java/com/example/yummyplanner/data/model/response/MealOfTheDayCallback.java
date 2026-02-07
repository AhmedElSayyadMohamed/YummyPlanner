package com.example.yummyplanner.data.model.response;

import com.example.yummyplanner.data.model.Meal;

public interface MealOfTheDayCallback {
    void onSuccess(Meal meal);
    void noInternet();
    void onFailure(String message);
}
