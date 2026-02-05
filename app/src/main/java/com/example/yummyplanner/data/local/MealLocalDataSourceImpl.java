package com.example.yummyplanner.data.local;

import com.example.yummyplanner.data.local.sharedPref.SharedPrefHelper;

public class MealLocalDataSourceImpl implements MealLocalDataSource {
    private final SharedPrefHelper prefHelper;

    public MealLocalDataSourceImpl(SharedPrefHelper prefHelper) {
        this.prefHelper = prefHelper;
    }


    @Override
    public boolean hasSeenOnboarding() {
        return prefHelper.hasSeenOnboarding();
    }

    @Override
    public void saveOnboardingSeen() {

        prefHelper.setOnboardingSeen(true);
    }

    @Override
    public boolean isGeust() {
        return prefHelper.isGeust();
    }

    @Override
    public void saveGeustMode() {
        prefHelper.setGeust(true);
    }
}
