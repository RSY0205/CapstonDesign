package com.example.savehometraining.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.savehometraining.Login.json.JoinData;
import com.example.savehometraining.Login.json.JoinResponse;
import com.example.savehometraining.Login.json.PartData;
import com.example.savehometraining.Login.json.PartResponse;
import com.example.savehometraining.MainActivity;
import com.example.savehometraining.R;
import com.example.savehometraining.retrofit.RetrofitClient;
import com.example.savehometraining.retrofit.ServiceApi;
import com.example.savehometraining.ui.calendar.MyAdaptSchedule;
import com.example.savehometraining.ui.calendar.json.DeleteFitnessResponse;
import com.example.savehometraining.ui.calendar.json.LoadDayInfoResponse;
import com.example.savehometraining.ui.calendar.json.LoadDayInfodata;
import com.example.savehometraining.ui.calendar.json.ModifyFitnessData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Thread.sleep;

public class Part_preference extends AppCompatActivity {

    private CheckBox checkbox_shoulder;
    private CheckBox checkbox_arm;
    private CheckBox checkbox_Chest;
    private CheckBox checkbox_back;
    private CheckBox checkbox_abdominal;
    private CheckBox checkbox_core;
    private CheckBox checkbox_Hip;
    private CheckBox checkbox_thigh;
    private CheckBox checkbox_Wholebody;
    private Button button_part_ok;
    private Button button_part_cancel;
    int check[]={1,1,1,1,1,1,1,1,1};
    // [0]-어깨, [1]-팔, [2]-가슴, [3]-등, [4]-복부, [5]-코어, [6]-엉덩이, [7]-허벅지, [8]-전신
    String User[]=new String[10];


    ServiceApi service = RetrofitClient.getClient().create(ServiceApi.class);
    //String user_id;

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.part_preference);


        //user_id= getIntent().getStringExtra("user_id");


        User[0]=getIntent().getStringExtra("user_name");
        User[1]=getIntent().getStringExtra("user_birth");
        User[2]=getIntent().getStringExtra("user_gender");
        User[3]=getIntent().getStringExtra("user_id");
        User[4]=getIntent().getStringExtra("user_pw");
        User[5]=getIntent().getStringExtra("user_pwcheck");
        User[6]=getIntent().getStringExtra("user_nick");
        User[7]=getIntent().getStringExtra("user_phone");
        User[8]=getIntent().getStringExtra("user_cm");
        User[9]=getIntent().getStringExtra("user_kg");


        checkbox_shoulder = (CheckBox) findViewById(R.id.checkbox_part_shoulder);
        checkbox_arm = (CheckBox) findViewById(R.id.checkbox_part_arm);
        checkbox_Chest = (CheckBox) findViewById(R.id.checkbox_part_Chest);
        checkbox_back = (CheckBox) findViewById(R.id.checkbox_part_back);
        checkbox_abdominal = (CheckBox) findViewById(R.id.checkbox_part_abdominal);
        checkbox_core = (CheckBox) findViewById(R.id.checkbox_part_core);
        checkbox_Hip = (CheckBox) findViewById(R.id.checkbox_part_Hip);
        checkbox_thigh = (CheckBox) findViewById(R.id.checkbox_part_thigh);
        checkbox_Wholebody = (CheckBox) findViewById(R.id.checkbox_part_Wholebody);
        button_part_ok=(Button)findViewById(R.id.Button_part_ok);
        button_part_cancel=(Button)findViewById(R.id.Button_part_cancle);


        button_part_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //회원가입 실행
                //startJoin(new JoinData(User));
                //finish();

                Intent exercise_preference = new Intent(getApplicationContext(), Exercise_preference.class);
                //Intent exercise_preference = new Intent(getApplicationContext(), Test_preference.class);

                exercise_preference.putExtra("user_name", User[0]);
                exercise_preference.putExtra("user_birth", User[1]);
                exercise_preference.putExtra("user_gender", User[2]);
                exercise_preference.putExtra("user_id", User[3]);
                exercise_preference.putExtra("user_pw", User[4]);
                exercise_preference.putExtra("user_pwcheck", User[5]);
                exercise_preference.putExtra("user_nick", User[6]);
                exercise_preference.putExtra("user_phone", User[7]);
                exercise_preference.putExtra("user_cm", User[8]);
                exercise_preference.putExtra("user_kg", User[9]);
                exercise_preference.putExtra("0", check[0]);
                exercise_preference.putExtra("1", check[1]);
                exercise_preference.putExtra("2", check[2]);
                exercise_preference.putExtra("3", check[3]);
                exercise_preference.putExtra("4", check[4]);
                exercise_preference.putExtra("5", check[5]);
                exercise_preference.putExtra("6", check[6]);
                exercise_preference.putExtra("7", check[7]);
                exercise_preference.putExtra("8", check[8]);

                startActivity(exercise_preference);
                finish();

            }
        });

        button_part_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup=new Intent(getApplicationContext(), Connectsignup.class);
                startActivity(signup);
                finish();
            }
        });

        checkbox_shoulder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    check[0]=6;
                }else{
                    check[0]=1;
                }
            }
        }); // 어깨 선호도 체크 이벤트처리 리스너

        checkbox_arm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    check[1]=6;
                }else{
                    check[1]=1;
                }
            }
        }); // 팔 선호도 체크 이벤트처리 리스너

        checkbox_Chest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    check[2]=6;
                }else{
                    check[2]=1;
                }
            }
        }); // 가슴 선호도 체크 이벤트처리 리스너

        checkbox_back.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    check[3]=6;
                }else{
                    check[3]=1;
                }
            }
        }); // 등 선호도 체크 이벤트처리 리스너

        checkbox_abdominal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    check[4]=6;
                }else{
                    check[4]=1;
                }
            }
        }); // 복부 선호도 체크 이벤트처리 리스너

        checkbox_core.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    check[5]=6;
                }else{
                    check[5]=1;
                }
            }
        }); // 코어 선호도 체크 이벤트처리 리스너

        checkbox_Hip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    check[6]=6;
                }else{
                    check[6]=1;
                }
            }
        }); // 엉덩이 선호도 체크 이벤트처리 리스너

        checkbox_thigh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    check[7]=6;
                }else{
                    check[7]=1;
                }
            }
        }); // 허벅지 선호도 체크 이벤트처리 리스너


        checkbox_Wholebody.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    check[8]=6;
                }else{
                    check[8]=1;
                }
            }
        }); // 전신 선호도 체크 이벤트처리 리스너


    }
/*
    private void Part(PartData data) {
        service.Part(data).enqueue(new Callback<PartResponse>() {
            @Override
            public void onResponse(Call<PartResponse> call, Response<PartResponse> response) {
                PartResponse result = response.body();
                Log.e("연결 성공", result.getMessage());
                if(result.getResult()=="true"){
                    //쓰고싶은거
                    Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<PartResponse> call, Throwable t) {
                Log.e("로그인 에러 발생", t.getMessage());
            }
        });
    }

    private void startJoin(JoinData data) {
        service.userJoin(data).enqueue(new Callback<JoinResponse>() {
            @Override
            public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {
                JoinResponse result = response.body();
                Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                if(result.getResult()=="true") // 회원가입 성공시
                {
                    Toast.makeText(getApplicationContext(), "가입을 환영합니다!!", Toast.LENGTH_SHORT).show();
                    Part(new PartData(User[3],check[0],check[1],check[2],check[3],check[4],check[5],check[6],check[7],check[8])); // 선호도 조사한거 post
                }


            }

            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러 발생", t.getMessage());
            }
        });
    }   //회원가입 서버 통신
*/

        /*
        // sleep함수
        try
        {
            sleep(500); // 0.5초 기다림
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }*/

}
