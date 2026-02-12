package com.example.yummyplanner.data.meals.cloud;

import com.example.yummyplanner.data.meals.local.entity.PlannedMealEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FireStoreManager implements CloudRemoteDataSource {

    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    private static FireStoreManager instance;


    private FireStoreManager() {
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    // singleton
    public static synchronized FireStoreManager getInstance(){
        if(instance == null){
            instance = new FireStoreManager();
        }
        return  instance;
    }

    // get user id
    private String getUserId() {
        if (auth.getCurrentUser() != null) {
            return auth.getCurrentUser().getUid();
        }
        return null;
    }


    // add user document after register to our app
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

            firestore.collection("users")
                    .document(uid)
                    .set(user)
                    .addOnSuccessListener(unused -> {
                        if (!emitter.isDisposed())
                            emitter.onComplete();
                    })
                    .addOnFailureListener(e -> {
                        if (!emitter.isDisposed())
                            emitter.onError(e);
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

            firestore.collection("users")
                    .document(uid)
                    .collection("planner")
                    .add(plannerMap)
                    .addOnSuccessListener(documentReference -> {
                        if (!emitter.isDisposed())
                            emitter.onComplete();
                    })
                    .addOnFailureListener(e -> {
                        if (!emitter.isDisposed())
                            emitter.onError(e);
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

            firestore.collection("users")
                    .document(uid)
                    .collection("planner")
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {

                        List<PlannedMealEntity> list = new ArrayList<>();

                        for (DocumentSnapshot doc : queryDocumentSnapshots) {

                            String mealId = doc.getString("mealId");
                            String date = doc.getString("plannedDate");
                            Long updatedAt = doc.getLong("updatedAt");

                            PlannedMealEntity entity = new PlannedMealEntity();
                            entity.setIdMeal(mealId != null ? mealId : "");
                            entity.setPlannedDate(date);
                            entity.setUpdatedAt(updatedAt != null ? updatedAt : 0);

                            list.add(entity);
                        }

                        if (!emitter.isDisposed())
                            emitter.onSuccess(list);
                    })
                    .addOnFailureListener(e -> {
                        if (!emitter.isDisposed())
                            emitter.onError(e);
                    });

        });
    }

    @Override
    public Completable deletePlannerMeal(String remoteId) {

        return Completable.create(emitter -> {

            String uid = getUserId();
            if (uid == null) {
                emitter.onError(new Exception("User not logged in"));
                return;
            }

            firestore.collection("users")
                    .document(uid)
                    .collection("planner")
                    .document(remoteId)
                    .delete()
                    .addOnSuccessListener(unused -> {
                        if (!emitter.isDisposed())
                            emitter.onComplete();
                    })
                    .addOnFailureListener(e -> {
                        if (!emitter.isDisposed())
                            emitter.onError(e);
                    });

        });
    }

}
