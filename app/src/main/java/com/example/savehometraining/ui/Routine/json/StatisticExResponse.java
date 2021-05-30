package com.example.savehometraining.ui.Routine.json;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class StatisticExResponse {
    public List<StatisticExResponse.response> response =new ArrayList<>();
    public List<StatisticExResponse.response> getResponse(){return response;}
    public class response{
        @SerializedName("date")
        String date;
        @SerializedName("count")
        float count;
        @SerializedName("min")
        float min;
        @SerializedName("second")
        float second;
        public String getDate(){return date;}
        public float getCount(){return count;}
        public void setCount(float count){this.count=count;}
        public float getMin(){return min;}
        public void setMin(float min){this.min=min;}
        public float getSecond(){return second;}
        public void setSecond(float second){this.second=second;}
        /*
        0.날짜
        1.갯수(분)
        2.초
         */
    }



    @SerializedName("message") private String message;
    @SerializedName("result") private String result;

    public String getMessage() { return message; }
    public String getResult() { return result; }

}
