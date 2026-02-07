package com.example.yummyplanner.data.model.response;

import com.example.yummyplanner.data.model.Category;

import java.util.List;

public interface CategoryCallback {
    void onCategoriesSuccess(List<Category> categories);
    void onCategoriesFailure(String error);
}
