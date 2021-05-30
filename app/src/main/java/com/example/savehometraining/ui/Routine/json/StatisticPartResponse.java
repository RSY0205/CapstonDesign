package com.example.savehometraining.ui.Routine.json;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class StatisticPartResponse {
    public List<StatisticPartResponse.response> response =new ArrayList<>();
    public List<StatisticPartResponse.response> getResponse(){return response;}
    public class response{
        @SerializedName("date")
        String date;
        public String getDate(){return date;}

    }



    @SerializedName("message") private String message;
    @SerializedName("result") private String result;

    public String getMessage() { return message; }
    public String getResult() { return result; }
}
