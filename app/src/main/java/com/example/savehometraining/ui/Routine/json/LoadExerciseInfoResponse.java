package com.example.savehometraining.ui.Routine.json;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LoadExerciseInfoResponse {

    public List<LoadExerciseInfoResponse.response> response =new ArrayList<>();
    public List<LoadExerciseInfoResponse.response> getResponse(){return response;}
    @SerializedName("message") private String message;
    @SerializedName("result") private String result;

    public String getMessage() { return message; }
    public String getResult() { return result; }

    public class response{
        @SerializedName("Exercise_title") String Exercise_title;
        public String getExercise_title(){return Exercise_title;}
        @SerializedName("Excerise_Data") int[] Excercise_Data;
        public int[] getExcercise_Data(){return Excercise_Data;}
    }
}
