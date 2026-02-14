package com.example.yummyplanner.ui.setting.presenter;

import android.content.Context;

import com.example.yummyplanner.data.auth.model.User;
import com.example.yummyplanner.data.auth.repository.AuthRepositoryImpl;
import com.example.yummyplanner.data.meals.local.userSession.UserSessionManager;
import com.example.yummyplanner.data.meals.repository.MealRepositoryImpl;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SettingPresenter implements SettingContract.Presenter {

    private final SettingContract.View view;
    private final Context context;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public SettingPresenter(Context context, SettingContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void loadUser() {
        User currentUser = UserSessionManager.getInstance(context).getUser();

        if (currentUser != null) {
            view.showUser(currentUser);
        } else {
            view.showGuest();
        }
    }

    @Override
    public void loadStats() {

        disposables.add(
                MealRepositoryImpl.getInstance(context).getAllFavorites()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                favorites -> view.showFavoriteCount(favorites.size()),
                                throwable -> view.showFavoriteCount(0)
                        )
        );

        disposables.add(
                MealRepositoryImpl.getInstance(context).getAllPlannedMeals()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                planned -> view.showPlanCount(planned.size()),
                                throwable -> view.showPlanCount(0)
                        )
        );
    }

    @Override
    public void logout() {

        disposables.add(
                AuthRepositoryImpl.getInstance(context).logout()
                        .andThen(MealRepositoryImpl.getInstance(context).clearAllData())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            UserSessionManager.getInstance(context).logout();
                            view.navigateToAuth();
                        }, throwable -> {
                            view.showError("Logout failed");
                        })
        );
    }

    @Override
    public void onDestroy() {
        disposables.clear();
    }
}
