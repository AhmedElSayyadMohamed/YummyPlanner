package com.example.yummyplanner.data.meals.local.appPreferences;


public interface AppPreferences {

    boolean isOnboardingCompleted();
    void setOnboardingCompleted(boolean completed);

    boolean isGuest();
    void setGuest(boolean guest);

    boolean isLoggedIn();
    void setLoggedIn(boolean loggedIn);

    void clear();
}
