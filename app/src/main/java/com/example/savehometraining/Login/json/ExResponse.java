package com.example.savehometraining.Login.json;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ExResponse {

    public List<ExResponse.response> response =new ArrayList<>();
    public List<ExResponse.response> getResponse(){return response;}
    @SerializedName("message") private String message;
    @SerializedName("result") private String result;

    public String getMessage() { return message; }
    public String getResult() {
        return result;
    }

    public class response{
        //@SerializedName("id")  int id;
        @SerializedName("Exercise_title") String Exercise_title;

        //public int getid () { return id; }
        public String getExercise_title(){return Exercise_title;}
    }
}