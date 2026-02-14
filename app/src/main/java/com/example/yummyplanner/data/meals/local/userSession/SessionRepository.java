package com.example.yummyplanner.data.meals.local.userSession;

import com.example.yummyplanner.data.auth.model.User;

public interface SessionRepository {

    boolean isOnboardingCompleted();
    void completeOnboarding();

    boolean isGuest();
    void enterGuest();

    boolean isLoggedIn();
    void loginSuccess();

    void saveUser(User user);
    User getUser();

    void logout();
    void clearAll();
}

