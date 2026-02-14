package com.example.yummyplanner.data.meals.local.userSession;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.yummyplanner.data.auth.model.User;

public class UserSessionManager {

    private static final String PREF_NAME = "yummy_session";

    private static final String KEY_ONBOARDING = "onboarding_seen";
    private static final String KEY_GUEST = "is_guest";
    private static final String KEY_LOGGED = "logged_in";

    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_NAME = "user_name";
    private static final String KEY_EMAIL = "user_email";
    private static final String KEY_AVATAR = "user_avatar";

    private static UserSessionManager instance;
    private SharedPreferences prefs;

    private UserSessionManager(Context context) {
        prefs = context.getApplicationContext()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized UserSessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new UserSessionManager(context);
        }
        return instance;
    }

    public boolean isOnboardingCompleted() {
        return prefs.getBoolean(KEY_ONBOARDING, false);
    }

    public void completeOnboarding() {
        prefs.edit().putBoolean(KEY_ONBOARDING, true).apply();
    }

    public boolean isGuest() {
        return prefs.getBoolean(KEY_GUEST, false);
    }

    public void enterGuest() {
        prefs.edit()
                .putBoolean(KEY_GUEST, true)
                .putBoolean(KEY_LOGGED, false)
                .apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_LOGGED, false);
    }

    public void loginSuccess() {
        prefs.edit()
                .putBoolean(KEY_LOGGED, true)
                .putBoolean(KEY_GUEST, false)
                .apply();
    }

    public void saveUser(User user) {
        if (user == null) return;

        prefs.edit()
                .putString(KEY_USER_ID, user.getuId())
                .putString(KEY_NAME, user.getName())
                .putString(KEY_EMAIL, user.getEmail())
                .putString(KEY_AVATAR, user.getAvatarUrl())
                .apply();

        loginSuccess();
    }

    public User getUser() {
        if (!isLoggedIn()) return null;

        return new User(
                prefs.getString(KEY_USER_ID, ""),
                prefs.getString(KEY_NAME, ""),
                prefs.getString(KEY_EMAIL, ""),
                prefs.getString(KEY_AVATAR, "")
        );
    }

    public void logout() {
        prefs.edit()
                .remove(KEY_LOGGED)
                .remove(KEY_GUEST)
                .remove(KEY_USER_ID)
                .remove(KEY_NAME)
                .remove(KEY_EMAIL)
                .remove(KEY_AVATAR)
                .apply();
    }

    public void clear() {
        prefs.edit().clear().apply();
    }
}
