package com.example.yummyplanner.data.meals.local.userSession;

import com.example.yummyplanner.data.meals.local.appPreferences.AppPreferences;

public class UserSessionManager {

    private AppPreferences prefs;

    public UserSessionManager(AppPreferences prefs) {
        this.prefs = prefs;
    }

    public boolean isOnboardingCompleted() {
        return prefs.isOnboardingCompleted();
    }

    public boolean isLoggedIn() {
        return prefs.isLoggedIn();
    }

    public boolean isGuest() {
        return prefs.isGuest();
    }

    public void completeOnboarding() {
        prefs.setOnboardingCompleted(true);
    }

    public void loginSuccess() {
        prefs.setLoggedIn(true);
        prefs.setGuest(false);
        prefs.setOnboardingCompleted(true);
    }

    public void enterGuest() {
        prefs.setGuest(true);
        prefs.setLoggedIn(false);
        prefs.setOnboardingCompleted(true);
    }

    public void logout() {
        prefs.clear();
    }
}
