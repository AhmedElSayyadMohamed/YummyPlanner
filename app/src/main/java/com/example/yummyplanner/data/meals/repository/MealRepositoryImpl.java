package com.example.yummyplanner.data.repository;
import com.example.yummyplanner.data.meals.model.Area;
import com.example.yummyplanner.data.meals.model.Category;
import com.example.yummyplanner.data.meals.model.IngredientApiItem;
import com.example.yummyplanner.data.meals.model.MealItemModel;
import com.example.yummyplanner.data.meals.model.MealdetailsItemModel;
import com.example.yummyplanner.data.meals.repository.MealsDataCallback;
import com.example.yummyplanner.data.remote.MealRemoteDataSource;
import com.example.yummyplanner.data.remote.MealRemoteDataSourceImpl;

import java.util.List;


public class MealRepositoryImpl implements MealRepository {

    private static MealRepositoryImpl instance;
    private final  MealRemoteDataSource remoteDataSource;

    private MealRepositoryImpl() {
        remoteDataSource = MealRemoteDataSourceImpl.getInstance();
    }

    public static synchronized MealRepositoryImpl getInstance(){
        if(instance == null){
            instance = new MealRepositoryImpl();
        }
        return  instance;
    }


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
}

