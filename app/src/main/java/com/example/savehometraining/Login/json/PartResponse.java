package com.example.savehometraining.Login.json;

import com.example.savehometraining.ui.calendar.json.LoadDayInfoResponse;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PartResponse {

    public List<PartResponse.response> response =new ArrayList<>();
    public List<PartResponse.response> getResponse(){return response;}
    @SerializedName("message") private String message;
    @SerializedName("result") private String result;

    public String getMessage() { return message; }
    public String getResult() {
        return result;
    }

    public class response{
        //@SerializedName("id")  int id;
        @SerializedName("Part_arm") String Part_arm;
        @SerializedName("Part_Chest") String Part_Chest;
        @SerializedName("Part_back") String Part_back;
        @SerializedName("Part_abdominal") String Part_abdominal;
        @SerializedName("Part_core") String Part_core;
        @SerializedName("Part_Hip") String Part_Hip;
        @SerializedName("Part_shoulder") String Part_shoulder;
        @SerializedName("Part_thigh") String Part_thigh;
        @SerializedName("Part_Wholebody") String Part_Wholebody;


        //public int getid () { return id; }
        public String getPart_arm(){return Part_arm;}
        public String getPart_Chest(){return Part_Chest;}
        public String getPart_back(){return Part_back;}
        public String getPart_abdominal(){return Part_abdominal;}
        public String getPart_core(){return Part_core;}
        public String getPart_Hip(){return Part_Hip;}
        public String getPart_shoulder(){return Part_shoulder;}
        public String getPart_thigh(){return Part_thigh;}
        public String getPart_Wholebody(){return Part_Wholebody;}
    }
}
