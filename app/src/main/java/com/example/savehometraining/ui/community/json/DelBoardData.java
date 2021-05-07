package com.example.savehometraining.ui.community.json;

import com.google.gson.annotations.SerializedName;

public class DelBoardData {
    @SerializedName("Board_number")
    private int Board_number;


    public DelBoardData(int Board_number){
        this.Board_number=Board_number;
    }
}
