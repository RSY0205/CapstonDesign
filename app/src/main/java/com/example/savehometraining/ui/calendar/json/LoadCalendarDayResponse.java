package com.example.savehometraining.ui.calendar.json;

import com.example.savehometraining.ui.community.json.LoadCommentResponse;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LoadCalendarDayResponse {
    public List<LoadCalendarDayResponse.response> response =new ArrayList<>();
    public List<LoadCalendarDayResponse.response> getResponse(){return response;}
    @SerializedName("message") private String message;
    @SerializedName("result") private String result;

    public String getMessage() { return message; }
    public String getResult() {
        return result;
    }

    public class response{
        @SerializedName("Calendar_date")
        String Calendar_date;

        public String getCalendar_date(){return Calendar_date;}
    }


}
