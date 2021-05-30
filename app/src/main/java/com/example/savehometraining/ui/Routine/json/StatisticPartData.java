package com.example.savehometraining.ui.Routine.json;

import com.google.gson.annotations.SerializedName;

public class StatisticPartData {
    @SerializedName("User_id")
    String User_id;
    @SerializedName("Month")
    String Month;
    @SerializedName("Part")
    String Part;
    public  StatisticPartData(String User_id,String Month,String Part){
        this.User_id=User_id; this.Month=Month; this.Part=Part;
    }
}
