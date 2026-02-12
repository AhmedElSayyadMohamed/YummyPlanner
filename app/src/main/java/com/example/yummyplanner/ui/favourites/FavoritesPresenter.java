package com.example.yummyplanner.ui.favourites;

import com.example.yummyplanner.data.meals.model.MealdetailsItemModel;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class FavoritesPresenter implements FavoritesContract.Presenter {

    private final FavoritesContract.View view;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public FavoritesPresenter(FavoritesContract.View view) {
        this.view = view;
    }

    @Override
    public void getFavorites() {
        // Implementation will go here
    }

    @Override
    public void deleteFromFavorites(MealdetailsItemModel meal) {
        // Implementation will go here
    }

    public void clear() {
        compositeDisposable.clear();
    }
}