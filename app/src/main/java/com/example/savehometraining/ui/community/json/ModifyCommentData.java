package com.example.savehometraining.ui.community.json;

import com.google.gson.annotations.SerializedName;

public class ModifyCommentData {
    @SerializedName("Comments_number")
    int Comments_number;
    @SerializedName("Comments_context")
    String Comments_context;
    @SerializedName("Comments_date")
    String Comments_date;

    public ModifyCommentData(int Comments_number, String Comments_context, String Comments_date){
        this.Comments_number=Comments_number;
        this.Comments_context=Comments_context;
        this.Comments_date=Comments_date;
    }
}
