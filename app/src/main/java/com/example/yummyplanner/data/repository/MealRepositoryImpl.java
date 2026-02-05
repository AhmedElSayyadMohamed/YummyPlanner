package com.example.yummyplanner.data.repository;

import com.example.yummyplanner.data.local.MealLocalDataSource;

public class MealRepositoryImpl implements MealRepository {

    private final MealLocalDataSource mealLocalDataSource;

    public MealRepositoryImpl(MealLocalDataSource mealLocalDataSource) {
        this.mealLocalDataSource = mealLocalDataSource;
    }


    @Override
    public boolean shouldShowOnboarding() {
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
