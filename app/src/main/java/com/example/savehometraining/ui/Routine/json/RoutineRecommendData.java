package com.example.savehometraining.ui.Routine.json;

import com.google.gson.annotations.SerializedName;

public class RoutineRecommendData {
    @SerializedName("User_id")
    String User_id;

    public RoutineRecommendData(String User_id){
        this.User_id=User_id;
    }
}
