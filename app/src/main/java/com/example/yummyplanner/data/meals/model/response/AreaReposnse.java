package com.example.yummyplanner.data.meals.model.response;

import com.example.yummyplanner.data.meals.model.Area;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AreaReposnse {
    @SerializedName("meals")
    private List<Area> areas;

    public List<Area> getAreas(){
        return areas;
    }

}
