package com.example.yummyplanner.data.meals.local.appPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferencesImpl implements AppPreferences {

    private static final String PREF_NAME = "yummy_prefs";
    private static final String KEY_ONBOARDING = "onboarding_seen";
    private static final String KEY_GUEST = "is_guest";
    private static final String KEY_LOGGED = "logged_in";

    private SharedPreferences prefs;

    public AppPreferencesImpl(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean isOnboardingCompleted() {
        return prefs.getBoolean(KEY_ONBOARDING, false);
    }

    @Override
    public void setOnboardingCompleted(boolean completed) {
        prefs.edit().putBoolean(KEY_ONBOARDING, completed).apply();
    }

    @Override
    public boolean isGuest() {
        return prefs.getBoolean(KEY_GUEST, false);
    }

    @Override
    public void setGuest(boolean guest) {
        prefs.edit().putBoolean(KEY_GUEST, guest).apply();
    }

    @Override
    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_LOGGED, false);
    }

    @Override
    public void setLoggedIn(boolean loggedIn) {
        prefs.edit().putBoolean(KEY_LOGGED, loggedIn).apply();
    }

    @Override
    public void clear() {
        prefs.edit().clear().apply();
    }
}
