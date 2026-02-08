package com.example.yummyplanner.data.auth.social;

public interface SocialAuthCallback {
    void onSuccess(String idToken);
    void onError(String message);
}
