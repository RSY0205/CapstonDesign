package com.example.savehometraining.Login.json;

import com.google.gson.annotations.SerializedName;

public class JoinData {
    @SerializedName("User_name")
    private String User_name;

    @SerializedName("User_birth")
    private String User_birth;

    @SerializedName("User_gender")
    private String User_gender;

    @SerializedName("User_id")
    private String User_id;

    @SerializedName("User_pwd")
    private String User_pwd;

    @SerializedName("User_nickname")
    private String User_nickname;

    @SerializedName("User_phone")
    private String User_phone;

    @SerializedName("User_height")
    private String User_height;

    @SerializedName("User_weight")
    private String User_weight;



    public JoinData(String user[]){
        this.User_name=user[0]; this.User_birth=user[1]; this.User_gender=user[2]; this.User_id=user[3];this.User_pwd=user[4];//user[5]같은 pw
        this.User_nickname=user[6]; this.User_phone=user[7]; this.User_height=user[8]; this.User_weight=user[9];


    }
}
