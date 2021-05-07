package com.example.savehometraining.ui.calendar.json;

import com.google.gson.annotations.SerializedName;

public class DeleteFitnessData {
    @SerializedName("id")
    private int Fit_number;

    public DeleteFitnessData(int Fit_number){
        this.Fit_number=Fit_number;
    }
}
