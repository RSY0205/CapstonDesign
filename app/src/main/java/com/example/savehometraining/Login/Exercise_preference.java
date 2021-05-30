package com.example.savehometraining.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.savehometraining.Login.json.ExData;
import com.example.savehometraining.Login.json.ExDatae;
import com.example.savehometraining.Login.json.ExResponse;
import com.example.savehometraining.Login.json.ExResponsea;
import com.example.savehometraining.Login.json.JoinData;
import com.example.savehometraining.Login.json.JoinResponse;
import com.example.savehometraining.Login.json.PartData;
import com.example.savehometraining.Login.json.PartResponse;
import com.example.savehometraining.R;
import com.example.savehometraining.retrofit.RetrofitClient;
import com.example.savehometraining.retrofit.ServiceApi;
import com.example.savehometraining.ui.calendar.Calendar_ConnectNoticeboard;
import com.example.savehometraining.ui.calendar.MyAdaptSchedule;
import com.example.savehometraining.ui.calendar.json.LoadDayInfoResponse;
import com.example.savehometraining.ui.calendar.json.LoadDayInfodata;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Exercise_preference extends AppCompatActivity {
    private ListView listview;
    public List<ExResponse.response> Exercise;
    ArrayList<Preperence_item> items;
    ServiceApi service = RetrofitClient.getClient().create(ServiceApi.class);
    private Button button_exercise_ok;
    private Button button_exercise_cancel;
    String User[]=new String[10];
    int[] check_result;
    int []check=new int[9];
    String a="임시변수";
    //MyAdaptExercise mMyAdapter = new MyAdaptExercise();
    MyAdaptExercise mMyAdapter;

    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.exercise_preference);

        listview = (ListView)findViewById(R.id.Listview_exercise);
        button_exercise_ok =(Button)findViewById(R.id.Button_exercise_ok);
        button_exercise_cancel =(Button)findViewById(R.id.Button_excercise_cancle);

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


        for(int i=0;i<9;i++)
        {
            a=String.valueOf(i);
            check[i]=getIntent().getIntExtra(a,9999);
        }



        button_exercise_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ok 버튼 클릭 리스너
                check_result=mMyAdapter.getCheck();
                startJoin(new JoinData(User));

                finish();
            }
        });

        button_exercise_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup=new Intent(getApplicationContext(), Connectsignup.class);
                startActivity(signup);
                finish();
                //Ex(new ExData(User[3],check_result));
            }
        });


        Ex(new ExDatae());

        //mMyAdapter = new MyAdaptExercise(items);
        //listview.setAdapter(mMyAdapter);
    }

    private void Ex(ExDatae data) { // 받기만
        //MyAdaptExercise mMyAdapter = new MyAdaptExercise();
        items=new ArrayList<Preperence_item>();
        service.Exercise(data).enqueue(new Callback<ExResponse>() {
            @Override
            public void onResponse(Call<ExResponse> call, Response<ExResponse> response) {
                ExResponse result = response.body();
                Log.e("연결 성공", result.getMessage());
                if(result.getResult()=="true"){
                    Exercise=result.getResponse();

                    for(int i=0;i<result.getResponse().size();i++){
                        //mMyAdapter.addItem(result.getResponse().get(i).getExercise_title(),result.getResponse().get(i+1).getExercise_title(),result.getResponse().get(i+2).getExercise_title());
                        String s=result.getResponse().get(i).getExercise_title();
                        boolean b=false;
                        Preperence_item item=new Preperence_item(b,s);
                        items.add(item);
                        //mMyAdapter.addItem(b,s)
                    }

                    mMyAdapter = new MyAdaptExercise(items);
                    listview.setAdapter(mMyAdapter);
                    //listview.setAdapter(mMyAdapter);

                }
            }

            @Override
            public void onFailure(Call<ExResponse> call, Throwable t) {
                Log.e("로그인 에러 발생1", t.getMessage());
            }
        });
    }



    private void Ex(ExData data) { // 데이터 넘겨주기
        //MyAdaptExercise mMyAdapter = new MyAdaptExercise();
        service.Exercised(data).enqueue(new Callback<ExResponsea>() {
            @Override
            public void onResponse(Call<ExResponsea> call, Response<ExResponsea> response) {
                ExResponsea result = response.body();
                Log.e("연결 성공", result.getMessage());
                if(result.getResult()=="true"){

                }
            }

            @Override
            public void onFailure(Call<ExResponsea> call, Throwable t) {
                Log.e("운동넘겨주기에러", t.getMessage());
            }
        });
    }

    /*
    private void dataSetting() {
        MyAdaptExercise mMyAdapter = new MyAdaptExercise();
        /* 리스트뷰에 어댑터 등록
        listview.setAdapter(mMyAdapter);
    }*/

    private void startJoin(JoinData data) {
        service.userJoin(data).enqueue(new Callback<JoinResponse>() {
            @Override
            public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {
                JoinResponse result = response.body();

                if(result.getResult()=="true") // 회원가입 성공시
                {
                    Toast.makeText(getApplicationContext(), "가입을 환영합니다!!", Toast.LENGTH_SHORT).show();
                    Log.e("가입환영", "ㅋㅋㅋㅋ");
                    Part(new PartData(User[3],check[0],check[1],check[2],check[3],check[4],check[5],check[6],check[7],check[8])); // 선호도 조사한거 post
                }


            }

            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러 발생2", t.getMessage());
            }
        });
    }   //회원가입 서버 통신

    private void Part(PartData data) {
        service.Part(data).enqueue(new Callback<PartResponse>() {
            @Override
            public void onResponse(Call<PartResponse> call, Response<PartResponse> response) {
                PartResponse result = response.body();
                Log.e("연결 성공", result.getMessage());
                if(result.getResult()=="true"){
                    //쓰고싶은거
                    Ex(new ExData(User[3],check_result));
                    //Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<PartResponse> call, Throwable t) {
                Log.e("로그인 에러 발생3", t.getMessage());
            }
        });
    }
}
