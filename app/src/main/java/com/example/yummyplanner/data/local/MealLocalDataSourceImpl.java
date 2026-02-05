package com.example.yummyplanner.data.local;

import android.content.Context;

import com.example.yummyplanner.data.local.sharedPref.SharedPrefHelper;
import com.example.yummyplanner.data.local.sharedPref.SharedPrefHelperImpl;

public class MealLocalDataSourceImpl implements MealLocalDataSource {
    private final SharedPrefHelper prefHelper;

    public MealLocalDataSourceImpl(Context context) {
        this.prefHelper = new SharedPrefHelperImpl(context);
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
