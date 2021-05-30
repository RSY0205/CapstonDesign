package com.example.savehometraining.ui.Routine;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.savehometraining.MainActivity;
import com.example.savehometraining.R;
import com.example.savehometraining.retrofit.RetrofitClient;
import com.example.savehometraining.retrofit.ServiceApi;
import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;

public class Routine_ConnectDetail extends AppCompatActivity {
    ServiceApi service = RetrofitClient.getClient().create(ServiceApi.class);
    ArrayList<String> userinfo= ((MainActivity)MainActivity.context_main).Userinfo;
    ArrayAdapter<CharSequence> adspin;   //스피너 문자 배열
    private BarChart barChartexcericse,barChartpart;

    protected void OnCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.routine_detail);


    }
}
