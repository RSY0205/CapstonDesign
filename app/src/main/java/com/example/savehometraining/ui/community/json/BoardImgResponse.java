package com.example.savehometraining.ui.community.json;

import com.google.gson.annotations.SerializedName;

public class BoardImgResponse {
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
