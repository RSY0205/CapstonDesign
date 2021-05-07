package com.example.savehometraining.Login.json;

import com.google.gson.annotations.SerializedName;

public class ExData {

    @SerializedName("User_id")
    String User_id;
    @SerializedName("Exercise_name")
    int[] Excercise_name;


    public ExData(String User_id, int[] exercise) {
        this.User_id=User_id;
        this.Excercise_name=exercise;
    }
}
