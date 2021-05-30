package com.example.savehometraining.ui.calendar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.example.savehometraining.ui.calendar.json.DeleteFitnessData;
import com.example.savehometraining.ui.calendar.json.DeleteFitnessResponse;
import com.example.savehometraining.ui.calendar.json.ModifyFitnessData;
import com.example.savehometraining.ui.calendar.json.ModifyFitnessResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Calendar_ConnectNoticeboard extends AppCompatActivity {
    //게시판
    ServiceApi service = RetrofitClient.getClient().create(ServiceApi.class);
    ArrayList<String> userinfo= ((MainActivity)MainActivity.context_main).Userinfo;
    ArrayList excerise_list=new ArrayList<>();
    Button fitmodify,fitdelete;
    String Select_excerise_title;
    int position;    //스피너 위치 저장
    DatePickerDialog.OnDateSetListener callbackMethod;//달력 기능
    EditText Edit_name,Edit_day,Edit_count,Edit_content, Edit_min, Edit_sec;
    int year,month,day;
    int timex; // 0이면 카운트, 1이면 시간
    ArrayAdapter<String> adspin;   //스피너 문자 배열
    public List<LoadExerciseInfoResponse.response> ExcersiseInfo;//운동 리스트

    //Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다
    // 년월일시분초 14자리 포멧
    //SimpleDateFormat fourteen_format = new SimpleDateFormat("yyyy년MM월dd일HH시mm분ss초");
    //System.out.println(fourteen_format.format(date_now)); // 14자리 포멧으로 출력한다

    protected void onCreate(Bundle saveInstanceState) {


        super.onCreate(saveInstanceState);
        setContentView(R.layout.calendar_noticeboard);

        fitmodify = (Button) findViewById(R.id.buttonfitmodify);
        fitdelete = (Button) findViewById(R.id.buttonfitdelete);
        //버튼 가져오기

        //Edit_name = (EditText) findViewById(R.id.fit_name);
        Edit_day = (EditText) findViewById(R.id.fit_day);
        Edit_count = (EditText) findViewById(R.id.fit_count);
        Edit_content = (EditText) findViewById(R.id.fit_content);
        Edit_min = (EditText) findViewById(R.id.fit_minute);
        Edit_sec = (EditText) findViewById(R.id.fit_second);

        //Edit 가져오기

        //운동 목록 불러오기
        String excercisename=getIntent().getStringExtra("calendar_title");



        excerise_list.add("초기 : "+excercisename);
        LodadExceriseList(new LoadExceriseInfoData(1),excerise_list);

        /*Spinner*/
        Spinner spin=(Spinner)findViewById(R.id.routine_spinnerexerciselist_calendarModify);

        //Spinner 자체에서 선택된 부위 이름만 출력 되게 레이아웃 지정
        adspin = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,excerise_list);
        //adspin=ArrayAdapter.createFromResource(this, R.array.excerise_list, android.R.layout.simple_spinner_dropdown_item);
        //adspin.setDropDownViewResource(android.R.layout.simple_spinner_item);
        //드랍다운 팝업에는 라디오 버튼도 출력되게 지정
        //adspin.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spin.setAdapter(adspin);


        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                Select_excerise_title=arg0.getItemAtPosition(pos).toString();
                position=pos;
                Log.e(Select_excerise_title,"");

                if(position==0) {
                    if(excercisename.equals("러닝")||excercisename.equals("홈사이클")||excercisename.equals("플랭크"))
                    {
                        timex=1;
                        Edit_count.setEnabled(false);
                        Edit_min.setEnabled(true);
                        Edit_sec.setEnabled(true);
                    }

                    else
                    {
                        timex=0;
                        Edit_count.setEnabled(true);
                        Edit_min.setEnabled(false);
                        Edit_sec.setEnabled(false);
                    }
                }

                else {
                    if(position==25||position==26||position==17)
                    {
                        timex=1;
                        Edit_count.setText("0");
                        Edit_count.setEnabled(false);
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

        Edit_day.setText(getIntent().getStringExtra("calendar_date"));
        Edit_count.setText(getIntent().getStringExtra("count"));
        Edit_content.setText(getIntent().getStringExtra("memo"));
        Edit_min.setText(getIntent().getStringExtra("min"));
        Edit_sec.setText(getIntent().getStringExtra("sec"));
        int number = getIntent().getIntExtra("id", 9999);


        fitmodify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((Edit_count.getText().toString().equals("")||Edit_count.getText().toString().equals("0"))&&timex==0)
                {
                    Toast.makeText(Calendar_ConnectNoticeboard.this, "개수를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Edit_count.setText("0");
                if((Edit_min.getText().toString().equals("")||Edit_min.getText().toString().equals("0"))&&timex==1)
                {
                    Toast.makeText(Calendar_ConnectNoticeboard.this, "min을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Edit_min.setText("0");
                if((Edit_sec.getText().toString().equals("")||Edit_sec.getText().toString().equals("0"))&&timex==1)
                {
                    Toast.makeText(Calendar_ConnectNoticeboard.this, "sec를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Edit_sec.setText("0");
                if(Edit_content.getText().toString().equals(""))
                    Edit_content.setText("없음");

                if(position==0){
                    SaveInfo(new ModifyFitnessData(userinfo.get(3), number, excercisename,Edit_count.getText().toString(),Edit_min.getText().toString(),Edit_sec.getText().toString(),Edit_content.getText().toString()));
                    finish();
                    //Toast.makeText(Calendar_ConnectNoticeboard.this, "운동을 선택해주세요!", Toast.LENGTH_SHORT).show();
                }
                //수정
                else{SaveInfo(new ModifyFitnessData(userinfo.get(3), number, Select_excerise_title,Edit_count.getText().toString(),Edit_min.getText().toString(),Edit_sec.getText().toString(),Edit_content.getText().toString()));
                    finish();}
            }
        });

        fitdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 삭제코드구현예정
                DeleteInfo(new DeleteFitnessData(number));
                finish();
            }
        });

    }


    //수정 레트로핏 진행 함수 수정할것
    private void SaveInfo(ModifyFitnessData data) {
        //MyAdapterBoard mMyAdapter = new MyAdapterBoard();
        service.Mofit(data).enqueue(new Callback<ModifyFitnessResponse>() {
            @Override
            public void onResponse(Call<ModifyFitnessResponse> call, Response<ModifyFitnessResponse> response) {
                ModifyFitnessResponse result = response.body();
                //Toast.makeText(CommunityFragment.This, result.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("연결 성공", result.getMessage());
                if(result.getResult()=="true"){
                    //textview_nickname textview_date textview_maintitle textview_maintext

                }
            }
            @Override
            public void onFailure(Call<ModifyFitnessResponse> call, Throwable t) {
                Log.e("로그인 에러 발생", t.getMessage());
            }
        });
    }

    //삭제 레트로핏 진행 함수 수정할것
    private void DeleteInfo(DeleteFitnessData data) {
        service.Delfit(data).enqueue(new Callback<DeleteFitnessResponse>() {
            @Override
            public void onResponse(Call<DeleteFitnessResponse> call, Response<DeleteFitnessResponse> response) {
                DeleteFitnessResponse result = response.body();
                //Toast.makeText(CommunityFragment.This, result.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("연결 성공", result.getMessage());
                if(result.getResult()=="true"){
                    //textview_nickname textview_date textview_maintitle textview_maintext

                }
            }
            @Override
            public void onFailure(Call<DeleteFitnessResponse> call, Throwable t) {
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