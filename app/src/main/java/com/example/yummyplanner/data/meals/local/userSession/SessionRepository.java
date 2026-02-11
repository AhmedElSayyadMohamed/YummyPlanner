package com.example.yummyplanner.data.meals.local.userSession;

public interface SessionRepository {
    boolean isOnboardingCompleted();
    boolean isLoggedIn();
    boolean isGuest();

    void completeOnboarding();
    void login();
    void enterGuest();
    void logout();
}
