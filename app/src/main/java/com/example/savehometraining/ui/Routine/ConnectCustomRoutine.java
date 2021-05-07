package com.example.savehometraining.ui.Routine;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.savehometraining.R;
import com.example.savehometraining.retrofit.RetrofitClient;
import com.example.savehometraining.retrofit.ServiceApi;
import com.example.savehometraining.ui.Routine.json.LoadExceriseInfoData;
import com.example.savehometraining.ui.Routine.json.LoadExerciseInfoResponse;
import com.example.savehometraining.ui.Routine.json.RoutineTestData;
import com.example.savehometraining.ui.Routine.json.RoutineTestResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnectCustomRoutine extends AppCompatActivity {
    ListView RoutineListView1, RoutineListView2;
    //ArrayAdapter<CharSequence> adspin;   //스피너 문자 배열
    MyadapterRecommendRoutine adapter;   //운동추천목록 어댑터
    //ServiceApi service = RetrofitClient.getClient().create(ServiceApi.class);

    //int[] exceriselist=new int[]{0,0,0,0,0,0,0,0,0};
    //0.어깨 1.팔, 2.가슴, 3.등, 4.복부, 5.코어, 6.엉덩이, 7.허벅지 ,8.전신
    //public List<RoutineTestResponse.response> Routinelist;  //루틴 리스트
    //public List<LoadExerciseInfoResponse.response> ExcersiseInfo;//운동 리스트
    //public List<LoadExerciseInfoResponse.response> ExcersisePartInfo;//운동 부분 리스트

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.nonmoon_page);
        //RoutineListView=(ListView)findViewById(R.id.listview_rotuinelist);
        RoutineListView1=(ListView)findViewById(R.id.listview_nonmoon_img1);
        RoutineListView2=(ListView)findViewById(R.id.listview_nonmoon_img2);
        LoadExerciseInfo();
        RecommandExerciseInfo();



        //Spinner 자체에서 선택된 부위 이름만 출력 되게 레이아웃 지정


    }

    private void RecommandExerciseInfo() {
        MyadapterExerciseList mMyAdapter=new MyadapterExerciseList();
        mMyAdapter.addItem("힐 킥", "케틀벨 스윙");
        mMyAdapter.addItem("스쿼트", "0");
        RoutineListView2.setAdapter(mMyAdapter);
    }
    //루틴 목록 읽어오기


    private void LoadExerciseInfo(){
        MyadapterExerciseList mMyAdapter=new MyadapterExerciseList();
        mMyAdapter.addItem("버드 독", "플랭크");
        mMyAdapter.addItem("힙 브릿지", "런지");
        RoutineListView1.setAdapter(mMyAdapter);
    }



}