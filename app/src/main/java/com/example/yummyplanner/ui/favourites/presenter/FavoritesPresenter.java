package com.example.yummyplanner.ui.favourites.presenter;

import com.example.yummyplanner.data.meals.model.MealdetailsItemModel;
import com.example.yummyplanner.data.meals.repository.MealRepository;
import java.util.stream.Collectors;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.List;

public class FavoritesPresenter implements FavoritesContract.Presenter {

    private final FavoritesContract.View view;
    private final MealRepository repository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public FavoritesPresenter(FavoritesContract.View view, MealRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void getFavorites() {
        compositeDisposable.add(
                repository.getAllFavorites()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meals -> {
                                    if (meals == null || meals.isEmpty()) {
                                        view.showEmptyState();
                                    } else {
                                        List<MealdetailsItemModel> favoriteMeals = meals.stream()
                                                .map(meal -> meal.toRemoteMeal())
                                                .collect(Collectors.toList());
                                        view.displayFavorites(favoriteMeals);
                                    }
                                },
                                throwable -> view.showError(throwable.getMessage())
                        )
        );
    }

    @Override
    public void deleteFromFavorites(MealdetailsItemModel meal) {
        compositeDisposable.add(
                repository.deleteFavoriteById(meal.getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {},
                                throwable -> view.showError(throwable.getMessage())
                        )
        );
    }
}
