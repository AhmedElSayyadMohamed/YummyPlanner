package com.example.yummyplanner.data.meals.repository;

public interface MealsDataCallback<T> {
    void onSuccess(T data);

    void onFailure(String message);
}
