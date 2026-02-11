package com.example.yummyplanner.data.meals.local;

import android.content.Context;

import com.example.yummyplanner.data.meals.local.entity.FavouriteMealEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class MealLocalDataSourceImpl implements MealLocalDataSource {

    private final FavoriteMealDao favoriteMealDao;
    private static MealLocalDataSourceImpl instance;

    private MealLocalDataSourceImpl(Context context) {
        this.favoriteMealDao = MealDatabase.getInstance(context).favoriteMealDao();
    }

   public static synchronized MealLocalDataSourceImpl getInstance(Context context){
        if(instance == null){
            instance = new MealLocalDataSourceImpl(context);
        }
        return  instance;
   }

    @Override
    public Flowable<List<FavouriteMealEntity>> getAllFavorites() {
        return favoriteMealDao.observeAllFavorites();
    }

    @Override
    public Completable insertFavorite(FavouriteMealEntity meal) {
        return favoriteMealDao.insertFavourite(meal);
    }

    @Override
    public Completable deleteFavorite(FavouriteMealEntity meal) {
        return favoriteMealDao.deleteFavourite(meal);
    }

    @Override
    public Completable deleteFavoriteById(String mealId) {
        return favoriteMealDao.deleteFavouriteById(mealId);
    }

    @Override
    public Single<Boolean> isFavorite(String mealId) {
        return favoriteMealDao.isFavourite(mealId);
    }

    @Override
    public Single<FavouriteMealEntity> getFavoriteById(String mealId) {
        return favoriteMealDao.getFavouriteById(mealId);
    }
}
