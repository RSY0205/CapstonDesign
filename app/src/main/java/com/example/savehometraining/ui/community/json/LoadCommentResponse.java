package com.example.savehometraining.ui.community.json;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LoadCommentResponse {
    public List<LoadCommentResponse.response> response =new ArrayList<>();
    public List<LoadCommentResponse.response> getResponse(){return response;}
    @SerializedName("message") private String message;
    @SerializedName("result") private String result;

    public String getMessage() { return message; }
    public String getResult() {
        return result;
    }

    public class response {
        @SerializedName("Comments_number")
        int Comments_number;
        @SerializedName("Board_number")
        int Board_number;
        @SerializedName("User_id")
        String User_id;
        @SerializedName("User_nickname")
        String User_nickname;
        @SerializedName("Comments_context")
        String Comments_context;
        @SerializedName("Comments_date")
        String Comments_date;
        @SerializedName("Comments_parentnumber")
        int Comments_parentnumber;

        public int getComments_number(){return Comments_number;}
        public int getBoard_number(){return Board_number;}
        public String getUser_id(){return User_id;}
        public String getUser_nickname(){return User_nickname;}
        public String getComments_context(){return Comments_context;}
        public String getComments_date(){return Comments_date;}
        public int getComments_parentnumber(){return Comments_parentnumber;}


    }
}
