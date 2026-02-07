package com.example.yummyplanner.data.model.response;

@FunctionalInterface
public interface OnSuccessCallback<T> {
    void onSuccess(T data);
}
