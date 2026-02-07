package com.example.yummyplanner.data.model.response;

import java.util.List;

public interface NetworkCallback<T> {
    void onSuccess(List<T> data);
    void onFailure(String message);
}
