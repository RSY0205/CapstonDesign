package com.example.savehometraining.ui.Routine.json;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RoutineRecommendResponse {
    public List<RoutineRecommendResponse.response> response =new ArrayList<>();
    public List<RoutineRecommendResponse.response> getResponse(){return response;}
    @SerializedName("message") private String message;
    @SerializedName("result") private String result;
    public String getMessage () { return message; }
    public String getResult () { return result; }

    public class response{
        @SerializedName("name") String name;
        @SerializedName("count") String count;
        @SerializedName("set") String set;
        //public ArrayList<Board> Board = new ArrayList<Board>();

        public String getName () { return name; }
        public String getCount () { return count; }
        public String getSet () { return set; }
    }
}
