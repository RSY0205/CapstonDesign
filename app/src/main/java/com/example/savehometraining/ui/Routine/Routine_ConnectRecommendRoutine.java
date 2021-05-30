package com.example.savehometraining.ui.Routine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.savehometraining.MainActivity;
import com.example.savehometraining.R;
import com.example.savehometraining.retrofit.RetrofitClient;
import com.example.savehometraining.retrofit.ServiceApi;
import com.example.savehometraining.ui.Routine.json.FavoriteExerciseData;
import com.example.savehometraining.ui.Routine.json.FavoriteExerciseResponse;
import com.example.savehometraining.ui.Routine.json.LoadExceriseInfoData;
import com.example.savehometraining.ui.Routine.json.LoadExerciseInfoResponse;
import com.example.savehometraining.ui.Routine.json.RecommendExerciseData;
import com.example.savehometraining.ui.Routine.json.RecommendExerciseResponse;
import com.example.savehometraining.ui.Routine.json.RoutineRecommendData;
import com.example.savehometraining.ui.Routine.json.RoutineRecommendResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Routine_ConnectRecommendRoutine extends AppCompatActivity {
    ListView RoutineListView;
    ListView ExceriseListView;
    Button  Button_LoadLocalRoutine,Button_SaveLocalRoutine;
    public static Context context_main;
    ArrayAdapter<CharSequence>adspin;   //스피너 문자 배열
    public MyadapterRoutine adapter;   //운동추천목록 어댑터
    ServiceApi service = RetrofitClient.getClient().create(ServiceApi.class);
    static MyadapterRoutine mMyAdapter_RecommendRoutine=new MyadapterRoutine();
    int FavoriteExercise=0,RecommedExericse=0;
    ArrayList<String> userinfo= ((MainActivity)MainActivity.context_main).Userinfo;
    //0.이름, 1.생년월일, 2.성별, 3.아이디, 4.비밀번호(null), 5.중복 비밀번호(null), 6.닉네임, 7.전화번호, 8.키, 9.체중, 10.힘, 11.목표

    int[] exceriselist=new int[]{0,0,0,0,0,0,0,0,0};
    //0.어깨 1.팔, 2.가슴, 3.등, 4.복부, 5.코어, 6.엉덩이, 7.허벅지 ,8.전신

    public List<RoutineRecommendResponse.response> Routinelist;  //루틴 리스트
    public List<LoadExerciseInfoResponse.response> ExcersiseInfo;//운동 리스트
    public List<LoadExerciseInfoResponse.response> ExcersisePartInfo;//운동 부분 리스트
    FloatingActionButton routine_start;

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.routine_recommend);
        RoutineListView=(ListView)findViewById(R.id.listview_rotuinelist);
        ExceriseListView=(ListView)findViewById(R.id.listview_exerciselist);
        Button_LoadLocalRoutine=(Button)findViewById(R.id.button_RoadLocalRountine);
        Button_SaveLocalRoutine=(Button)findViewById(R.id.button_SaveLocalRountine);
        routine_start=(FloatingActionButton)findViewById(R.id.routine_start);
        RoutineFragment.sel_page=0;

        LoadRoutineList(new RoutineRecommendData(userinfo.get(3)));      //루틴 리스트 불러오기
        RecommendExercise(new RecommendExerciseData(userinfo.get(3)));
        FavoriteExercise(new FavoriteExerciseData(userinfo.get(3)));

        Button_LoadLocalRoutine.setVisibility(View.GONE);
        Button_LoadLocalRoutine.setEnabled(false);
        Button_SaveLocalRoutine.setVisibility(View.GONE);
        Button_SaveLocalRoutine.setEnabled(false);

    /*                          스피너                  */

        Spinner spin=(Spinner)findViewById(R.id.routine_spinnerexercise);
        spin.setPrompt("운동 부위를 선택하세요");

        //Spinner 자체에서 선택된 부위 이름만 출력 되게 레이아웃 지정
        adspin=ArrayAdapter.createFromResource(
                this,
                R.array.excerise_list,
                android.R.layout.simple_spinner_dropdown_item
        );

    adspin.setDropDownViewResource(android.R.layout.simple_spinner_item);
    spin.setAdapter(adspin);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {

                for(int i=0;i<9;i++){exceriselist[i]=0;}    //배열 초기화
               LoadExerciseInfo(new LoadExceriseInfoData(1),pos);

            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
    });

    routine_start.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent Hardware = new Intent(getApplicationContext(), Routine_HardwareActivity.class);
            startActivity(Hardware);
            finish();
        }
    }); //루틴 시작 클릭 이벤트

    }

    public void onBackPressed() {
        Toast.makeText(this, "뒤로가기를 누르셨습니다.", Toast.LENGTH_SHORT).show();
        MyadapterRoutine.mItems.clear();
        super.onBackPressed();
    }//종료시 루틴 항목 초기화

   private void LoadRoutineList(RoutineRecommendData data){

        service.RoutineRecommend(data).enqueue(new Callback<RoutineRecommendResponse>() {
            @Override
            public void onResponse(Call<RoutineRecommendResponse> call, Response<RoutineRecommendResponse> response) {
                RoutineRecommendResponse result=response.body();
                if(result.getResult()=="true"){
                    Routinelist=result.getResponse();
                    for(int i = 0; i<result.getResponse().size(); i++){
                        mMyAdapter_RecommendRoutine.addItem(result.getResponse().get(i).getName(), result.getResponse().get(i).getCount(), result.getResponse().get(i).getCount());
                    }
                    RoutineListView.setAdapter(mMyAdapter_RecommendRoutine);
                }
            }

            @Override
            public void onFailure(Call<RoutineRecommendResponse> call, Throwable t) {
                Log.e("통신 실패","sad");
            }
        });
   }//루틴 목록 읽어오기

   public void LoadExerciseInfo(LoadExceriseInfoData data,int pos){
        MyadapterExerciseList mMyAdapter=new MyadapterExerciseList();

        service.LoadExceriseInfo(data).enqueue(new Callback<LoadExerciseInfoResponse>() {
            @Override
            public void onResponse(Call<LoadExerciseInfoResponse> call, Response<LoadExerciseInfoResponse> response) {
                LoadExerciseInfoResponse result = response.body();
                Log.e("통신 완료", "good");
                Log.e(Integer.toString(pos), "good");
                String exercisename1 = null;
                String exercisename2 = null;

                ExcersiseInfo = result.getResponse();
                ExcersisePartInfo=new ArrayList<>();

                //pos의 경우 0번 전체,1번 어깨, 2번...순
                if (pos == 0) {

                    for (int i = 0; i < ExcersiseInfo.size(); i += 2)
                    {

                        exercisename1 = ExcersiseInfo.get(i).getExercise_title();
                        if (i + 1 >= ExcersiseInfo.size()) { exercisename2 = "0"; }//운동 수가 홀수 일 경우 2번째 출력 방지
                        else {exercisename2 = ExcersiseInfo.get(i + 1).getExercise_title();}

                        mMyAdapter.addItem(exercisename1, exercisename2);
                        }
                    }
                else if(pos==10)
                    {
                        //추천 운동
                        if(FavoriteExercise>=0&&FavoriteExercise<9)
                        {
                            for (int i = 0; i < ExcersiseInfo.size(); i ++) {
                                int[] ExerciseData = ExcersiseInfo.get(i).getExcercise_Data();  //운동 부위 배열
                                Log.e(ExcersiseInfo.get(i).getExercise_title(),"");
                                if (ExerciseData[FavoriteExercise] >= 1) { ExcersisePartInfo.add(ExcersiseInfo.get(i)); }//pos-1=excericseData위치와 동일
                            }
                            for (int i = 0; i < ExcersisePartInfo.size(); i += 2) {
                                exercisename1 = ExcersisePartInfo.get(i).getExercise_title();
                                if (i + 1 >= ExcersisePartInfo.size()) { exercisename2 = "0"; }//운동 수가 홀수 일 경우 2번째 출력 방지
                                else{exercisename2 = ExcersisePartInfo.get(i + 1).getExercise_title();}

                                mMyAdapter.addItem(exercisename1, exercisename2);
                            }
                        }
                    }
                else if(pos==11)
                    {
                        //선호 운동
                        if(RecommedExericse>=0&&RecommedExericse<9)
                        {
                            for (int i = 0; i < ExcersiseInfo.size(); i ++) {
                                int[] ExerciseData = ExcersiseInfo.get(i).getExcercise_Data();  //운동 부위 배열
                                Log.e(ExcersiseInfo.get(i).getExercise_title(),"");
                                if (ExerciseData[RecommedExericse] == 2) { ExcersisePartInfo.add(ExcersiseInfo.get(i)); }//pos-1=excericseData위치와 동일
                            }
                            for (int i = 0; i < ExcersisePartInfo.size(); i += 2) {
                                exercisename1 = ExcersisePartInfo.get(i).getExercise_title();
                                if (i + 1 >= ExcersisePartInfo.size()) { exercisename2 = "0"; }//운동 수가 홀수 일 경우 2번째 출력 방지
                                else{exercisename2 = ExcersisePartInfo.get(i + 1).getExercise_title();}

                                mMyAdapter.addItem(exercisename1, exercisename2);
                            }
                        }
                    }

                else
                    {
                    for (int i = 0; i < ExcersiseInfo.size(); i ++) {
                        int[] ExerciseData = ExcersiseInfo.get(i).getExcercise_Data();  //운동 부위 배열
                        Log.e(ExcersiseInfo.get(i).getExercise_title(),"");
                        if (ExerciseData[pos - 1] != 0) { ExcersisePartInfo.add(ExcersiseInfo.get(i)); }//pos-1=excericseData위치와 동일
                        }

                    for (int i = 0; i < ExcersisePartInfo.size(); i += 2) {
                        exercisename1 = ExcersisePartInfo.get(i).getExercise_title();
                        if (i + 1 >= ExcersisePartInfo.size()) { exercisename2 = "0"; }//운동 수가 홀수 일 경우 2번째 출력 방지
                        else{exercisename2 = ExcersisePartInfo.get(i + 1).getExercise_title();}

                        mMyAdapter.addItem(exercisename1, exercisename2);
                        }
                    }
                ExceriseListView.setAdapter(mMyAdapter);
                }

            @Override
            public void onFailure(Call<LoadExerciseInfoResponse> call, Throwable t) {
                Log.e("통신 실패","sad");
            }
        });
   }//운동 목록 읽어오기

    private void RecommendExercise(RecommendExerciseData data){
        service.RecommendExercise(data).enqueue(new Callback<RecommendExerciseResponse>() {
            @Override
            public void onResponse(Call<RecommendExerciseResponse> call, Response<RecommendExerciseResponse> response) {
                RecommendExerciseResponse result=response.body();
                Log.e("추천부위 위치:",result.getResult());
                if(Integer.parseInt(result.getResult())!=-1){
                    RecommedExericse=Integer.parseInt(result.getResult());
                }
            }

            @Override
            public void onFailure(Call<RecommendExerciseResponse> call, Throwable t) {
                Log.e("통신 실패","sad");
            }
        });
    }
    private void FavoriteExercise(FavoriteExerciseData data){
        service.FavoriteExercise(data).enqueue(new Callback<FavoriteExerciseResponse>() {
            @Override
            public void onResponse(Call<FavoriteExerciseResponse> call, Response<FavoriteExerciseResponse> response) {
                FavoriteExerciseResponse result=response.body();
                Log.e("선호부위 위치:",result.getResult());
                if(Integer.parseInt(result.getResult())!=-1){
                    FavoriteExercise=Integer.parseInt(result.getResult());
                }
            }

            @Override
            public void onFailure(Call<FavoriteExerciseResponse> call, Throwable t) {
                Log.e("통신 실패","sad");
            }
        });
    }

}
