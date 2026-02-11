package com.example.yummyplanner.ui.details.presenter;

import com.example.yummyplanner.data.meals.local.entity.FavouriteMealEntity;
import com.example.yummyplanner.data.meals.model.MealdetailsItemModel;
import com.example.yummyplanner.data.meals.repository.MealsDataCallback;
import com.example.yummyplanner.data.repository.MealRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MealDetailsPresenter implements MealDetailsContract.Presenter {

    private MealDetailsContract.View view;
    private final MealRepository mealRepository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MealDetailsPresenter(MealDetailsContract.View view, MealRepository mealRepository) {
        this.view = view;
        this.mealRepository = mealRepository;
    }

    @Override
    public void attachView(MealDetailsContract.View view) {
        this.view = view;
    }

    @Override
    public void getMealDetails(String mealId) {
        if (view == null) return;

        view.showLoading();
        mealRepository.getMeadDetails(mealId, new MealsDataCallback<MealdetailsItemModel>() {
            @Override
            public void onSuccess(MealdetailsItemModel meal) {
                view.hideLoading();
                view.showMealDetails(meal);
                view.showIngredients(meal.getIngredientsList());
                view.showInstructions(meal.getInstructions());
                checkIfFavorite(meal.getId());
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

        compositeDisposable.add(
                mealRepository.isFavorite(meal.getId())
                        .subscribeOn(Schedulers.io())
                        .flatMapCompletable(isFav -> {
                            if (isFav) {
                                view.showFavoriteRemoved();
                                return mealRepository.deleteFavoriteById(meal.getId());
                            } else {
                                FavouriteMealEntity entity = FavouriteMealEntity.fromRemoteMeal(meal);
                                view.showFavoriteAdded();
                                return mealRepository.insertFavorite(entity);
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    checkIfFavorite(meal.getId());
                                },
                                throwable -> {
                                    if (view != null)
                                        view.showError(throwable.getMessage());
                                }
                        )
        );
    }

    private void checkIfFavorite(String mealId) {
        compositeDisposable.add(
                mealRepository.isFavorite(mealId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                isFav -> {
                                    if (view != null) {
                                        view.updateFavoriteState(isFav);
                                    }
                                },
                                throwable -> {
                                    if (view != null)
                                        view.showError(throwable.getMessage());
                                }
                        )
        );
    }

    @Override
    public void onAddToPlannerClicked(MealdetailsItemModel meal, String date) {

    }


    @Override
    public void detachView() {
        this.view = null;
        compositeDisposable.clear();
    }

}
