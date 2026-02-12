package com.example.yummyplanner.data.repository;
import android.content.Context;

import com.example.yummyplanner.data.auth.model.User;
import com.example.yummyplanner.data.meals.cloud.CloudRemoteDataSource;
import com.example.yummyplanner.data.meals.cloud.FireStoreManager;
import com.example.yummyplanner.data.meals.local.MealLocalDataSource;
import com.example.yummyplanner.data.meals.local.MealLocalDataSourceImpl;
import com.example.yummyplanner.data.meals.local.entity.FavouriteMealEntity;
import com.example.yummyplanner.data.meals.local.entity.PlannedMealEntity;
import com.example.yummyplanner.data.meals.model.Area;
import com.example.yummyplanner.data.meals.model.Category;
import com.example.yummyplanner.data.meals.model.IngredientApiItem;
import com.example.yummyplanner.data.meals.model.MealItemModel;
import com.example.yummyplanner.data.meals.model.MealdetailsItemModel;
import com.example.yummyplanner.data.meals.repository.MealsDataCallback;
import com.example.yummyplanner.data.remote.MealRemoteDataSource;
import com.example.yummyplanner.data.remote.MealRemoteDataSourceImpl;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;


public class MealRepositoryImpl implements com.example.yummyplanner.data.repository.MealRepository {

    private static MealRepositoryImpl instance;
    private final  MealRemoteDataSource remoteDataSource;
    private final  MealLocalDataSource localDataSource;


    private MealRepositoryImpl(Context context) {
        remoteDataSource = MealRemoteDataSourceImpl.getInstance();
        localDataSource = MealLocalDataSourceImpl.getInstance(context);
    }

    public static synchronized MealRepositoryImpl getInstance(Context context){
        if(instance == null){
            instance = new MealRepositoryImpl(context);
        }
        return  instance;
    }



    // home fetch
    @Override
    public void getRandomMeal(MealsDataCallback<MealItemModel> callback) {
        remoteDataSource.getRandomMeal(callback);
    }

    @Override
    public void getCategories(MealsDataCallback<List<Category>> callback) {
        remoteDataSource.getCategories(callback);
    }

    @Override
    public void getAreas(MealsDataCallback<List<Area>> callback) {
        remoteDataSource.getAreas(callback);
    }

    @Override
    public void getIngredients(MealsDataCallback<List<IngredientApiItem>> callback) {
        remoteDataSource.getIngredients(callback);
    }

    @Override
    public void getPopularMeals(MealsDataCallback<List<MealItemModel>> callback) {
        remoteDataSource.getPopularMeals(callback);
    }

    @Override
    public void getMeadDetails(String id, MealsDataCallback<MealdetailsItemModel> callback) {
        remoteDataSource.getMealById(id,callback);
    }





    //favourite
    public Flowable<List<FavouriteMealEntity>> getAllFavorites() {
        return localDataSource.getAllFavorites();
    }

    public Completable insertFavorite(FavouriteMealEntity meal) {
        return localDataSource.insertFavorite(meal);
    }

    @Override
    public Completable deleteFavorite(FavouriteMealEntity meal) {
        return localDataSource.deleteFavorite(meal);
    }

    public Completable deleteFavoriteById(String id) {
        return localDataSource.deleteFavoriteById(id);
    }

    public Single<Boolean> isFavorite(String id) {
        return localDataSource.isFavorite(id);
    }

    @Override
    public Single<FavouriteMealEntity> getFavoriteById(String mealId) {
        return localDataSource.getFavoriteById(mealId);
    }
}

