package com.example.yummyplanner.ui.home.presenter;

import android.content.Context;

import com.example.yummyplanner.data.auth.repository.AuthRepository;
import com.example.yummyplanner.data.auth.repository.AuthRepositoryImpl;
import com.example.yummyplanner.data.meals.model.MealItemModel;
import com.example.yummyplanner.data.meals.repository.MealRepository;
import com.example.yummyplanner.data.meals.repository.MealRepositoryImpl;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View view;
    private final AuthRepository authRepository;
    private final MealRepository mealRepository;


    private final CompositeDisposable disposables = new CompositeDisposable();

    public HomePresenter(HomeContract.View view, Context context) {
        this.view = view;
        this.mealRepository = MealRepositoryImpl.getInstance(context);
        this.authRepository = AuthRepositoryImpl.getInstance();
    }

    @Override
    public void attachView(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void loadHomeData() {
        if (view == null) return;
        loadUserName();
        view.showLoading();

        loadMealOfTheDay();
        loadCategories();
        loadPopularMeals();
        loadCuisines();
    }

    private void loadMealOfTheDay() {
        disposables.add(
                mealRepository.getRandomMeal()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meal -> {
                                    if (view != null) view.showMealOfTheDay(meal);
                                },
                                throwable -> {
                                    if (view != null)
                                        view.showError("Failed to load meal of the day");
                                }
                        )
        );
    }

    private void loadCategories() {
        disposables.add(
                mealRepository.getCategories()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                categories -> {
                                    if (view != null) view.showCategories(categories);
                                },
                                throwable -> {
                                    if (view != null) view.showError("Failed to load Categories");
                                }
                        )
        );
    }

    private void loadPopularMeals() {
        disposables.add(
                mealRepository.getPopularMeals()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meals -> {
                                    if (view != null) view.showPopularMeals(meals);
                                },
                                throwable -> {
                                    if (view != null)
                                        view.showError("Failed to load Popular Meals");
                                }
                        )
        );
    }

    private void loadCuisines() {
        disposables.add(
                mealRepository.getAreas()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                areas -> {
                                    if (view != null) {
                                        view.showCuisines(areas);
                                        view.hideLoading();
                                    }
                                },
                                throwable -> {
                                    if (view != null) view.showError("Failed to load Areas");
                                }
                        )
        );
    }

    private void loadUserName() {
        if (view == null) return;
        if (authRepository.isUserLoggedIn() && authRepository.getCurrentUser() != null) {
            String userName = authRepository.getCurrentUser().getName();
            view.setUserName(userName != null ? userName : "Guest");
        } else {
            view.setUserName("Guest");
        }
    }

    @Override
    public void detachView() {
        disposables.clear();
        this.view = null;
    }

    @Override
    public void onMealOfTheDayClicked(MealItemModel meal) {
        if (view != null) view.navigateToMealDetails(meal);
    }

    @Override
    public void onCategoryClicked(String category) {
        if (view != null) view.navigateToMealsList(category, "category");
    }

    @Override
    public void onCuisineClicked(String area) {
        if (view != null) view.navigateToMealsList(area, "area");
    }

    @Override
    public void onPopularMealClicked(MealItemModel meal) {
        if (view != null) view.navigateToMealDetails(meal);
    }
}