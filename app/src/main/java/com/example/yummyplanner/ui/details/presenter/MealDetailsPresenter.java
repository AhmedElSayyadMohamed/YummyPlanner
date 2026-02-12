package com.example.yummyplanner.ui.details.presenter;

import com.example.yummyplanner.data.meals.local.entity.FavouriteMealEntity;
import com.example.yummyplanner.data.meals.local.entity.PlannedMealEntity;
import com.example.yummyplanner.data.meals.model.MealdetailsItemModel;
import com.example.yummyplanner.data.meals.repository.MealRepository;

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


        compositeDisposable.add(
                mealRepository.getMealDetails(mealId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meal -> {
                                    if (view != null) {
                                        view.hideLoading();
                                        view.showMealDetails(meal);
                                        view.showIngredients(meal.getIngredientsList());
                                        view.showInstructions(meal.getInstructions());
                                        checkIfFavorite(meal.getId());
                                    }
                                },
                                throwable -> {
                                    if (view != null) {
                                        view.hideLoading();
                                        view.showError("Failed to load meal details: " + throwable.getMessage());
                                    }
                                }
                        )
        );
    }

    @Override
    public void onFavoriteClicked(MealdetailsItemModel meal) {
        compositeDisposable.add(
                mealRepository.isFavorite(meal.getId())
                        .subscribeOn(Schedulers.io())
                        .flatMapCompletable(isFav -> {
                            if (isFav) {
                                return mealRepository.deleteFavoriteById(meal.getId());
                            } else {
                                FavouriteMealEntity entity = FavouriteMealEntity.fromRemoteMeal(meal);
                                return mealRepository.insertFavorite(entity);
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    // Feedback is usually handled via side effects or checking state again
                                    // In original code, it was showing snackbar BEFORE the repository call finished.
                                    // Let's keep it simple or follow the previous pattern if needed.
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
        PlannedMealEntity plannedMeal = PlannedMealEntity.fromMealDetails(meal, date);
        compositeDisposable.add(
                mealRepository.insertPlannedMeal(plannedMeal)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    if (view != null) {
                                        view.showMealAddedToPlanner(date);
                                    }
                                },
                                throwable -> {
                                    if (view != null) {
                                        view.showError("Failed to add to planner: " + throwable.getMessage());
                                    }
                                }
                        )
        );
    }

    @Override
    public void detachView() {
        this.view = null;
        compositeDisposable.clear();
    }
}
