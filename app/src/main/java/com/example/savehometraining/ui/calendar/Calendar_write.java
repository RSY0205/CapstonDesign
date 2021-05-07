package com.example.savehometraining.ui.calendar;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.savehometraining.MainActivity;
import com.example.savehometraining.R;
import com.example.savehometraining.retrofit.RetrofitClient;
import com.example.savehometraining.retrofit.ServiceApi;
import com.example.savehometraining.ui.Routine.ConnectCounterAv;
import com.example.savehometraining.ui.Routine.json.FitnessData;
import com.example.savehometraining.ui.Routine.json.FitnessResponse;
import com.example.savehometraining.ui.Routine.json.LoadExceriseInfoData;
import com.example.savehometraining.ui.Routine.json.LoadExerciseInfoResponse;
import com.example.savehometraining.ui.calendar.json.WriteFitnessData;
import com.example.savehometraining.ui.calendar.json.WriteFitnessResponse;
import com.example.savehometraining.ui.community.json.WriteCommentResponse;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Calendar_write extends AppCompatActivity {
    ServiceApi service = RetrofitClient.getClient().create(ServiceApi.class);
    ArrayList<String> userinfo= ((MainActivity)MainActivity.context_main).Userinfo;
    ArrayList excerise_list=new ArrayList<>();
    Button fitAccount,Checkday;
    DatePickerDialog.OnDateSetListener callbackMethod;//달력 기능
    EditText Edit_name,Edit_day,Edit_count,Edit_content;
    String Select_excerise_title;
    int year,month,day;
    int position;    //스피너 위치 저장
    ArrayAdapter<String> adspin;   //스피너 문자 배열

    public List<LoadExerciseInfoResponse.response> ExcersiseInfo;//운동 리스트



    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.calendar_write);

        Checkday = (Button) findViewById(R.id.Buttonfitday);
        fitAccount = (Button) findViewById(R.id.buttonfitready);
        //버튼 가져오기

        //Edit_name = (EditText) findViewById(R.id.fit_name);
        Edit_day = (EditText) findViewById(R.id.fit_day);
        Edit_count = (EditText) findViewById(R.id.fit_count);
        Edit_content = (EditText) findViewById(R.id.fit_content);
        //Edit 가져오기

        //운동 목록 불러오기
        excerise_list.add("운동 목록을 선택하세요!");
        LodadExceriseList(new LoadExceriseInfoData(1),excerise_list);

        /*Spinner*/
        Spinner spin=(Spinner)findViewById(R.id.routine_spinnerexerciselist_calendar);


        //Spinner 자체에서 선택된 부위 이름만 출력 되게 레이아웃 지정
        adspin = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,excerise_list);
        //adspin=ArrayAdapter.createFromResource(this, R.array.excerise_list, android.R.layout.simple_spinner_dropdown_item);
        //adspin.setDropDownViewResource(android.R.layout.simple_spinner_item);
        //드랍다운 팝업에는 라디오 버튼도 출력되게 지정
        //adspin.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spin.setSelection(0);
        spin.setAdapter(adspin);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                Select_excerise_title=arg0.getItemAtPosition(pos).toString();
                position=pos;
                Log.e(Select_excerise_title,"");
                //((TextView)arg0.getChildAt(0)).setTextColor(Color.RED);
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        //sponner 내용 넣기
        GregorianCalendar today=new GregorianCalendar();

        year=today.get(today.YEAR);
        month=today.get(today.MONTH);
        day=today.get(today.DAY_OF_MONTH);

        Checkday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //생년월일 선택
                callbackMethod = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Edit_day.setText(year + "년" + (monthOfYear+1) + "월" + dayOfMonth + "일");
                    }
                };


                DatePickerDialog dialog = new DatePickerDialog(Calendar_write.this, callbackMethod, year, month, day);
                dialog.show();
            }
        });//날짜 달력 입력

        fitAccount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if(position==0){
                    Toast.makeText(Calendar_write.this, "운동을 선택해주세요!", Toast.LENGTH_SHORT).show();
                }
                else{SaveCalendar(new WriteFitnessData(userinfo.get(3),Edit_day.getText().toString(),Select_excerise_title,Edit_count.getText().toString(),Edit_content.getText().toString()));}

            }
        });//운동 기록
    }

    private void SaveCalendar(WriteFitnessData data) {
        service.WriteFitness(data).enqueue(new Callback<WriteFitnessResponse>() {
            @Override
            public void onResponse(Call<WriteFitnessResponse> call, Response<WriteFitnessResponse> response) {
                WriteFitnessResponse result = response.body();
                Toast.makeText(Calendar_write.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                if(result.getResult()=="true"){
                    Calendar_write.this.finish();}
            }

            @Override
            public void onFailure(Call<WriteFitnessResponse> call, Throwable t) {
                Toast.makeText(Calendar_write.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러 발생", t.getMessage());
            }
        });
    }
    private void LodadExceriseList(LoadExceriseInfoData data,ArrayList<String> excerise_list){
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

}
