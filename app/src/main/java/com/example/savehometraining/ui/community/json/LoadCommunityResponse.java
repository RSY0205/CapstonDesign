package com.example.savehometraining.ui.community.json;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LoadCommunityResponse {
    public List<response> response =new ArrayList<>();
    public List<response> getResponse(){return response;}
    @SerializedName("message") private String message;
    @SerializedName("result") private String result;
    public String getMessage () { return message; }
    public String getResult () { return result; }

    public class response{
        @SerializedName("Board_number") int Board_number;
        @SerializedName("Board_title") String Board_title;
        @SerializedName("Board_context") String Board_context;
        @SerializedName("User_id") String User_id;
        @SerializedName("User_nickname") String User_nickname;
        @SerializedName("Board_date") String Board_date;
        @SerializedName("Board_count") int Board_count;
        @SerializedName("Board_countcomment") String Board_countcomment;
        @SerializedName("Img_num") int Img_num;
        //public ArrayList<Board> Board = new ArrayList<Board>();

        public int getBoard_number () { return Board_number; }
        public String getBoard_title () { return Board_title; }
        public String getBoard_context () { return Board_context; }
        public String getUser_id () { return User_id; }
        public String getUser_nickname () { return User_nickname; }
        public String getBoard_date () { return Board_date; }
        public int getBoard_count () { return Board_count; }
        public String getBoard_countcomment () { return Board_countcomment; }
        public int getImg_num(){return Img_num;}

    }
    /*
    public LoadCommunityResponse(){}

    public LoadCommunityResponse( int Board_number,String Board_title,String Board_context,String User_id,String User_nickname,
                                String Board_date,int Board_count,String Board_countcomment){
        //Board temp= new Board();
        this.Board_number=Board_number;
        this.Board_title=Board_title;
        this.Board_context=Board_context;
        this.User_id=User_id;
        this.User_nickname=User_nickname;
        this.Board_date=Board_date;
        this.Board_count=Board_count;
        this.Board_countcomment=Board_countcomment;


    }
    */

}
