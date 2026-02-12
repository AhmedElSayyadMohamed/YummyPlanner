package com.example.yummyplanner.data.meals.local.userSession;

import com.example.yummyplanner.data.auth.model.User;

public interface SessionRepository {
    boolean isOnboardingCompleted();
    boolean isLoggedIn();
    boolean isGuest();

    void completeOnboarding();
    void login();
    void enterGuest();
    void logout();
    void saveUser(User user);
}
