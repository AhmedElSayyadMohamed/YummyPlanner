package com.example.yummyplanner.data.meals.local;

import android.content.Context;
import com.example.yummyplanner.data.meals.local.entity.FavouriteMealEntity;
import com.example.yummyplanner.data.meals.local.entity.PlannedMealEntity;

import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class MealLocalDataSourceImpl implements MealLocalDataSource {

    private final FavoriteMealDao favoriteMealDao;
    private final PlannerDao plannerDao;
    private static MealLocalDataSourceImpl instance;

    private MealLocalDataSourceImpl(Context context) {
        MealDatabase db = MealDatabase.getInstance(context);
        this.favoriteMealDao = db.favoriteMealDao();
        this.plannerDao = db.plannerDao();
    }

    public static synchronized MealLocalDataSourceImpl getInstance(Context context) {
        if (instance == null) {
            instance = new MealLocalDataSourceImpl(context);
        }
        return instance;
    }



    @Override
    public Flowable<List<FavouriteMealEntity>> getAllFavorites() {
        return favoriteMealDao.getAllFavorites();
    }

    @Override
    public Completable insertFavorite(FavouriteMealEntity meal) {
        return favoriteMealDao.insertFavorite(meal);
    }

    @Override
    public Completable deleteFavorite(FavouriteMealEntity meal) {
        return favoriteMealDao.deleteFavorite(meal);
    }

    @Override
    public Completable deleteFavoriteById(String mealId) {
        return favoriteMealDao.deleteFavoriteById(mealId);
    }

    @Override
    public Single<Boolean> isFavorite(String mealId) {
        return favoriteMealDao.isFavorite(mealId);
    }

    @Override
    public Single<FavouriteMealEntity> getFavoriteById(String mealId) {
        return favoriteMealDao.getFavoriteById(mealId);
    }

    @Override
    public Flowable<List<PlannedMealEntity>> getPlannedMealsByDate(String date) {

        return plannerDao.getPlannedMealsByDate(date);
    }

    @Override
    public Flowable<List<PlannedMealEntity>> getAllPlannedMeals() {
        return plannerDao.getAllPlannedMeals();
    }

    @Override
    public Completable insertPlannedMeal(PlannedMealEntity meal) {
        return plannerDao.insertPlannedMeal(meal);
    }

    @Override
    public Completable deletePlannedMeal(PlannedMealEntity meal) {
        return plannerDao.deletePlannedMeal(meal);
    }

    @Override
    public Completable insertAllFavorites(List<FavouriteMealEntity> meals) {
        return favoriteMealDao.insertAllFavorites(meals);
    }

    @Override
    public Completable insertAllPlannedMeals(List<PlannedMealEntity> meals) {
        return plannerDao.insertAllPlannedMeals(meals);
    }

    @Override
    public Completable deleteAllFavorites() {
        return favoriteMealDao.clearAllFavorites();
    }

    @Override
    public Completable deleteAllPlannedMeals() {
        return plannerDao.clearAllPlannedMeals();
    }
}
