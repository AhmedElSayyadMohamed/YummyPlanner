package com.example.yummyplanner.data.local;

public interface MealLocalDataSource {

    boolean hasSeenOnboarding();
    void saveOnboardingSeen();

    boolean isGeust();
    void saveGeustMode();
}
