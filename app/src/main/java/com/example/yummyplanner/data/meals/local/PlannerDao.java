package com.example.yummyplanner.data.meals.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.yummyplanner.data.meals.local.entity.PlannedMealEntity;
import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface PlannerDao {

    @Query("SELECT * FROM planned_meals WHERE planned_date = :plannedDate")
    Flowable<List<PlannedMealEntity>> getPlannedMealsByDate(String plannedDate);

    @Query("SELECT * FROM planned_meals")
    Flowable<List<PlannedMealEntity>> getAllPlannedMeals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertPlannedMeal(PlannedMealEntity meal);

    @Delete
    Completable deletePlannedMeal(PlannedMealEntity meal);
}
