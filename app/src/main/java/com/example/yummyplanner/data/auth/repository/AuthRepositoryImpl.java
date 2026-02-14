package com.example.yummyplanner.data.auth.repository;

import android.util.Log;
import com.example.yummyplanner.data.auth.model.User;
import com.example.yummyplanner.data.meals.cloud.CloudRemoteDataSource;
import com.example.yummyplanner.data.meals.cloud.FireStoreManager;
import com.example.yummyplanner.data.meals.repository.MealRepository;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

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
    public Single<User> loginWithEmailAndPassword(String email, String password) {
        return Single.create(emitter -> {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(authResult -> {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            emitter.onSuccess(getUserFromFirebaseUser(firebaseUser));
                        } else {
                            emitter.onError(new Exception("User not found after login"));
                        }
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }

    @Override
    public Single<User> registerWithEmailAndPassword(User user) {
        return Single.create(emitter -> {
            firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                    .addOnSuccessListener(authResult -> {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            cloudRemoteDataSource.createUserDocument(user.getName(), user.getEmail())
                                    .subscribe(
                                            () -> emitter.onSuccess(getUserFromFirebaseUser(firebaseUser)),
                                            emitter::onError
                                    );
                        } else {
                            emitter.onError(new Exception("Failed to get Firebase User"));
                        }
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }

    @Override
    public Single<User> loginWithGoogle(String idToken) {
        return Single.create(emitter -> {
            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
            firebaseAuth.signInWithCredential(credential)
                    .addOnSuccessListener(authResult -> {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        boolean isNewUser = authResult.getAdditionalUserInfo().isNewUser();

                        if (firebaseUser != null) {
                            if(isNewUser){
                            cloudRemoteDataSource.createUserDocument(firebaseUser.getDisplayName(),firebaseUser.getEmail())
                                    .subscribe(
                                            () -> emitter.onSuccess(getUserFromFirebaseUser(firebaseUser)),
                                            emitter::onError
                                    );
                            }else {
                                emitter.onSuccess(getUserFromFirebaseUser(firebaseUser));
                            }
                        } else {
                            emitter.onError(new Exception("Google User is null"));
                        }

                    })
                    .addOnFailureListener(emitter::onError);
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
    public Completable logout() {
        return Completable.fromAction(firebaseAuth::signOut);
    }

    private User getUserFromFirebaseUser(FirebaseUser firebaseUser) {
        if (firebaseUser == null) return null;
        return new User(firebaseUser.getDisplayName(), firebaseUser.getEmail());
    }
}