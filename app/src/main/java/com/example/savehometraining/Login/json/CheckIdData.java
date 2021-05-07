package com.example.savehometraining.Login.json;

import com.google.gson.annotations.SerializedName;

public class CheckIdData {
    @SerializedName("User_id")
    private String User_id;

    public CheckIdData (String User_id){
        this.User_id=User_id;
    }
}
