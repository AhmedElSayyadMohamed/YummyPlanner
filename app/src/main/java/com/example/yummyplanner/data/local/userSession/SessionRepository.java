package com.example.yummyplanner.data.local.userSession;

public interface SessionRepository {
    boolean isOnboardingCompleted();
    boolean isLoggedIn();
    boolean isGuest();

    void completeOnboarding();
    void login();
    void enterGuest();
    void logout();
}
