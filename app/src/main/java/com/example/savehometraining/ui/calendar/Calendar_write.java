package com.example.savehometraining.ui.calendar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Context;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.savehometraining.MainActivity;
import com.example.savehometraining.R;
import com.example.savehometraining.retrofit.RetrofitClient;
import com.example.savehometraining.retrofit.ServiceApi;
import com.example.savehometraining.ui.Routine.json.LoadExceriseInfoData;
import com.example.savehometraining.ui.Routine.json.LoadExerciseInfoResponse;
import com.example.savehometraining.ui.calendar.json.WriteFitnessData;
import com.example.savehometraining.ui.calendar.json.WriteFitnessResponse;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Calendar_write extends AppCompatActivity {
    ServiceApi service = RetrofitClient.getClient().create(ServiceApi.class);
    ArrayList<String> userinfo= ((MainActivity)MainActivity.context_main).Userinfo;
    ArrayList excerise_list=new ArrayList<>();
    Button fitAccount, Checkday;
    DatePickerDialog.OnDateSetListener callbackMethod;//달력 기능
    EditText Edit_name,Edit_day,Edit_count,Edit_content, Edit_min, Edit_sec;
    String Select_excerise_title;
    int year,month,day;
    int position;    //스피너 위치 저장
    ArrayAdapter<String> adspin;   //스피너 문자 배열
    int timex; // 0이면 카운트, 1이면 시간

    public List<LoadExerciseInfoResponse.response> ExcersiseInfo;//운동 리스트
    public static Context mContext; //외부 호출


    public void SaveCalendar(WriteFitnessData data) {
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

    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.calendar_write);
        mContext = this;

        Checkday = (Button) findViewById(R.id.Buttonfitday);
        fitAccount = (Button) findViewById(R.id.buttonfitready);
        //버튼 가져오기

        //Edit_name = (EditText) findViewById(R.id.fit_name);
        Edit_day = (EditText) findViewById(R.id.fit_day);
        Edit_count = (EditText) findViewById(R.id.fit_count);
        Edit_content = (EditText) findViewById(R.id.fit_content);
        Edit_min = (EditText) findViewById(R.id.fit_minute);
        Edit_sec = (EditText) findViewById(R.id.fit_second);
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



                if(position==0) {
                    Edit_count.setEnabled(false);
                    Edit_min.setEnabled(false);
                    Edit_sec.setEnabled(false);
                }

                else {
                    if(position==25||position==26||position==17)
                    {
                        timex=1;
                        Edit_count.setEnabled(false);
                        Edit_count.setText("0");
                        Edit_min.setEnabled(true);
                        Edit_sec.setEnabled(true);
                    }
                    else
                    {
                        timex=0;
                        Edit_count.setEnabled(true);
                        Edit_min.setEnabled(false);
                        Edit_sec.setEnabled(false);
                        Edit_min.setText("0");
                        Edit_sec.setText("0");
                    }
                }
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
                        String month;
                        String day;
                        if((monthOfYear+1)<=9)
                            month= "0"+String.valueOf(monthOfYear+1);
                        else
                            month=String.valueOf(monthOfYear+1);

                        if(dayOfMonth<=9)
                            day="0"+String.valueOf(dayOfMonth);
                        else
                            day=String.valueOf(dayOfMonth);

                        Edit_day.setText(year + "년" + month + "월" + day + "일");
                        //Edit_day.setText(year + "년" + monthOfYear+1 + "월" + dayOfMonth + "일");
                    }
                };


                DatePickerDialog dialog = new DatePickerDialog(Calendar_write.this, callbackMethod, year, month, day);
                dialog.show();
            }
        });//날짜 달력 입력

        fitAccount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if(Edit_day.getText().toString().equals(""))
                {
                    Toast.makeText(Calendar_write.this, "날짜를 선택해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if((Edit_count.getText().toString().equals("")||Edit_count.getText().toString().equals("0"))&&timex==0)
                {
                    Toast.makeText(Calendar_write.this, "개수를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Edit_count.setText("0");
                if((Edit_min.getText().toString().equals("")||Edit_min.getText().toString().equals("0"))&&timex==1)
                {
                    Toast.makeText(Calendar_write.this, "min을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Edit_min.setText("0");
                if((Edit_sec.getText().toString().equals("")||Edit_sec.getText().toString().equals("0"))&&timex==1)
                {
                    Toast.makeText(Calendar_write.this, "sec를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Edit_sec.setText("0");
                if(Edit_content.getText().toString().equals(""))
                    Edit_content.setText("없음");

                if(position==0){
                    Toast.makeText(Calendar_write.this, "운동을 선택해주세요!", Toast.LENGTH_SHORT).show();
                }
                else{SaveCalendar(new WriteFitnessData(userinfo.get(3),Edit_day.getText().toString(),Select_excerise_title,Edit_count.getText().toString(),Edit_min.getText().toString(),Edit_min.getText().toString(),Edit_content.getText().toString()));}

            }
        });//운동 기록
    }
    protected void LodadExceriseList(LoadExceriseInfoData data, ArrayList<String> excerise_list){
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