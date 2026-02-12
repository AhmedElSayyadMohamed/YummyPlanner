package com.example.yummyplanner.data.auth.repository;

import com.example.yummyplanner.data.auth.model.User;

public interface AuthRepository {

    void loginWithEmailAndPassword(String email,String password, AuthResultCallback listener);

    void registerWithEmailAndPassword(User user, AuthResultCallback listener);

    void loginWithGoogle(String idToken, AuthResultCallback callback);

    boolean isUserLoggedIn();

    User getCurrentUser();
    void logout();
}
