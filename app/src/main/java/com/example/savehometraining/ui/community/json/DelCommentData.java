package com.example.savehometraining.ui.community.json;

import com.google.gson.annotations.SerializedName;

public class DelCommentData {
    @SerializedName("Comments_number")
    private int Comments_number;


    public DelCommentData(int Comments_number){
        this.Comments_number=Comments_number;
    }
}
