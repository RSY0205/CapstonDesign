package com.example.savehometraining.ui.Routine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.savehometraining.MainActivity;
import com.example.savehometraining.R;
import com.example.savehometraining.retrofit.RetrofitClient;
import com.example.savehometraining.retrofit.ServiceApi;
import com.example.savehometraining.ui.Routine.json.StatisticData;
import com.example.savehometraining.ui.Routine.json.StatisticResponse;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Routine_statistic extends AppCompatActivity {
    ServiceApi service = RetrofitClient.getClient().create(ServiceApi.class);
    ArrayList<String> userinfo= ((MainActivity)MainActivity.context_main).Userinfo;
    ArrayAdapter<CharSequence>adspin;   //스피너 문자 배열
    public List<StatisticResponse.response> Statistic; // 다음페이지에 정보 넘겨줄때 사용할것?
    SimpleDateFormat fourteen_format = new SimpleDateFormat("MM월");
    Button detail, cancel;
    TextView tv_part1, tv_part2, tv_part3;
    Button TimeStatic,CountStatic,FreqStatic;
    static String Select_month;
    private BarChart barChart;
    Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다
    // 년월일시분초 14자리 포멧

   //ArrayList<Integer> integersList = new ArrayList<>(); // ArrayList 선언
    ArrayList<Float> floatList = new ArrayList<>(); // ArrayList 선언
    ArrayList<String> labelList = new ArrayList<>(); // ArrayList 선언

    protected void onCreate(Bundle saveInstanceState) {
        // 시작시 서버와 통신 메서드 실행해서 뛰워주는거도 낫배드

        super.onCreate(saveInstanceState);
        setContentView(R.layout.routine_statistics);

        detail = (Button) findViewById(R.id.Button_detail);
        cancel = (Button) findViewById(R.id.Button_cancle);

        TimeStatic= (Button) findViewById(R.id.Button_timestastics);
        CountStatic= (Button) findViewById(R.id.Button_countstastics);
        FreqStatic= (Button) findViewById(R.id.Button_freqstastics);

        tv_part1 = (TextView) findViewById(R.id.tv_part_1);
        tv_part2 = (TextView) findViewById(R.id.tv_part_2);
        tv_part3 = (TextView) findViewById(R.id.tv_part_3);

        barChart = (BarChart)findViewById(R.id.bar_chart);


        /*                          스피너                  */

        Spinner spin=(Spinner)findViewById(R.id.routine_spinnermonth);
        spin.setPrompt("월을 선택해주세요");

        //Spinner 자체에서 선택된 부위 이름만 출력 되게 레이아웃 지정
        adspin= ArrayAdapter.createFromResource(
                this,
                R.array.month,
                android.R.layout.simple_spinner_dropdown_item
        );
        String time=fourteen_format.format(date_now);
        //Log.e("현재달:",time);
        adspin.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spin.setAdapter(adspin);
        spin.setSelection(getIndex(spin,time));
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                Select_month=arg0.getItemAtPosition(pos).toString();
                Log.e("선택한 달",Select_month);
                Statistic(new StatisticData(userinfo.get(3),Select_month));

            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });



        TimeStatic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //0.시간별,1.분,2.초,3.빈도,4.개수별정렬,5.개수,6.빈도

                String exceriseName[]=Statistic.get(0).getData();
                String min[]=Statistic.get(1).getData();
                String sec[]=Statistic.get(2).getData();

                labelList.clear();
                for(int i=0;i<exceriseName.length;i++){labelList.add(exceriseName[i]);}
                floatList.clear();
                for(int i=0;i<min.length;i++){
                    String string_time=min[i]+"."+sec[i];
                    float time=Float.parseFloat(string_time);
                    floatList.add(time);
                }

                BarChartGraph(labelList, floatList,"시간 기준");
            }
        });

        CountStatic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String[] exceriseName=Statistic.get(4).getData();
                String[] exceriseCount=Statistic.get(5).getData();
                labelList.clear();
                for(int i=0;i<exceriseName.length;i++){labelList.add(exceriseName[i]);}
                floatList.clear();
                for(int i=0; i<exceriseCount.length;i++){
                    float time=Float.parseFloat(exceriseCount[i]);
                    floatList.add(time);
                }
                BarChartGraph(labelList, floatList,"갯수 기준");
            }
        });

        FreqStatic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String[] exceriseName=Statistic.get(4).getData();
                String[] exceriseFreq=Statistic.get(6).getData();
                labelList.clear();
                for(int i=0;i<exceriseName.length;i++){labelList.add(exceriseName[i]);}
                floatList.clear();
                for(int i=0; i<exceriseFreq.length;i++){
                    float time=Float.parseFloat(exceriseFreq[i]);
                    floatList.add(time);
                }
                BarChartGraph(labelList, floatList,"빈도 기준");
            }
        });

        detail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent detail=new Intent(getApplicationContext(), Routine_statistic_detail.class);
                startActivity(detail);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
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
        barChart.setDescription(" ");

        ArrayList<String> labels = new ArrayList<String>();
        for(int i=0; i < labelList.size(); i++){
            labels.add((String) labelList.get(i));
        }

        BarData data = new BarData(labels,depenses); // 라이브러리 v3.x 사용하면 에러 발생함
        depenses.setColors(ColorTemplate.LIBERTY_COLORS); //

        barChart.setData(data);
        barChart.animateXY(1000,1000);
        barChart.invalidate();
    }//그래프 함수

    private void Statistic(StatisticData data) {
        service.Statistic(data).enqueue(new Callback<StatisticResponse>() {
            @Override
            public void onResponse(Call<StatisticResponse> call, Response<StatisticResponse> response) {
                StatisticResponse result = response.body();
                Statistic=result.getResponse();
                //Toast.makeText(Routine_statistic.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                if(result.getResult()=="true"){

                    String[] ex_name,ex_count,ex_freq;
                    for(int i=0;i<result.getResponse().size();i++){
                        String[] test=result.getResponse().get(i).getData();
                        {for(int j=0;j<test.length;j++){Log.e("결과",test[j]);}}
                    }
                    String part[]=Statistic.get(7).getData();
                    tv_part1.setText(part[0]);
                    tv_part2.setText(part[1]);
                    tv_part3.setText(part[2]);
                }
                //0.시간별,1.분,2.초,3.빈도,4.개수별정렬,5.개수,6.빈도
            }

            @Override
            public void onFailure(Call<StatisticResponse> call, Throwable t) {
                Toast.makeText(Routine_statistic.this, "통신 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("통신 에러 발생", t.getMessage());
            }
        });
    }

    private int getIndex(Spinner spinner, String item){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(item)){
                return i;
            }
        }
        return 0;
    }//원하는 내용값의 spinner의 위치반환


}