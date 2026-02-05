package com.example.yummyplanner.data.repository;

public interface MealRepository {
    boolean shouldShowOnboarding();

    void setOnboardingCompleted();

    boolean isGuestUser();

    void loginAsGuest();
}
