package com.example.yummyplanner.data.auth.repository;

import android.content.Context;
import android.util.Log;
import com.example.yummyplanner.data.auth.model.User;
import com.example.yummyplanner.data.meals.cloud.CloudRemoteDataSource;
import com.example.yummyplanner.data.meals.cloud.FireStoreManager;
import com.example.yummyplanner.data.meals.local.userSession.UserSessionManager;
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
    private final UserSessionManager sessionManager;



    private AuthRepositoryImpl(Context context) {
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.cloudRemoteDataSource = FireStoreManager.getInstance();
        this.sessionManager = UserSessionManager.getInstance(context);
    }

    public static synchronized AuthRepositoryImpl getInstance(Context context) {
        if (instance == null) {
            instance = new AuthRepositoryImpl(context);
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
                            String uid = firebaseUser.getUid();

                            cloudRemoteDataSource.getUserDocument(uid)
                                    .subscribe(documentSnapshot -> {
                                        if (documentSnapshot.exists()) {
                                            String name = documentSnapshot.getString("name");
                                            if (name == null) name = "Guest";

                                            User user = new User(uid, name, firebaseUser.getEmail());
                                            Log.d("Login", "User: " + user.toString());

                                            emitter.onSuccess(user);
                                        } else {
                                            emitter.onError(new Exception("User document not found in Firestore"));
                                        }
                                    }, emitter::onError);

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
                                            () -> {
                                                User newUser = getUserFromFirebaseUser(firebaseUser);
                                                // Make sure UID is set
                                                newUser.setuId(firebaseUser.getUid());
                                                emitter.onSuccess(newUser);
                                            },
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
                        boolean isNewUser = authResult.getAdditionalUserInfo() != null && authResult.getAdditionalUserInfo().isNewUser();

                        if (firebaseUser != null) {
                            User user = getUserFromFirebaseUser(firebaseUser);
                            if(isNewUser){
                                cloudRemoteDataSource.createUserDocument(firebaseUser.getDisplayName(), firebaseUser.getEmail())
                                    .subscribe(
                                            () -> emitter.onSuccess(user),
                                            emitter::onError
                                    );
                            } else {
                                emitter.onSuccess(user);
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
        if (firebaseUser == null) return null;
        
        User sessionUser = sessionManager.getUser();
        String userName = (sessionUser != null) ? sessionUser.getName() : firebaseUser.getDisplayName();
        String avatarUrl = (firebaseUser.getPhotoUrl() != null) ? firebaseUser.getPhotoUrl().toString() : null;
        
        User user = new User(userName, firebaseUser.getEmail());
        user.setuId(firebaseUser.getUid());
        user.setAvatarUrl(avatarUrl);
        return user;
    }

    @Override
    public Completable logout() {
        return Completable.fromAction(firebaseAuth::signOut);
    }

    private User getUserFromFirebaseUser(FirebaseUser firebaseUser) {
        if (firebaseUser == null) return null;
        String photoUrl = (firebaseUser.getPhotoUrl() != null) ? firebaseUser.getPhotoUrl().toString() : null;
        // Correct constructor usage or manual setting
        User user = new User(firebaseUser.getDisplayName(), firebaseUser.getEmail(), photoUrl, firebaseUser.getUid());
        return user;
    }
}
