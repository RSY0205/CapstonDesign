package com.example.savehometraining.ui.Routine.json;

import com.google.gson.annotations.SerializedName;

public class FavoriteExerciseData {
    @SerializedName("User_id")
    String User_id;

    public FavoriteExerciseData(String User_id){
        this.User_id=User_id;
    }
}
