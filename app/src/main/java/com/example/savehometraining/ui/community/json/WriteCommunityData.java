package com.example.savehometraining.ui.community.json;

import com.google.gson.annotations.SerializedName;

public class WriteCommunityData {
    //@SerializedName("Board_number")
    //private String Board_number;
    @SerializedName("User_id")
    private String User_id;
    @SerializedName("User_nickname")
    private String User_nickname;
    @SerializedName("Board_date")
    private String Board_date;
    @SerializedName("Board_title")
    private String Board_title;
    @SerializedName("Board_context")
    private String Board_context;
    @SerializedName("Board_countcomment")
    private String Board_countcomment;
    @SerializedName("Img_num")
    private int Img_num;

    //@SerializedName("img_name")
    //private String img_name;

    public WriteCommunityData(String User_id, String User_nickname,
                              String Board_date, String Board_title, String Board_context, String Board_countcoment, int Img_num)
            //,String img_name
    {
        //this.Board_number=Board_number;
        this.User_id=User_id; this.User_nickname=User_nickname; this.Board_date=Board_date;
        this.Board_title=Board_title; this.Board_context=Board_context; this.Board_countcomment=Board_countcoment;
        this.Img_num=Img_num;
    }
}
