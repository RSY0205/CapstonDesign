package com.example.savehometraining.ui.calendar.json;

import com.google.gson.annotations.SerializedName;

public class WriteFitnessResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("result")
    private String result;


    public String getMessage() {
        return message;
    }

    public String getResult() {
        return result;
    }
}
