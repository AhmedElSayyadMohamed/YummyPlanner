package com.example.yummyplanner.data.meals.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.example.yummyplanner.data.meals.model.MealdetailsItemModel;

@Entity(tableName = "planned_meals", primaryKeys = {"id_meal", "planned_date"})
public class PlannedMealEntity {

    @NonNull
    @ColumnInfo(name = "id_meal")
    private String idMeal;

    @NonNull
    @ColumnInfo(name = "planned_date")
    private String plannedDate;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "thumbnail")
    private String thumbnail;

    @ColumnInfo(name = "updatedAt")
    private long updatedAt;


    public PlannedMealEntity() {
        this.idMeal = "";
        this.plannedDate = "";
    }

    @NonNull
    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(@NonNull String idMeal) {
        this.idMeal = idMeal;
    }

    @NonNull
    public String getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(@NonNull String plannedDate) {
        this.plannedDate = plannedDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static PlannedMealEntity fromFavourite(FavouriteMealEntity favourite, String date) {
        PlannedMealEntity plannedMeal = new PlannedMealEntity();
        plannedMeal.setIdMeal(favourite.getIdMeal());
        plannedMeal.setName(favourite.getName());
        plannedMeal.setThumbnail(favourite.getThumbnail());
        plannedMeal.setPlannedDate(date);
        return plannedMeal;
    }

    public static PlannedMealEntity fromMealDetails(MealdetailsItemModel meal, String date) {
        PlannedMealEntity plannedMeal = new PlannedMealEntity();
        plannedMeal.setIdMeal(meal.getId());
        plannedMeal.setName(meal.getName());
        plannedMeal.setThumbnail(meal.getThumbNail());
        plannedMeal.setPlannedDate(date);
        return plannedMeal;
    }
}
