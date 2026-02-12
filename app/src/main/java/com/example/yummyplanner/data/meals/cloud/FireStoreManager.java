package com.example.yummyplanner.data.meals.cloud;

import com.example.yummyplanner.data.meals.local.entity.FavouriteMealEntity;
import com.example.yummyplanner.data.meals.local.entity.PlannedMealEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class FireStoreManager implements CloudRemoteDataSource {

    private final FirebaseFirestore firestore;
    private final FirebaseAuth auth;
    private static FireStoreManager instance;

    private static final String USERS_COLLECTION = "users";
    private static final String PLANNER_COLLECTION = "planner";
    private static final String FAVORITES_COLLECTION = "favorites";

    private FireStoreManager() {
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    public static synchronized FireStoreManager getInstance() {
        if (instance == null) {
            instance = new FireStoreManager();
        }
        return instance;
    }

    private String getUserId() {
        return (auth.getCurrentUser() != null) ? auth.getCurrentUser().getUid() : null;
    }


    @Override
    public Completable createUserDocument(String name, String email) {
        return Completable.create(emitter -> {
            String uid = getUserId();
            if (uid == null) {
                emitter.onError(new Exception("User not logged in"));
                return;
            }

            Map<String, Object> user = new HashMap<>();
            user.put("name", name);
            user.put("email", email);
            user.put("createdAt", FieldValue.serverTimestamp());

            firestore.collection(USERS_COLLECTION).document(uid)
                    .set(user)
                    .addOnSuccessListener(unused -> {
                        if (!emitter.isDisposed()) emitter.onComplete();
                    })
                    .addOnFailureListener(e -> {
                        if (!emitter.isDisposed()) emitter.onError(e);
                    });
        });
    }


    @Override
    public Completable addMealToPlanner(PlannedMealEntity meal) {
        return Completable.create(emitter -> {
            String uid = getUserId();
            if (uid == null) {
                emitter.onError(new Exception("User not logged in"));
                return;
            }

            Map<String, Object> plannerMap = new HashMap<>();
            plannerMap.put("mealId", meal.getIdMeal());
            plannerMap.put("plannedDate", meal.getPlannedDate());
            plannerMap.put("updatedAt", meal.getUpdatedAt());

            firestore.collection(USERS_COLLECTION).document(uid)
                    .collection(PLANNER_COLLECTION)
                    .document(meal.getIdMeal()) // Better to use fixed ID for easier sync
                    .set(plannerMap)
                    .addOnSuccessListener(unused -> {
                        if (!emitter.isDisposed()) emitter.onComplete();
                    })
                    .addOnFailureListener(e -> {
                        if (!emitter.isDisposed()) emitter.onError(e);
                    });
        });
    }

    @Override
    public Single<List<PlannedMealEntity>> getPlannerMeals() {
        return Single.create(emitter -> {
            String uid = getUserId();
            if (uid == null) {
                emitter.onError(new Exception("User not logged in"));
                return;
            }

            firestore.collection(USERS_COLLECTION).document(uid)
                    .collection(PLANNER_COLLECTION).get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        List<PlannedMealEntity> list = new ArrayList<>();
                        for (DocumentSnapshot doc : queryDocumentSnapshots) {
                            PlannedMealEntity entity = new PlannedMealEntity();
                            entity.setIdMeal(doc.getString("mealId"));
                            entity.setPlannedDate(doc.getString("plannedDate"));
                            Long updatedAt = doc.getLong("updatedAt");
                            entity.setUpdatedAt(updatedAt != null ? updatedAt : 0);
                            list.add(entity);
                        }
                        if (!emitter.isDisposed()) emitter.onSuccess(list);
                    })
                    .addOnFailureListener(e -> {
                        if (!emitter.isDisposed()) emitter.onError(e);
                    });
        });
    }

    @Override
    public Completable deletePlannerMeal(String mealId) {
        return Completable.create(emitter -> {
            String uid = getUserId();
            if (uid == null) {
                emitter.onError(new Exception("User not logged in"));
                return;
            }

            firestore.collection(USERS_COLLECTION).document(uid)
                    .collection(PLANNER_COLLECTION).document(mealId)
                    .delete()
                    .addOnSuccessListener(unused -> {
                        if (!emitter.isDisposed()) emitter.onComplete();
                    })
                    .addOnFailureListener(e -> {
                        if (!emitter.isDisposed()) emitter.onError(e);
                    });
        });
    }


    @Override
    public Flowable<List<FavouriteMealEntity>> getAllFavorites() {
        return Flowable.create(emitter -> {
            String uid = getUserId();
            if (uid == null) {
                emitter.onError(new Exception("User not logged in"));
                return;
            }

            firestore.collection(USERS_COLLECTION).document(uid)
                    .collection(FAVORITES_COLLECTION)
                    .addSnapshotListener((value, error) -> {
                        if (error != null) {
                            if (!emitter.isCancelled()) emitter.onError(error);
                            return;
                        }
                        if (value != null && !emitter.isCancelled()) {
                            List<FavouriteMealEntity> list = value.toObjects(FavouriteMealEntity.class);
                            emitter.onNext(list);
                        }
                    });
        }, BackpressureStrategy.LATEST);
    }

    @Override
    public Completable insertFavorite(FavouriteMealEntity meal) {
        return Completable.create(emitter -> {
            String uid = getUserId();
            if (uid == null) {
                emitter.onError(new Exception("User not logged in"));
                return;
            }

            firestore.collection(USERS_COLLECTION).document(uid)
                    .collection(FAVORITES_COLLECTION).document(meal.getIdMeal())
                    .set(meal)
                    .addOnSuccessListener(unused -> {
                        if (!emitter.isDisposed()) emitter.onComplete();
                    })
                    .addOnFailureListener(e -> {
                        if (!emitter.isDisposed()) emitter.onError(e);
                    });
        });
    }

    @Override
    public Completable deleteFavorite(FavouriteMealEntity meal) {
        return deleteFavoriteById(meal.getIdMeal());
    }

    @Override
    public Completable deleteFavoriteById(String mealId) {
        return Completable.create(emitter -> {
            String uid = getUserId();
            if (uid == null) {
                emitter.onError(new Exception("User not logged in"));
                return;
            }

            firestore.collection(USERS_COLLECTION).document(uid)
                    .collection(FAVORITES_COLLECTION).document(mealId)
                    .delete()
                    .addOnSuccessListener(unused -> {
                        if (!emitter.isDisposed()) emitter.onComplete();
                    })
                    .addOnFailureListener(e -> {
                        if (!emitter.isDisposed()) emitter.onError(e);
                    });
        });
    }

    @Override
    public Single<Boolean> isFavorite(String mealId) {
        return Single.create(emitter -> {
            String uid = getUserId();
            if (uid == null) {
                emitter.onSuccess(false);
                return;
            }

            firestore.collection(USERS_COLLECTION).document(uid)
                    .collection(FAVORITES_COLLECTION).document(mealId).get()
                    .addOnSuccessListener(doc -> {
                        if (!emitter.isDisposed()) emitter.onSuccess(doc.exists());
                    })
                    .addOnFailureListener(e -> {
                        if (!emitter.isDisposed()) emitter.onError(e);
                    });
        });
    }

    @Override
    public Single<FavouriteMealEntity> getFavoriteById(String mealId) {
        return Single.create(emitter -> {
            String uid = getUserId();
            if (uid == null) {
                emitter.onError(new Exception("User not logged in"));
                return;
            }

            firestore.collection(USERS_COLLECTION).document(uid)
                    .collection(FAVORITES_COLLECTION).document(mealId).get()
                    .addOnSuccessListener(doc -> {
                        if (!emitter.isDisposed()) {
                            if (doc.exists()) {
                                emitter.onSuccess(doc.toObject(FavouriteMealEntity.class));
                            } else {
                                emitter.onError(new Exception("Meal not found"));
                            }
                        }
                    })
                    .addOnFailureListener(e -> {
                        if (!emitter.isDisposed()) emitter.onError(e);
                    });
        });
    }
}
