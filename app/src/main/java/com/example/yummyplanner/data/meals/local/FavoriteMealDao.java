package com.example.yummyplanner.data.meals.local;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.yummyplanner.data.local.entity.FavouriteMealEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface FavoriteMealDao {

    @Query("SELECT * FROM favourite_meals")
    Flowable<List<FavouriteMealEntity>> observeAllFavorites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertFavourite(FavouriteMealEntity meal);

    @Delete
    Completable deleteFavourite(FavouriteMealEntity meal);

    @Query("DELETE FROM favourite_meals WHERE id_meal = :mealId")
    Completable deleteFavouriteById(String mealId);

    @Query("SELECT EXISTS(SELECT 1 FROM favourite_meals WHERE id_meal = :mealId)")
    Single<Boolean> isFavourite(String mealId);

    @Query("SELECT * FROM favourite_meals WHERE id_meal = :mealId")
    Single<FavouriteMealEntity> getFavouriteById(String mealId);
}
