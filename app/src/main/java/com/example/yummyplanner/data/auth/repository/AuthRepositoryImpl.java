package com.example.yummyplanner.data.auth.repository;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.example.yummyplanner.data.auth.model.User;
import com.google.firebase.auth.AuthCredential;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthRepositoryImpl implements AuthRepository {
    private final FirebaseAuth firebaseAuth;
    private static AuthRepositoryImpl instance;

    private AuthRepositoryImpl() {
        this.firebaseAuth = FirebaseAuth.getInstance();
    }


    public static synchronized AuthRepositoryImpl getInstance(){
        if(instance == null){
            instance = new AuthRepositoryImpl();
        }
        return  instance;
    }
    @Override
    public void loginWithEmailAndPassword(String email, String password, AuthResultCallback authResultCallback) {

        Log.d("LOGIN", "email "+email+" password "+password);

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
    public void registerWithEmailAndPassword(String email, String password, AuthResultCallback authResultCallback) {

        firebaseAuth.createUserWithEmailAndPassword(email, password)
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
