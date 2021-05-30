package com.example.savehometraining.ui.calendar.json;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DeleteFitnessResponse {
    /*public List<DeleteFitnessResponse.response> response =new ArrayList<>();
    public List<DeleteFitnessResponse.response> getResponse(){return response;}*/
    @SerializedName("message") private String message;
    @SerializedName("result") private String result;


    public String getMessage() { return message; }
    public String getResult() { return result; }

}
