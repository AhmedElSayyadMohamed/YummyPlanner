package com.example.yummyplanner.data.repository;

import com.example.yummyplanner.data.meals.model.Area;
import com.example.yummyplanner.data.meals.model.Category;
import com.example.yummyplanner.data.meals.model.Ingredient;
import com.example.yummyplanner.data.meals.model.MealItemModel;
import com.example.yummyplanner.data.remote.MealRemoteDataSource;
import com.example.yummyplanner.data.remote.MealRemoteDataSourceImpl;
import com.example.yummyplanner.data.repository.MealRepository;
import java.util.List;
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
    public Single<List<Ingredient>> getIngredients() {
        return remoteDataSource.getIngredients();
    }

    @Override
    public Single<List<MealItemModel>> getPopularMeals() {
        return remoteDataSource.getPopularMeals();
    }

    @Override
    public Single<MealItemModel> getMealDetails(String id) {
        return remoteDataSource.getMealById(id);
    }
}