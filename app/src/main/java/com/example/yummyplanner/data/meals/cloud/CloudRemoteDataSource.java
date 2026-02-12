package com.example.yummyplanner.data.meals.cloud;

import com.example.yummyplanner.data.meals.local.entity.PlannedMealEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface CloudRemoteDataSource {

    Completable createUserDocument(String name, String email);

    Completable addMealToPlanner(PlannedMealEntity meal);

    Completable deletePlannerMeal(String remoteId);

    Single<List<PlannedMealEntity>> getPlannerMeals();

}
