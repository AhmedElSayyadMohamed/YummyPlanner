package com.example.yummyplanner.data.meals.local.userSession;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.yummyplanner.data.auth.model.User;
import com.example.yummyplanner.data.meals.local.appPreferences.AppPreferences;
import com.example.yummyplanner.data.meals.local.appPreferences.AppPreferencesImpl;

public class UserSessionManager {

    private static final String PREF_NAME = "UserSession";
    private static final String KEY_NAME = "user_name";
    private static final String KEY_EMAIL = "user_email";
    private static final String KEY_AVATAR = "user_avatar";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    private static UserSessionManager instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private AppPreferences prefs;

    private UserSessionManager(Context context) {
        this.prefs = new AppPreferencesImpl(context);
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public static synchronized UserSessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new UserSessionManager(context.getApplicationContext());
        }
        return instance;
    }

    public void saveUser(User user) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_AVATAR, user.getAvatarUrl());
        editor.apply();
    }

    public User getUser() {
        if (!sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)) return null;

        return new User(
                sharedPreferences.getString(KEY_NAME, ""),
                sharedPreferences.getString(KEY_EMAIL, ""),
                sharedPreferences.getString(KEY_AVATAR, "")
        );
    }

    public void logout() {
        editor.clear();
        editor.apply();
        prefs.clear();
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

}
