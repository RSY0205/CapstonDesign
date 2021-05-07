package com.example.savehometraining.ui.Routine;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savehometraining.R;
import com.example.savehometraining.retrofit.RetrofitClient;
import com.example.savehometraining.retrofit.ServiceApi;
import com.example.savehometraining.ui.Routine.json.LoadExceriseInfoData;
import com.example.savehometraining.ui.Routine.json.LoadExerciseInfoResponse;
import com.example.savehometraining.ui.Routine.json.RoutineTestData;
import com.example.savehometraining.ui.Routine.json.RoutineTestResponse;
import com.example.savehometraining.ui.community.MyAdapterBoard;
import com.example.savehometraining.ui.community.json.LoadCommunityData;
import com.example.savehometraining.ui.community.json.LoadCommunityResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnectRecommendRoutine extends AppCompatActivity {
    ListView RoutineListView, ExceriseListView;
    ArrayAdapter<CharSequence>adspin;   //스피너 문자 배열
    MyadapterRecommendRoutine adapter;   //운동추천목록 어댑터
    ServiceApi service = RetrofitClient.getClient().create(ServiceApi.class);

    int[] exceriselist=new int[]{0,0,0,0,0,0,0,0,0};
    //0.어깨 1.팔, 2.가슴, 3.등, 4.복부, 5.코어, 6.엉덩이, 7.허벅지 ,8.전신
    public List<RoutineTestResponse.response> Routinelist;  //루틴 리스트
    public List<LoadExerciseInfoResponse.response> ExcersiseInfo;//운동 리스트
    public List<LoadExerciseInfoResponse.response> ExcersisePartInfo;//운동 부분 리스트

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.routine_recommend);
        RoutineListView=(ListView)findViewById(R.id.listview_rotuinelist);
        ExceriseListView=(ListView)findViewById(R.id.listview_exerciselist);
        LoadRoutineList(new RoutineTestData("1"));      //루틴 리스트 불러오기

    /*                          스피너 시작                  */
        Spinner spin=(Spinner)findViewById(R.id.routine_spinnerexercise);
        spin.setPrompt("운동 부위를 선택하세요");

        //Spinner 자체에서 선택된 부위 이름만 출력 되게 레이아웃 지정
        adspin=ArrayAdapter.createFromResource(
                this,
                R.array.excerise_list,
                android.R.layout.simple_spinner_dropdown_item
        );

    //드랍다운 팝업에는 라디오 버튼도 출력되게 지정
    adspin.setDropDownViewResource(android.R.layout.simple_spinner_item);
    spin.setAdapter(adspin);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {

                for(int i=0;i<9;i++){exceriselist[i]=0;}    //배열 초기화
                //if(pos==0){for(int i=0;i<9;i++){exceriselist[i]=2;}}    //선택 받은 값들만 불러오기
                //else{exceriselist[pos=1]=2;}
               LoadExerciseInfo(new LoadExceriseInfoData(1),pos);

            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
    });
/*              스피너 종료              */

    }
    //루틴 목록 읽어오기
   private void LoadRoutineList(RoutineTestData data){
        MyadapterRecommendRoutine mMyAdapter=new MyadapterRecommendRoutine();
        service.RoutineTest(data).enqueue(new Callback<RoutineTestResponse>() {
            @Override
            public void onResponse(Call<RoutineTestResponse> call, Response<RoutineTestResponse> response) {
                RoutineTestResponse result=response.body();
                if(result.getResult()=="true"){
                    Routinelist=result.getResponse();
                    for(int i = 0; i<result.getResponse().size(); i++){
                        mMyAdapter.addItem(result.getResponse().get(i).getName(), result.getResponse().get(i).getCount(), result.getResponse().get(i).getCount());
                    }
                    RoutineListView.setAdapter(mMyAdapter);
                }
            }

            @Override
            public void onFailure(Call<RoutineTestResponse> call, Throwable t) {
                Log.e("통신 실패","sad");
            }
        });
   }

   private void LoadExerciseInfo(LoadExceriseInfoData data,int pos){
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

                    for (int i = 0; i < ExcersiseInfo.size(); i += 2) {

                        exercisename1 = ExcersiseInfo.get(i).getExercise_title();
                        if (i + 1 >= ExcersiseInfo.size()) { exercisename2 = "0"; }//운동 수가 홀수 일 경우 2번째 출력 방지
                        else{exercisename2 = ExcersiseInfo.get(i + 1).getExercise_title();}

                        mMyAdapter.addItem(exercisename1, exercisename2);
                        }
                    }

                else {
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
   }

}
