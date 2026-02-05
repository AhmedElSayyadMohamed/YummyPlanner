package com.example.yummyplanner.data.repository;

public interface MealRepository {

    boolean hasSeenOnboarding();
    void setOnboardingCompleted();
    boolean isGuestUser();
    void loginAsGuest();
}
