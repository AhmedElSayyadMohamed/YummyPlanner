package com.example.yummyplanner.data.meals.cloud;

import com.example.yummyplanner.data.meals.local.entity.FavouriteMealEntity;
import com.example.yummyplanner.data.meals.local.entity.PlannedMealEntity;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface CloudRemoteDataSource {

    Completable createUserDocument(String name, String email);

    Single<DocumentSnapshot> getUserDocument(String uid);

    Completable addMealToPlanner(PlannedMealEntity meal);

    Single<List<PlannedMealEntity>> getPlannerMeals();

    Completable deletePlannerMeal(String mealId);

    Flowable<List<FavouriteMealEntity>> getAllFavorites();

    Completable insertFavorite(FavouriteMealEntity meal);

    Completable deleteFavorite(FavouriteMealEntity meal);

    Completable deleteFavoriteById(String mealId);

    Single<Boolean> isFavorite(String mealId);

    Single<FavouriteMealEntity> getFavoriteById(String mealId);
}