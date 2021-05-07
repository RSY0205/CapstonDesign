package com.example.savehometraining.ui.calendar.json;

import com.google.gson.annotations.SerializedName;

public class LoadCalendarDayData {
    @SerializedName("User_id")
    private String User_id;

    public LoadCalendarDayData(String User_id){
        this.User_id=User_id;
    }
}
