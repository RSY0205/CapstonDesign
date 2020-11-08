package com.example.savehometraining.Login;

import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("User_id")
    String User_id;

    @SerializedName("User_pwd")
    String User_pwd;

    public LoginData(String User_id, String User_pwd) {
        this.User_id=User_id;
        this.User_pwd=User_pwd;
    }
}
