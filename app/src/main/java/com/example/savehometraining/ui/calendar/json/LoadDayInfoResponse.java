package com.example.savehometraining.ui.calendar.json;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LoadDayInfoResponse {
    public List<LoadDayInfoResponse.response> response =new ArrayList<>();
    public List<LoadDayInfoResponse.response> getResponse(){return response;}
    @SerializedName("message") private String message;
    @SerializedName("result") private String result;

    public String getMessage() { return message; }
    public String getResult() {
        return result;
    }

    public class response{
        @SerializedName("id")  int id;
        @SerializedName("Calendar_date") String Calendar_date;
        @SerializedName("Calendar_title") String Calendar_title;
        @SerializedName("Calendar_min") String Calendar_min;
        @SerializedName("Calendar_sec") String Calendar_sec;
        @SerializedName("Calendar_count") String Calendar_count;
        @SerializedName("Calendar_memo") String Calendar_memo;

        public int getid () { return id; }
        public String getCalendar_date(){return Calendar_date;}
        public String getCalendar_title(){return Calendar_title;}
        public String getCalendar_min(){return Calendar_min;}
        public String getCalendar_sec(){return Calendar_sec;}
        public String getCalendar_count(){return Calendar_count;}
        public String getCalendar_memo(){return Calendar_memo;}
    }
}