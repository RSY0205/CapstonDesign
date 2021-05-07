package com.example.savehometraining.ui.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.savehometraining.MainActivity;
import com.example.savehometraining.R;
import com.example.savehometraining.retrofit.RetrofitClient;
import com.example.savehometraining.retrofit.ServiceApi;
import com.example.savehometraining.ui.calendar.json.LoadDayInfoResponse;
import com.example.savehometraining.ui.calendar.json.LoadDayInfodata;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//import com.example.savehometraining.ui.calendar;


public class Calendar_Schedule extends AppCompatActivity {
    private ListView listview;
    public List<LoadDayInfoResponse.response>Schedule;
    private FloatingActionButton button_fab;
    ServiceApi service = RetrofitClient.getClient().create(ServiceApi.class);
    ArrayList<String> userinfo= ((MainActivity)MainActivity.context_main).Userinfo;
    //0.이름, 1.생년월일, 2.성별, 3.아이디, 4.비밀번호(null), 5.중복 비밀번호(null), 6.닉네임, 7.전화번호, 8.키, 9.체중, 10.힘, 11.목표

    //TextView teview=((CalendarFragment)CalendarFragment.context_calendarFragment).tv_day;
    //TextView text=((CalendarFragment) getSupportFragmentManager().findFragmentByTag("myFragmentTag")).tv_day;





    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.calendar_listview_schedule);

        listview = (ListView)findViewById(R.id.lv_schedule);
        button_fab =(FloatingActionButton)findViewById(R.id.calendarfab);

        button_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent writing=new Intent(getApplicationContext(), Calendar_write.class);
                startActivity(writing);
            }

        });




        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getContext(),Integer.toString(position), Toast.LENGTH_SHORT).show();
                //Intent noticeboard=new Intent(getApplication(), Calendar_ConnectNoticeboard.class);
                Intent rehabilitation=new Intent(getApplicationContext(), Calendar_ConnectNoticeboard.class);


                rehabilitation.putExtra("id",Schedule.get(position).getid());
                rehabilitation.putExtra("calendar_date",Schedule.get(position).getCalendar_date());
                rehabilitation.putExtra("calendar_title",Schedule.get(position).getCalendar_title());
                rehabilitation.putExtra("count",Schedule.get(position).getCalendar_count());
                rehabilitation.putExtra("memo",Schedule.get(position).getCalendar_memo());

                startActivity(rehabilitation);
            }
        });



        String dayinfo=getIntent().getStringExtra("dayinfo");
        LoadDayInfo(new LoadDayInfodata(dayinfo,userinfo.get(3)));
    }

    private void dataSetting() {

        MyAdaptSchedule mMyAdapter = new MyAdaptSchedule();
        /*
        mMyAdapter.addItem("2020-11-09","팔굽혀펴기 10회");
        mMyAdapter.addItem("2020-11-13","윗몸일으키기 20회");
        mMyAdapter.addItem("2020-11-15","스쿼트 10회");
        mMyAdapter.addItem("2020-11-18","런지 20회");
        mMyAdapter.addItem("2020-11-04","팔굽혀펴기 20회");
        mMyAdapter.addItem("2020-11-07","런지 20회");
        mMyAdapter.addItem("2020-11-21","스쿼트 20회");

         */



        /* 리스트뷰에 어댑터 등록 */
        listview.setAdapter(mMyAdapter);
    }
    private void LoadDayInfo(LoadDayInfodata data) {
        MyAdaptSchedule mMyAdapter = new MyAdaptSchedule();
        service.LoadDay(data).enqueue(new Callback<LoadDayInfoResponse>() {
            @Override
            public void onResponse(Call<LoadDayInfoResponse> call, Response<LoadDayInfoResponse> response) {
                LoadDayInfoResponse result = response.body();
                Log.e("연결 성공", result.getMessage());
                if(result.getResult()=="true"){
                    Schedule=result.getResponse();
                    for(int i=0;i<result.getResponse().size();i++){
                        String context=(result.getResponse().get(i).getCalendar_title()+": "+result.getResponse().get(i).getCalendar_count()+"\n"+result.getResponse().get(i).getCalendar_memo());
                        mMyAdapter.addItem(result.getResponse().get(i).getCalendar_date(),context);
                    }
                    listview.setAdapter(mMyAdapter);
                }
            }

            @Override
            public void onFailure(Call<LoadDayInfoResponse> call, Throwable t) {
                Log.e("로그인 에러 발생", t.getMessage());
            }
        });
    }
}