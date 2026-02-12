package com.example.yummyplanner.data.meals.local.userSession;

import com.example.yummyplanner.data.auth.model.User;

public class SessionRepositoryImpl implements SessionRepository {

    private UserSessionManager sessionManager;

    public SessionRepositoryImpl(UserSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean isOnboardingCompleted() {
        return sessionManager.isOnboardingCompleted();
    }

    @Override
    public boolean isLoggedIn() {
        return sessionManager.isLoggedIn();
    }

    @Override
    public boolean isGuest() {
        return sessionManager.isGuest();
    }

    @Override
    public void completeOnboarding() {
        sessionManager.completeOnboarding();
    }

    @Override
    public void login() {
        sessionManager.loginSuccess();
    }

    @Override
    public void enterGuest() {
        sessionManager.enterGuest();
    }

    @Override
    public void logout() {
        sessionManager.logout();
    }

    @Override
    public void saveUser(User user) {
        sessionManager.saveUser(user);
    }
}
