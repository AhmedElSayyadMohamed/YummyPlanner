package com.example.yummyplanner.ui.auth.presenter;

import android.app.Application;

import com.example.yummyplanner.data.repository.MealRepository;
import com.example.yummyplanner.data.repository.MealRepositoryImpl;

public class AuthPresenter implements AuthContract.Presenter{

    private AuthContract.View view;
    private MealRepository mealRepository;

    public AuthPresenter(AuthContract.View view,Application application){
        this.view = view ;
        this.mealRepository = new MealRepositoryImpl(application);
    }

    @Override
    public void continueAsAGeust() {

        mealRepository.loginAsGuest();
    }
}
