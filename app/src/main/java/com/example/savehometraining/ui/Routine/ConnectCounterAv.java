package com.example.savehometraining.ui.Routine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.savehometraining.Login.json.LoginData;
import com.example.savehometraining.Login.json.LoginResponse;
import com.example.savehometraining.MainActivity;
import com.example.savehometraining.R;
import com.example.savehometraining.TrainingActivity;
import com.example.savehometraining.retrofit.RetrofitClient;
import com.example.savehometraining.retrofit.ServiceApi;
import com.example.savehometraining.ui.Routine.json.FitnessData;
import com.example.savehometraining.ui.Routine.json.FitnessResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnectCounterAv extends AppCompatActivity {
    ServiceApi service = RetrofitClient.getClient().create(ServiceApi.class);
    EditText title;
    EditText main;
    Button Button_start;
    Button Button_stop;
    Button Button_ok;
    Button Button_Cancel;
    ImageButton Button_top;
    ImageButton Button_bottom;
    ArrayList<String> userinfo= ((MainActivity)MainActivity.context_main).Userinfo;
//    ArrayList<sensor>gyro;
    int i;
    int keep=0;
    Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다
    // 년월일시분초 14자리 포멧
    //SimpleDateFormat fourteen_format = new SimpleDateFormat("yyyy년MM월dd일HH시mm분ss초");
    SimpleDateFormat fourteen_format = new SimpleDateFormat("yyyy년MM월d일");
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.routine_counter);



        Button_top = (ImageButton) findViewById(R.id.imageButtontop);
        Button_bottom = (ImageButton) findViewById(R.id.imageButtonbottom);
        Button_ok = (Button) findViewById(R.id.buttonOK);
        Button_Cancel= (Button) findViewById(R.id.buttonCancle);
        //Button_start= (Button) findViewById(R.id.buttonstart);
        //Button_stop= (Button) findViewById(R.id.buttonstop);
        title=(EditText) findViewById(R.id.editTextNumber);
        main=(EditText) findViewById(R.id.textViewtitle);

        String message = getIntent().getStringExtra("title");
        int custom = getIntent().getIntExtra("custom",1);
        if(custom==1) {main.setHint(message);}
        if(custom==0) {main.setText(message);main.setEnabled(false);}
        i=0;
        title.setText(String.format("%d",i));

        //gyro= (ArrayList<sensor>) ((ConnectBluetooth)ConnectBluetooth.context_main).Gyro;
        //String temp=Float.toString(gyro.get(i).getX())+Float.toString(gyro.get(i).getY())+Float.toString(gyro.get(i).getZ());
        //Toast.makeText(ConnectCounterAv.this, temp, Toast.LENGTH_SHORT).show();

        /*
        Button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        Button_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keep=0;
            }
        });
        */


        Button_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.getText().toString()!=""){
                    i=Integer.parseInt(title.getText().toString());
                }

                else {
                    i=0;
                    title.setText(String.format("%d",i));
                }

                i++;

                title.setText(String.format("%d",i));
            }
        });

        Button_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.getText().toString()!=""){
                    i=Integer.parseInt(title.getText().toString());
                }
                else {
                    i=0;
                    title.setText(String.format("%d",i));
                }

                if(i>0) i--;
                title.setText(String.format("%d",i));
            }
        });

        Button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveCalendar(new FitnessData(userinfo.get(3),fourteen_format.format(date_now),main.getText().toString(),title.getText().toString(), "루틴"));
            }
        });

        Button_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keep=0;
                finish();
            }
        });

    }
    private void SaveCalendar(FitnessData data) {
        service.Fitness(data).enqueue(new Callback<FitnessResponse>() {
            @Override
            public void onResponse(Call<FitnessResponse> call, Response<FitnessResponse> response) {
                FitnessResponse result = response.body();
                Toast.makeText(ConnectCounterAv.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                if(result.getResult()=="true"){
                    finish();}
            }

            @Override
            public void onFailure(Call<FitnessResponse> call, Throwable t) {
                Toast.makeText(ConnectCounterAv.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러 발생", t.getMessage());
            }
        });
    }
}
