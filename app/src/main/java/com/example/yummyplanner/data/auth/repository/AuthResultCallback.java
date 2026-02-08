package com.example.yummyplanner.data.auth.repository;
import com.example.yummyplanner.data.auth.model.User;

public interface AuthResultCallback {
    void onSuccess(User user);
    void onError(String message);
}
