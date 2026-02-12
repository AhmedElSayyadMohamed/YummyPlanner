package com.example.yummyplanner.data.auth.repository;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.example.yummyplanner.data.auth.model.User;
import com.example.yummyplanner.data.meals.cloud.CloudRemoteDataSource;
import com.example.yummyplanner.data.meals.cloud.FireStoreManager;
import com.example.yummyplanner.utiles.LogsConstants;
import com.google.firebase.auth.AuthCredential;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthRepositoryImpl implements AuthRepository {
    private final FirebaseAuth firebaseAuth;
    private static AuthRepositoryImpl instance;
    private final CloudRemoteDataSource cloudRemoteDataSource;

    private AuthRepositoryImpl() {
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.cloudRemoteDataSource = FireStoreManager.getInstance();
    }


    public static synchronized AuthRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new AuthRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void loginWithEmailAndPassword(String email, String password, AuthResultCallback authResultCallback) {

        Log.d("LOGIN", "email " + email + " password " + password);

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        Log.d("LOGIN", "signInWithEmail:success");

                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser == null) {
                            authResultCallback.onError("User is null");
                            return;
                        }

                        User user = getUserFromFirebaseUser(firebaseUser);
                        authResultCallback.onSuccess(user);

                    } else {
//                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Log.d("LOGIN", "Fail  signInWithEmail:failure ");

                        String errorMsg = task.getException() != null
                                ? task.getException().getMessage()
                                : "Login failed";

                        authResultCallback.onError(errorMsg);
                    }
                });
    }


    @Override
    public void registerWithEmailAndPassword(User user, AuthResultCallback authResultCallback) {
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success");
                        Log.d(LogsConstants.userRegister, "success registerUser in AuthRepositoryImpl ");
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser == null) {
                            authResultCallback.onError("User is null");
                            return;
                        }
                        Log.d(LogsConstants.userRegister, "we try to create firestor docs for you ");

                        // Create user document in Firestore
                        cloudRemoteDataSource.createUserDocument(user.getName(), user.getEmail())
                                .subscribe(
                                        () -> {
                                            Log.d(LogsConstants.userRegister, "createUserDocument:success");
                                            authResultCallback.onSuccess(getUserFromFirebaseUser(firebaseUser));
                                        },
                                        error -> {
                                            Log.w(LogsConstants.userRegister, "createUserDocument:failure", error);
                                            authResultCallback.onError(error.getMessage());
                                        }
                                );

                    } else {
                        String errorMsg = task.getException() != null
                                ? task.getException().getMessage()
                                : "Unknown error";

                        Log.w(LogsConstants.userRegister, "createUserWithEmail:failure", task.getException());
                        authResultCallback.onError(errorMsg);
                    }
                });
    }

    @Override
    public void loginWithGoogle(String idToken, AuthResultCallback authResultCallback) {

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success");

                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser == null) {
                            authResultCallback.onError("User is null");
                            return;
                        }

                        User user = getUserFromFirebaseUser(firebaseUser);
                        authResultCallback.onSuccess(user);

                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());

                        String errorMsg = task.getException() != null
                                ? task.getException().getMessage()
                                : "Unknown error";

                        authResultCallback.onError(errorMsg);
                    }
                });
    }

    @Override
    public boolean isUserLoggedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }

    @Override
    public User getCurrentUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        return getUserFromFirebaseUser(firebaseUser);
    }

    @Override
    public void logout() {
        firebaseAuth.signOut();
    }


    private User getUserFromFirebaseUser(FirebaseUser firebaseUser) {
        if (firebaseUser == null) {
            return null;
        }

        return new User(firebaseUser.getDisplayName(), firebaseUser.getEmail());
    }
}
