package com.example.savehometraining.Login.json;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LoginResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("result")
    private String result;

    @SerializedName("User_name")
    private String User_name;

    @SerializedName("User_birth")
    private String User_birth;

    @SerializedName("User_gender")
    private String User_gender;

    @SerializedName("User_id")
    private String User_id;

    @SerializedName("User_nickname")
    private String User_nickname;

    @SerializedName("User_phone")
    private String User_phone;

    @SerializedName("User_height")
    private String User_height;

    @SerializedName("User_weight")
    private String User_weight;

    @SerializedName("User_strength")
    private String User_strength;

    @SerializedName("User_goal")
    private String User_goal;

    public String getMessage() {
        return message;
    }

    public ArrayList<String> getUserInfo() {
        ArrayList<String>userinfo= new ArrayList();
        userinfo.add(User_name);userinfo.add(User_birth);userinfo.add(User_gender);userinfo.add(User_id);userinfo.add(null);userinfo.add(null);
        userinfo.add(User_nickname);userinfo.add(User_phone);userinfo.add(User_weight);userinfo.add(User_strength);userinfo.add(User_goal);
        return userinfo;
    }
    //public String getUser_name(){return User_name;}

    public String getResult(){return result;}
}
