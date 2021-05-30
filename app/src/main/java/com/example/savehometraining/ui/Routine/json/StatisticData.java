package com.example.savehometraining.ui.Routine.json;

import com.google.gson.annotations.SerializedName;

public class StatisticData {
    @SerializedName("User_id")
    String User_id;
    @SerializedName("Month")
    String Month;
    public  StatisticData(String User_id,String Month){
        this.User_id=User_id; this.Month=Month;
    }
}
