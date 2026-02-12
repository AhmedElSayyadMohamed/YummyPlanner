package com.example.yummyplanner.data.meals.repository;

import com.example.yummyplanner.data.meals.local.entity.FavouriteMealEntity;
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

public class MealRepositoryImpl implements MealRepository {

    private static MealRepositoryImpl instance;
    private final MealRemoteDataSource remoteDataSource;

    private MealRepositoryImpl() {
        remoteDataSource = MealRemoteDataSourceImpl.getInstance();
    }

    public static synchronized MealRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new MealRepositoryImpl();
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
        return remoteDataSource.getMealById(id);
    }

    @Override
    public Flowable<List<FavouriteMealEntity>> getAllFavorites() {
        return null;
    }

    @Override
    public Completable insertFavorite(FavouriteMealEntity meal) {
        return null;
    }

    @Override
    public Completable deleteFavorite(FavouriteMealEntity meal) {
        return null;
    }

    @Override
    public Completable deleteFavoriteById(String mealId) {
        return null;
    }

    @Override
    public Single<Boolean> isFavorite(String mealId) {
        return null;
    }

    @Override
    public Single<FavouriteMealEntity> getFavoriteById(String mealId) {
        return null;
    }
}
