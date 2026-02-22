package com.example.yummyplanner.data.meals.local.userSession;

import android.content.Context;

import com.airbnb.lottie.animation.content.Content;
import com.example.yummyplanner.data.auth.model.User;
import com.example.yummyplanner.data.meals.local.userSession.UserSessionManager;



public class SessionRepositoryImpl implements SessionRepository {

    private static SessionRepositoryImpl instance;

    private final UserSessionManager sessionManager;

    public SessionRepositoryImpl(Context context) {
        this.sessionManager = UserSessionManager.getInstance(context);
    }

    public SessionRepositoryImpl(UserSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public static synchronized SessionRepositoryImpl getInstance(Context context) {
        if (instance == null) {
            instance = new SessionRepositoryImpl(context);
        }
        return instance;
    }

    @Override
    public boolean isOnboardingCompleted() {
        return sessionManager.isOnboardingCompleted();
    }

    @Override
    public void completeOnboarding() {
        sessionManager.completeOnboarding();
    }

    @Override
    public boolean isLoggedIn() {
        return sessionManager.isLoggedIn();
    }

    @Override
    public void loginSuccess() {
        sessionManager.loginSuccess();
    }

    @Override
    public boolean isGuest() {
        return sessionManager.isGuest();
    }

    @Override
    public void enterGuest() {
        sessionManager.enterGuest();
    }

    @Override
    public void saveUser(User user) {
        sessionManager.saveUser(user);
    }

    @Override
    public User getUser() {
        return sessionManager.getUser();
    }

    @Override
    public void logout() {
        sessionManager.logout();
    }

    @Override
    public void clearAll() {
        sessionManager.clear();
    }
}
