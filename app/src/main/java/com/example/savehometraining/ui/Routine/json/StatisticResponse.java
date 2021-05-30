package com.example.savehometraining.ui.Routine.json;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class StatisticResponse {

    public List<StatisticResponse.response> response =new ArrayList<>();
    public List<StatisticResponse.response> getResponse(){return response;}
    public class response{
        @SerializedName("data")
        String[] data;
        public String[]getData(){return data;}
        /*
        response[] 리스트 위치별 값
        0.시간별
        1.분
        2.초
        3.빈도
        4.개수별 정렬
        5.개수
        6.빈도
         */
    }



    @SerializedName("message") private String message;
    @SerializedName("result") private String result;

    public String getMessage() { return message; }
    public String getResult() { return result; }

}

