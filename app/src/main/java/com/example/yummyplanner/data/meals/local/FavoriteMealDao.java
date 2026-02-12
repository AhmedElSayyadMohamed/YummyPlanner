package com.example.yummyplanner.data.meals.local;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.yummyplanner.data.meals.local.entity.FavouriteMealEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface FavoriteMealDao {

    @Query("SELECT * FROM favourite_meals")
    Flowable<List<FavouriteMealEntity>> getAllFavorites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertFavorite(FavouriteMealEntity meal);

    @Delete
    Completable deleteFavorite(FavouriteMealEntity meal);

    @Query("DELETE FROM favourite_meals WHERE id_meal = :mealId")
    Completable deleteFavoriteById(String mealId);

    @Query("SELECT EXISTS(SELECT 1 FROM favourite_meals WHERE id_meal = :mealId)")
    Single<Boolean> isFavorite(String mealId);

    @Query("SELECT * FROM favourite_meals WHERE id_meal = :mealId")
    Single<FavouriteMealEntity> getFavoriteById(String mealId);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAllFavorites(List<FavouriteMealEntity> meals);

    @Query("DELETE FROM favourite_meals")
    Completable clearAllFavorites();
}
