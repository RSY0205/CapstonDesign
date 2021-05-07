package com.example.savehometraining.ui.Routine.json;

import com.google.gson.annotations.SerializedName;

public class RoutineTestData {
    @SerializedName("Test")
    private String Test;

    public RoutineTestData(String Test){
        this.Test=Test;
    }
}
