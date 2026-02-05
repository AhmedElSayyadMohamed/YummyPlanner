package com.example.yummyplanner.data.repository;

import android.app.Application;
import android.content.Context;

import com.example.yummyplanner.data.local.MealLocalDataSource;
import com.example.yummyplanner.data.local.MealLocalDataSourceImpl;

public class MealRepositoryImpl implements MealRepository {

    private final MealLocalDataSource mealLocalDataSource;

    public MealRepositoryImpl(Application application) {
        this.mealLocalDataSource = new MealLocalDataSourceImpl(application);
    }


    @Override
    public boolean hasSeenOnboarding() {
        return mealLocalDataSource.hasSeenOnboarding();
    }

    @Override
    public void setOnboardingCompleted() {
         mealLocalDataSource.saveOnboardingSeen();
    }

    @Override
    public boolean isGuestUser() {
        return mealLocalDataSource.isGeust();
    }

    @Override
    public void loginAsGuest() {
        mealLocalDataSource.saveGeustMode();
    }
}
