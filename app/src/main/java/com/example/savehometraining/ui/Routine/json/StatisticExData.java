package com.example.savehometraining.ui.Routine.json;

import com.google.gson.annotations.SerializedName;

public class StatisticExData {
    @SerializedName("User_id")
    String User_id;

    @SerializedName("Month")
    String Month;
    @SerializedName("Exercise")
    String Excercise;

    public  StatisticExData(String User_id,String Month,String Excerise){
        this.User_id=User_id; this.Month=Month; this.Excercise=Excerise;
    }

    public String getExcercise(){return this.Excercise;}
}
