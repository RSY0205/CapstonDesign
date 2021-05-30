package com.example.savehometraining.Login.json;

import com.google.gson.annotations.SerializedName;

public class CheckNicknameData {
    @SerializedName("User_nickname")
    private String User_nickname;

    public CheckNicknameData (String User_nickname){
        this.User_nickname=User_nickname;
    }
}
