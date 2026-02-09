package com.example.yummyplanner.ui.home.presenter;

import com.example.yummyplanner.data.meals.model.MealItemModel;
import com.example.yummyplanner.data.meals.repository.MealsDataCallback;
import com.example.yummyplanner.data.repository.MealRepository;
import com.example.yummyplanner.data.repository.MealRepositoryImpl;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View view;
    private final A
    private final MealRepository mealRepository;


    public HomePresenter(HomeContract.View view) {
        this.view = view;
        this.mealRepository = MealRepositoryImpl.getInstance();
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

    private void loadUserName() {
        if (view == null) return;

        if (authRepository.isUserLoggedIn() && authRepository.getCurrentUser() != null) {
            String fullName = authRepository.getCurrentUser().getFullName();
            String firstName = fullName != null && fullName.contains(" ")
                    ? fullName.split(" ")[0]
                    : fullName;
            view.setUserName(firstName != null && !firstName.isEmpty() ? firstName : "Guest");
        } else {
            view.setUserName("Guest");
        }
    }
    private void loadMealOfTheDay(){

        mealRepository.getRandomMeal(new MealsDataCallback<MealItemModel>() {

            @Override
            public void onSuccess(MealItemModel data) {
                if(view != null){
                    view.showMealOfTheDay(data);
                    view.hideLoading();
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

    }

    @Override
    public void onCategoryClicked(String category) {

    }

    @Override
    public void onCuisineClicked(String area) {

    }

    @Override
    public void onIngredientClicked(String ingredient) {

    }

    @Override
    public void onPopularMealClicked(MealItemModel meal) {

    }


    @Override
    public void detachView() {
        this.view = null;
    }
}
