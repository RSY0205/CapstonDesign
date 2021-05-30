package com.example.savehometraining.Login.json;

import com.google.gson.annotations.SerializedName;

public class JoinResponse {

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
