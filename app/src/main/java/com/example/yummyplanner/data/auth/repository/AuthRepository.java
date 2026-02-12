package com.example.yummyplanner.data.auth.repository;

import com.example.yummyplanner.data.auth.model.User;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface AuthRepository {

    Single<User> loginWithEmailAndPassword(String email, String password);

    Single<User> registerWithEmailAndPassword(User user);

    Single<User> loginWithGoogle(String idToken);

    boolean isUserLoggedIn();

    User getCurrentUser();

    Completable logout();
}
