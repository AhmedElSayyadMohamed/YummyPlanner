package com.example.yummyplanner.ui.details.presenter;

import com.example.yummyplanner.data.meals.model.MealdetailsItemModel;
import com.example.yummyplanner.data.meals.repository.MealsDataCallback;
import com.example.yummyplanner.data.repository.MealRepository;
import com.example.yummyplanner.data.repository.MealRepositoryImpl;


public class MealDetailsPresenter implements MealDetailsContract.Presenter{

    private MealDetailsContract.View view;
    private MealRepository mealRepository;

    public MealDetailsPresenter(MealDetailsContract.View view) {
        this.view = view;
        this.mealRepository = MealRepositoryImpl.getInstance();
    }

    @Override
    public void attachView(MealDetailsContract.View view) {
        this.view = view;
    }

    @Override
    public void getMealDetails(String mealId) {
        if(view ==null)return;

        view.showLoading();
        mealRepository.getMeadDetails(mealId, new MealsDataCallback<MealdetailsItemModel>() {
            @Override
            public void onSuccess(MealdetailsItemModel meal) {
                view.hideLoading();
                view.showMealDetails(meal);
                view.showIngredients(meal.getIngredientsList());
                view.showInstructions(meal.getInstructions());

//                boolean isFav = favoritesRepository.isFavorite(meal.getId());
//                view.updateFavoriteState(isFav);
            }

            @Override
            public void onFailure(String message) {
                if (view == null) return;
                view.hideLoading();
                view.showError(message);
            }
        });
    }

    @Override
    public void onFavoriteClicked(MealdetailsItemModel meal) {

    }

    @Override
    public void onAddToPlannerClicked(MealdetailsItemModel meal, String date) {

    }

    @Override
    public void detachView() {
        this.view = null;
    }

}
