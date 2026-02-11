package com.example.yummyplanner.data.meals.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.yummyplanner.data.meals.local.entity.FavouriteMealEntity;

@Database(entities = {FavouriteMealEntity.class}, version = 1, exportSchema = false)
public abstract class MealDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "yummy_planner_database";
    private static volatile MealDatabase instance;

    public abstract FavoriteMealDao favoriteMealDao();

    public static synchronized MealDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    MealDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

}
