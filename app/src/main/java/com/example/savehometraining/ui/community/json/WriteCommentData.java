package com.example.savehometraining.ui.community.json;

import com.google.gson.annotations.SerializedName;

public class WriteCommentData {
    @SerializedName("Board_number")
    private int Board_number;
    @SerializedName("User_id")
    private String User_id;
    @SerializedName("User_nickname")
    private String User_nickname;
    @SerializedName("Comments_date")
    private String Comments_date;
    @SerializedName("Comments_context")
    private String Comments_context;


    public WriteCommentData(int Board_number,String User_id,String User_nickname,String Comments_date,String Board_context){
        this.Board_number=Board_number;this.User_id=User_id;this.User_nickname=User_nickname;
        this.Comments_date=Comments_date;this.Comments_context=Board_context;
    }

}
