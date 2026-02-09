package com.example.yummyplanner.ui.home.presenter;

import com.example.yummyplanner.data.auth.repository.AuthRepository;
import com.example.yummyplanner.data.auth.repository.AuthRepositoryImpl;
import com.example.yummyplanner.data.meals.model.Area;
import com.example.yummyplanner.data.meals.model.Category;
import com.example.yummyplanner.data.meals.model.Ingredient;
import com.example.yummyplanner.data.meals.model.MealItemModel;
import com.example.yummyplanner.data.meals.repository.MealsDataCallback;
import com.example.yummyplanner.data.repository.MealRepository;
import com.example.yummyplanner.data.repository.MealRepositoryImpl;

import java.util.List;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View view;
    private final AuthRepository authRepository;
    private final MealRepository mealRepository;


    public HomePresenter(HomeContract.View view) {
        this.view = view;
        this.mealRepository = MealRepositoryImpl.getInstance();
        this.authRepository = AuthRepositoryImpl.getInstance();
    }

    @Override
    public void attachView(HomeContract.View view) {
        this.view = view ;
    }


    @Override
    public void loadHomeData() {
        if(view == null) return;

        loadUserName();

        view.showLoading();

        loadMealOfTheDay();
        loadCategories();
        loadPopularMeals();
        loadCuisines();
        loadIngredients();

    }

    private void loadIngredients() {
        mealRepository.getIngredients(new MealsDataCallback<List<Ingredient>>() {

            @Override
            public void onSuccess(List<Ingredient> data) {

                if (view != null) {
                    view.showIngredients(data);
                }
            }

            @Override
            public void onFailure(String message) {
                view.showError("Failed to load Ingredients");
            }
        });
    }

    private void loadCuisines() {
        mealRepository.getAreas(new MealsDataCallback<List<Area>>() {
            @Override
            public void onSuccess(List<Area> data) {
                if(view !=null){
                    view.showCuisines(data);
                    view.hideLoading();
                }
            }

            @Override
            public void onFailure(String message) {
                view.showError("Failed to load Areas");
            }
        });
    }

    private void loadPopularMeals() {
        mealRepository.getPopularMeals(new MealsDataCallback<List<MealItemModel>>() {
            @Override
            public void onSuccess(List<MealItemModel> data) {
                if (view != null) {
                    view.showPopularMeals(data);
                }
            }

            @Override
            public void onFailure(String message) {
                view.showError("Failed to load Popular Meals");
            }
        });
    }

    private void loadCategories() {
        mealRepository.getCategories(new MealsDataCallback<List<Category>>() {
            @Override
            public void onSuccess(List<Category> data) {
                if(view!=null){
                    view.showCategories(data);
                }
            }

            @Override
            public void onFailure(String message) {
                view.showError("Failed to load Categories");

            }
        });
    }

    private void loadUserName() {
        if(view == null)return;
        if(authRepository.isUserLoggedIn()&&authRepository.getCurrentUser()!=null){

            String userName = authRepository.getCurrentUser().getName();
            view.setUserName(userName !=null?userName:"Guest");
        }else {
            view.setUserName("Guest");
        }

    }

    private void loadMealOfTheDay(){

        mealRepository.getRandomMeal(new MealsDataCallback<MealItemModel>() {

            @Override
            public void onSuccess(MealItemModel data) {
                if(view != null){
                    view.showMealOfTheDay(data);
                }
            }

            @Override
            public void onFailure(String message) {
                if (view != null) {
                    view.showError("Failed to load meal of the day");
                    view.hideLoading();
                }
            }
        });
    }



    @Override
    public void onMealOfTheDayClicked(MealItemModel meal) {
        if (view != null && meal != null) {
            view.navigateToMealDetails(meal);
        }
    }

    @Override
    public void onCategoryClicked(String category) {
        if (view != null && category != null) {
            view.navigateToMealsList(category, "category");
        }
    }

    @Override
    public void onCuisineClicked(String area) {
        if (view != null && area != null) {
            view.navigateToMealsList(area, "area");
        }
    }

//    @Override
//    public void onIngredientClicked(String ingredient) {
//        if (view != null && ingredient != null) {
//            view.navigateToMealsList(ingredient, "ingredient");
//        }
//    }

    @Override
    public void onPopularMealClicked(MealItemModel meal) {
        if (view != null && meal != null) {
            view.navigateToMealDetails(meal);
        }
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
