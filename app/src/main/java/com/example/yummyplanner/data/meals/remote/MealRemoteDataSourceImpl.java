package com.example.yummyplanner.data.remote;

import com.example.yummyplanner.data.meals.model.Area;
import com.example.yummyplanner.data.meals.model.Category;
import com.example.yummyplanner.data.meals.model.Ingredient;
import com.example.yummyplanner.data.meals.model.MealItemModel;
import com.example.yummyplanner.data.network.Network;
import com.example.yummyplanner.data.remote.MealService;
import  com.example.yummyplanner.data.remote.MealRemoteDataSource;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class MealRemoteDataSourceImpl implements MealRemoteDataSource {

    private final com.example.yummyplanner.data.remote.MealService mealService;
    private static MealRemoteDataSourceImpl instance;

    private MealRemoteDataSourceImpl() {
        this.mealService = Network.getInstance().create(MealService.class);
    }

    public static synchronized MealRemoteDataSourceImpl getInstance() {
        if (instance == null) {
            instance = new MealRemoteDataSourceImpl();
        }
        return instance;
    }

    @Override
    public Single<MealItemModel> getRandomMeal() {
        return mealService.getMealOfTheDay()
                .map(response -> response.getMeals().get(0));
    }

    @Override
    public Single<List<Category>> getCategories() {
        return mealService.getCategories()
                .map(response -> response.getCategories());
    }

    @Override
    public Single<List<Area>> getAreas() {
        return mealService.getAreas()
                .map(response -> response.getAreas());
    }

    @Override
    public Single<List<Ingredient>> getIngredients() {
        return mealService.getIngredients()
                .map(response -> response.getIngredients());
    }

    @Override
    public Single<List<MealItemModel>> getPopularMeals() {
        return mealService.getPopularMeals("a")
                .map(response -> response.getMeals().stream()
                        .limit(10)
                        .collect(Collectors.toList()));
    }

    @Override
    public Single<MealItemModel> getMealById(String id) {
        return mealService.getMealById(id)
                .map(response -> response.getMeals().get(0));
    }
}