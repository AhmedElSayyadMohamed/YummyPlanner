package com.example.yummyplanner.data.model.response;

@FunctionalInterface
public interface FailureCallback {
    void onFailure(String message);
}
