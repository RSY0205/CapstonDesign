package com.example.savehometraining.ui.community.json;

import com.google.gson.annotations.SerializedName;

public class
ModifyBoardData {
    @SerializedName("Board_number")
    int Board_number;
    @SerializedName("Board_title")
    String  Board_title;
    @SerializedName("Board_context")
    String  Board_context;
    @SerializedName("Board_date")
    String  Board_date;

    public ModifyBoardData(int Board_number, String Board_title,String Board_context,String Board_date){
        this.Board_number=Board_number;
        this.Board_title=Board_title;
        this.Board_context=Board_context;
        this.Board_date=Board_date;
    }
}