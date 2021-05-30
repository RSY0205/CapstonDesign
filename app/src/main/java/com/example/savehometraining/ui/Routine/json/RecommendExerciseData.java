package com.example.savehometraining.ui.Routine.json;

import com.google.gson.annotations.SerializedName;

public class RecommendExerciseData {
    @SerializedName("User_id")
    String User_id;

    public  RecommendExerciseData(String User_id){
        this.User_id=User_id;
    }
}
