package com.example.yummyplanner.data.meals.cloud;


public class RemotePlannedMealResponse {

    private String mealId;
    private String date;
    private String uid;
    private String status;
    private long updatedAt;

    public RemotePlannedMealResponse() {}

    public RemotePlannedMealResponse(String mealId, String date, String uid, String status, long updatedAt) {
        this.mealId = mealId;
        this.date = date;
        this.uid = uid;
        this.status = status;
        this.updatedAt = updatedAt;
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
