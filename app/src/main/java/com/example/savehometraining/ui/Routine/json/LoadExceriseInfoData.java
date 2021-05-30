package com.example.savehometraining.ui.Routine.json;

import com.google.gson.annotations.SerializedName;

public class LoadExceriseInfoData {
    @SerializedName("Requset")
    int Request;
    public LoadExceriseInfoData(int Request) {
        this.Request=Request;
    }
}
