package com.example.yummyplanner.data.meals.repository;

import android.content.Context;

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
import com.example.yummyplanner.data.meals.remote.MealRemoteDataSource;
import com.example.yummyplanner.data.meals.remote.MealRemoteDataSourceImpl;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealRepositoryImpl implements MealRepository {

    private static MealRepositoryImpl instance;
    private final MealRemoteDataSource remoteDataSource;
    private final MealLocalDataSource localDataSource;
    private final CloudRemoteDataSource cloudRemoteDataSource;

    private MealRepositoryImpl(Context context) {
        remoteDataSource = MealRemoteDataSourceImpl.getInstance();
        localDataSource = MealLocalDataSourceImpl.getInstance(context);
        cloudRemoteDataSource = FireStoreManager.getInstance();
    }

    public static synchronized MealRepositoryImpl getInstance(Context context) {
        if (instance == null) {
            instance = new MealRepositoryImpl(context);
        }
        return instance;
    }

    @Override
    public Single<MealItemModel> getRandomMeal() {
        return remoteDataSource.getRandomMeal();
    }

    @Override
    public Single<List<Category>> getCategories() {
        return remoteDataSource.getCategories();
    }

    @Override
    public Single<List<Area>> getAreas() {
        return remoteDataSource.getAreas();
    }

    @Override
    public Single<List<IngredientApiItem>> getIngredients() {
        return remoteDataSource.getIngredients();
    }

    @Override
    public Single<List<MealItemModel>> getPopularMeals() {
        return remoteDataSource.getPopularMeals();
    }

    @Override
    public Single<MealdetailsItemModel> getMealDetails(String id) {
        return remoteDataSource.getMealById(id)
                .onErrorResumeNext(throwable -> 
                    localDataSource.getFavoriteById(id)
                            .map(FavouriteMealEntity::toRemoteMeal)
                );
    }

    @Override
    public Single<List<MealItemModel>> searchMealsByName(String name) {
        return remoteDataSource.searchMealsByName(name);
    }

    @Override
    public Single<List<MealItemModel>> filterByCategory(String category) {
        return remoteDataSource.filterByCategory(category);
    }

    @Override
    public Single<List<MealItemModel>> filterByArea(String area) {
        return remoteDataSource.filterByArea(area);
    }

    @Override
    public Single<List<MealItemModel>> filterByIngredient(String ingredient) {
        return remoteDataSource.filterByIngredient(ingredient);
    }

    //favourite

    @Override
    public Completable insertFavorite(FavouriteMealEntity meal) {
        return localDataSource.insertFavorite(meal)
                .andThen(cloudRemoteDataSource.insertFavorite(meal))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deleteFavoriteById(String mealId) {
        return localDataSource.deleteFavoriteById(mealId)
                .andThen(
                        cloudRemoteDataSource.deleteFavoriteById(mealId)
                )
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<List<FavouriteMealEntity>> getAllFavorites() {
        return localDataSource.getAllFavorites();
    }

    @Override
    public Single<Boolean> isFavorite(String mealId) {

        return localDataSource.isFavorite(mealId);
    }

    @Override
    public Completable insertPlannedMeal(PlannedMealEntity meal) {
        return localDataSource.insertPlannedMeal(meal)
                .andThen(cloudRemoteDataSource.addMealToPlanner(meal))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<List<PlannedMealEntity>> getPlannedMealsByDate(String date) {
        return localDataSource.getPlannedMealsByDate(date);
    }

    @Override
    public Flowable<List<PlannedMealEntity>> getAllPlannedMeals() {
        return localDataSource.getAllPlannedMeals();
    }

    @Override
    public Completable deletePlannedMeal(PlannedMealEntity meal) {
        return localDataSource.deletePlannedMeal(meal)
                .andThen(cloudRemoteDataSource.deletePlannerMeal(meal.getIdMeal()))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable clearAllData() {
        return localDataSource.deleteAllFavorites()
                .andThen(localDataSource.deleteAllPlannedMeals())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable syncDataFromCloud(String UId) {
        Completable syncFavs = cloudRemoteDataSource.getAllFavorites(UId)
                .firstOrError()
                .subscribeOn(Schedulers.io())
                .flatMapCompletable(favorites -> 
                    localDataSource.insertAllFavorites(favorites)
                        .subscribeOn(Schedulers.io())
                );
        
        Completable syncPlanner = cloudRemoteDataSource.getPlannerMeals(UId)
                .subscribeOn(Schedulers.io())
                .flatMapCompletable(plannedMeals -> 
                    localDataSource.insertAllPlannedMeals(plannedMeals)
                        .subscribeOn(Schedulers.io())
                );

        return Completable.mergeArray(syncFavs, syncPlanner)
                .subscribeOn(Schedulers.io());
    }
}
