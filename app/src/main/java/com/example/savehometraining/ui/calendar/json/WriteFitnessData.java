package com.example.savehometraining.ui.calendar.json;

import com.google.gson.annotations.SerializedName;

public class WriteFitnessData {
    @SerializedName("User_id")
    String User_id;
    @SerializedName("Calendar_date")
    String Calendar_date;
    @SerializedName("Calendar_title")
    String Calendar_title;
    @SerializedName("Calendar_count")
    String Calendar_count;
    @SerializedName("Calendar_min")
    String Calendar_min;
    @SerializedName("Calendar_sec")
    String Calendar_sec;
    @SerializedName("Calendar_memo")
    String Calendar_memo;

    public WriteFitnessData(String User_id, String Calendar_date, String Calenar_title, String Calendar_count, String Calendar_min, String Calendar_sec, String Calendar_memo)
    {
        this.User_id=User_id;
        this.Calendar_date=Calendar_date;
        this.Calendar_title=Calenar_title;
        this.Calendar_count=Calendar_count;
        this.Calendar_min=Calendar_min;
        this.Calendar_sec=Calendar_sec;
        this.Calendar_memo=Calendar_memo;
    }
}