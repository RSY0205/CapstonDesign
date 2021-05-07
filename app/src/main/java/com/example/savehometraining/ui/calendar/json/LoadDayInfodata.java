package com.example.savehometraining.ui.calendar.json;

import com.google.gson.annotations.SerializedName;

public class LoadDayInfodata {
    @SerializedName("Calendar_date")
    String Calendar_date;
    @SerializedName("User_id")
    String User_id;

    public LoadDayInfodata(String Calendar_date,String User_id){
        this.Calendar_date=Calendar_date;
        this.User_id=User_id;
    }
}
