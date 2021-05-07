package com.example.savehometraining.Login.json;

import com.google.gson.annotations.SerializedName;

public class PartData {

    @SerializedName("User_id")
    String User_id;
    @SerializedName("Part_shoulder")
    int Part_shoulder;
    @SerializedName("Part_arm")
    int Part_arm;
    @SerializedName("Part_Chest")
    int Part_Chest;
    @SerializedName("Part_back")
    int Part_back;
    @SerializedName("Part_abdominal")
    int Part_abdominal;
    @SerializedName("Part_core")
    int Part_core;
    @SerializedName("Part_Hip")
    int Part_Hip;
    @SerializedName("Part_thigh")
    int Part_thigh;
    @SerializedName("Part_Wholebody")
    int Part_Wholebody;


    public PartData(String User_id, int Part_shoulder, int Part_arm, int Part_Chest, int Part_back, int Part_abdominal, int Part_core, int Part_Hip, int Part_thigh, int Part_Wholebody) {
        this.User_id=User_id;
        this.Part_shoulder=Part_shoulder;
        this.Part_arm=Part_arm;
        this.Part_Chest=Part_Chest;
        this.Part_back=Part_back;
        this.Part_abdominal=Part_abdominal;
        this.Part_core=Part_core;
        this.Part_Hip=Part_Hip;
        this.Part_thigh=Part_thigh;
        this.Part_Wholebody=Part_Wholebody;
    }
}
