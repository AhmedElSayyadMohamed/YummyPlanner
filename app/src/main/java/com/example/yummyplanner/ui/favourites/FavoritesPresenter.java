package com.example.yummyplanner.ui.favourites;

import android.app.Application;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class FavoritesPresenter implements  FavoritesContract.Presenter{
//    ProductsRepository productsRepository;
//    FavouriteView favouriteView;
//    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
//
//    public FavouritePresenterImp(Application application, FavouriteView favouriteView) {
//        productsRepository = new ProductsRepository(application);
//        this.favouriteView = favouriteView;
//    }
//
//    @Override
//    public void getFavouriteProducts() {
//        compositeDisposable.add(
//                productsRepository.getFavouriteProducts()
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                list -> {
//                                    if (list == null || list.isEmpty()) {
//                                        favouriteView.showWarningText();
//                                    } else {
//                                        favouriteView.hideWarningText();
//                                        favouriteView.showProducts(list);
//                                    }
//                                },
//                                throwable -> favouriteView.showError(throwable.getMessage())
//                        )
//        );
//    }
//
//    @Override
//    public void deleteFromFavourite(Product product) {
//        compositeDisposable.add(
//                productsRepository.deleteFromFavourite(product)
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                () -> {},
//                                throwable -> favouriteView.showError(throwable.getMessage())
//                        )
//        );
//    }
//
//    @Override
//    public void clear() {
//        compositeDisposable.clear();
//    }
}
