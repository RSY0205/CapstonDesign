package com.example.savehometraining.ui.Routine;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.savehometraining.MainActivity;
import com.example.savehometraining.R;
import com.example.savehometraining.retrofit.RetrofitClient;
import com.example.savehometraining.retrofit.ServiceApi;
import com.example.savehometraining.ui.Routine.json.LoadExceriseInfoData;
import com.example.savehometraining.ui.Routine.json.LoadExerciseInfoResponse;
import com.example.savehometraining.ui.Routine.json.StatisticExData;
import com.example.savehometraining.ui.Routine.json.StatisticExResponse;
import com.example.savehometraining.ui.Routine.json.StatisticPartData;
import com.example.savehometraining.ui.Routine.json.StatisticPartResponse;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Routine_statistic_detail extends AppCompatActivity {
    ServiceApi service = RetrofitClient.getClient().create(ServiceApi.class);
    ArrayList<String> userinfo= ((MainActivity)MainActivity.context_main).Userinfo;
    public List<StatisticExResponse.response> StatisticEx;
    public List<StatisticPartResponse.response> StatisticPart;
    String Select_Ex;
    String Select_Part;

    Button back;
    private BarChart barChart_exercise;
    ArrayList<Float> floatList_ex = new ArrayList<>(); // ArrayList 선언
    ArrayList<String> labelList_ex = new ArrayList<>(); // ArrayList 선언

    private BarChart barChart_part;
    ArrayList<Float> floatList_part = new ArrayList<>(); // ArrayList 선언
    ArrayList<String> labelList_part = new ArrayList<>(); // ArrayList 선언

    ArrayList excercise_list=new ArrayList<>();
    int position_excercise;    //스피너 위치 저장
    ArrayAdapter<String> adspin_exercise;   //스피너 문자 배열
    public List<LoadExerciseInfoResponse.response> ExcersiseInfo;//운동 리스트

    String Select_part_title;
    int position_part;    //스피너 위치 저장
    ArrayAdapter<CharSequence> adspin_part;   //스피너 문자 배열

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.routine_statistics_detail);
        back=(Button) findViewById(R.id.Button_back);

        barChart_exercise = (BarChart)findViewById(R.id.bar_chart_exercise);
        barChart_part = (BarChart)findViewById(R.id.bar_chart_part);


        /*운동 목록 스피너*/
        //운동 목록 불러오기
        excercise_list.add("운동 목록을 선택하세요!");
        LoadExcerciseList(new LoadExceriseInfoData(1),excercise_list);
        /*Spinner*/
        Spinner spin_exercise=(Spinner)findViewById(R.id.routine_spinnerexercise);

        //Spinner 자체에서 선택된 부위 이름만 출력 되게 레이아웃 지정
        adspin_exercise = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,excercise_list);
        //adspin=ArrayAdapter.createFromResource(this, R.array.excerise_list, android.R.layout.simple_spinner_dropdown_item);
        //adspin.setDropDownViewResource(android.R.layout.simple_spinner_item);
        //드랍다운 팝업에는 라디오 버튼도 출력되게 지정
        //adspin.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spin_exercise.setSelection(0);
        spin_exercise.setAdapter(adspin_exercise);
        spin_exercise.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                position_excercise=pos;

                Select_Ex=arg0.getItemAtPosition(pos).toString();
                Log.e(Select_Ex,"가 선택 되었습니다.");
                if(pos==0){}
                else{StatisticEx(new StatisticExData(userinfo.get(3),Routine_statistic.Select_month,Select_Ex));}

                }





            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        /*                          운동 부위 스피너                  */

        Spinner spin_part=(Spinner)findViewById(R.id.routine_spinnerpart);
        spin_part.setPrompt("운동 부위를 선택하세요");

        //Spinner 자체에서 선택된 부위 이름만 출력 되게 레이아웃 지정
        adspin_part=ArrayAdapter.createFromResource(
                this,
                R.array.part_list,
                android.R.layout.simple_spinner_dropdown_item
        );

        adspin_part.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spin_part.setAdapter(adspin_part);
        spin_part.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {

                Select_Part=arg0.getItemAtPosition(pos).toString();
                Log.e("선택 부위",Select_Part);
                if(pos==0){}
                else{ StatisticPart(new StatisticPartData(userinfo.get(3),Routine_statistic.Select_month,Select_Part));}




            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void BarChartGraph(ArrayList<String> labelList, ArrayList<Float> valList,String title) {
        // BarChart 메소드
        ArrayList<BarEntry> entries = new ArrayList<>();
        for(int i=0; i < valList.size();i++){
            entries.add(new BarEntry((Float) valList.get(i), i));
        }

        BarDataSet depenses = new BarDataSet (entries, title); // 변수로 받아서 넣어줘도 됨
        depenses.setAxisDependency(YAxis.AxisDependency.RIGHT);
        barChart_exercise.setDescription(" ");

        ArrayList<String> labels = new ArrayList<String>();
        for(int i=0; i < labelList.size(); i++){
            labels.add((String) labelList.get(i));
        }

        BarData data = new BarData(labels,depenses); // 라이브러리 v3.x 사용하면 에러 발생함
        depenses.setColors(ColorTemplate.LIBERTY_COLORS); //

        barChart_exercise.setData(data);
        barChart_exercise.animateXY(1000,1000);
        barChart_exercise.invalidate();
    }

    private void BarChartGraph_2(ArrayList<String> labelList, ArrayList<Float> valList,String title) {
        // BarChart 메소드
        ArrayList<BarEntry> entries = new ArrayList<>();
        for(int i=0; i < valList.size();i++){
            entries.add(new BarEntry((Float) valList.get(i), i));
        }

        BarDataSet depenses = new BarDataSet (entries, title); // 변수로 받아서 넣어줘도 됨
        depenses.setAxisDependency(YAxis.AxisDependency.RIGHT);
        barChart_part.setDescription(" ");

        ArrayList<String> labels = new ArrayList<String>();
        for(int i=0; i < labelList.size(); i++){
            labels.add((String) labelList.get(i));
        }

        BarData data = new BarData(labels,depenses); // 라이브러리 v3.x 사용하면 에러 발생함
        depenses.setColors(ColorTemplate.LIBERTY_COLORS); //

        barChart_part.setData(data);
        barChart_part.animateXY(1000,1000);
        barChart_part.invalidate();
    }

    private void LoadExcerciseList(LoadExceriseInfoData data,ArrayList<String> excerise_list){
        service.LoadExceriseInfo(data).enqueue(new Callback<LoadExerciseInfoResponse>() {
            @Override
            public void onResponse(Call<LoadExerciseInfoResponse> call, Response<LoadExerciseInfoResponse> response) {
                LoadExerciseInfoResponse result = response.body();
                ExcersiseInfo = result.getResponse();
                for (int i = 0; i < ExcersiseInfo.size(); i ++){
                    excerise_list.add(ExcersiseInfo.get(i).getExercise_title());
                }
            }

            @Override
            public void onFailure(Call<LoadExerciseInfoResponse> call, Throwable t) {

            }
        });
    }

    private void StatisticEx(StatisticExData data){
        service.StatisticEx(data).enqueue(new Callback<StatisticExResponse>() {
            @Override
            public void onResponse(Call<StatisticExResponse> call, Response<StatisticExResponse> response) {
                Log.e("불러오기 함수","성공");
                StatisticExResponse result= response.body();
                //StatisticEx.clear();
                StatisticEx=result.getResponse();
                labelList_ex.clear();
                floatList_ex.clear();
                if(data.getExcercise().equals("러닝")||data.getExcercise().equals("홈사이클")||data.getExcercise().equals("플랭크"))
                {
                    for(int i=0;i<StatisticEx.size();i++) {
                        if(i+1==StatisticEx.size()){break;}
                        String temp = StatisticEx.get(i + 1).getDate();
                        if (StatisticEx.get(i).getDate().equals(temp)) {
                            Log.e("중복 발견","O");
                            StatisticEx.get(i).setMin(StatisticEx.get(i).getMin() + StatisticEx.get(i + 1).getMin());
                            StatisticEx.get(i).setSecond(StatisticEx.get(i).getSecond() + StatisticEx.get(i + 1).getSecond());
                            StatisticEx.remove(i + 1);
                            Log.e("중복 삭제","O");
                            i = i - 1;
                        }
                    }//중복 검사

                    for(int i=0;i<StatisticEx.size();i++){
                        labelList_ex.add(StatisticEx.get(i).getDate());
                        float sec=StatisticEx.get(i).getSecond();
                        if(sec>60){
                            float temp= (float) ((sec%60)*0.01);
                            sec=sec/60+temp;}
                        else {
                            sec =(float)(sec * 0.01);
                        }
                        float time= (float) (StatisticEx.get(i).getMin()+sec);
                        floatList_ex.add(time);
                    }//그래프 그리기


                }
                else{
                    for(int i=0;i<StatisticEx.size();i++) {
                        if(i+1==StatisticEx.size()){break;}
                        String temp = StatisticEx.get(i + 1).getDate();
                        if (StatisticEx.get(i).getDate().equals(temp)) {
                            Log.e("중복 발견","O");
                            StatisticEx.get(i).setCount(StatisticEx.get(i).getCount() + StatisticEx.get(i + 1).getCount());
                            StatisticEx.remove(i + 1);
                            Log.e("중복 삭제","O");
                            i = i - 1;
                        }
                    }//중복 검사

                    for(int i=0;i<StatisticEx.size();i++){
                        labelList_ex.add(StatisticEx.get(i).getDate());
                        floatList_ex.add(StatisticEx.get(i).getCount());
                    }
                }
                BarChartGraph(labelList_ex,floatList_ex,Select_Ex);
            }

            @Override
            public void onFailure(Call<StatisticExResponse> call, Throwable t) {
                Toast.makeText(Routine_statistic_detail.this, "통신 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("통신 에러 발생", t.getMessage());
            }
        });
    }

    private void StatisticPart(StatisticPartData data){
        service.StatisticPart(data).enqueue(new Callback<StatisticPartResponse>() {
            @Override
            public void onResponse(Call<StatisticPartResponse> call, Response<StatisticPartResponse> response) {

                StatisticPartResponse result= response.body();
                StatisticPart=result.getResponse();
                labelList_part.clear();
                floatList_part.clear();
                float count=1;
                for(int i=0;i<StatisticPart.size();i++){
                    //Log.e("Part_date",StatisticPart.get(i).getDate());
                    for(int j=i+1;j<StatisticPart.size();){
                        if(StatisticPart.get(i).getDate().equals(StatisticPart.get(j).getDate())){
                         count=count+1;
                         StatisticPart.remove(j);
                         j=j-1;
                        }
                        j++;
                    }
                    floatList_part.add(count);
                    count=1;
                }
                for(int i=0;i<StatisticPart.size();i++){
                    labelList_part.add(StatisticPart.get(i).getDate());
                }
                BarChartGraph_2(labelList_part,floatList_part,Select_Part);//그래프 그리기
            }

            @Override
            public void onFailure(Call<StatisticPartResponse> call, Throwable t) {
                Toast.makeText(Routine_statistic_detail.this, "통신 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("통신 에러 발생", t.getMessage());
            }
        });
    }

}