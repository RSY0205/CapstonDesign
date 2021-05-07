package com.example.savehometraining.ui.calendar;

import android.app.Activity;
import android.app.ApplicationExitInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.fonts.SystemFonts;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.savehometraining.MainActivity;
import com.example.savehometraining.R;
import com.example.savehometraining.retrofit.RetrofitClient;
import com.example.savehometraining.retrofit.ServiceApi;
import com.example.savehometraining.ui.calendar.decorators.EventDecorator;
import com.example.savehometraining.ui.calendar.decorators.OneDayDecorator;
import com.example.savehometraining.ui.calendar.decorators.SaturdayDecorator;
import com.example.savehometraining.ui.calendar.decorators.SundayDecorator;
import com.example.savehometraining.ui.calendar.json.LoadCalendarDayData;
import com.example.savehometraining.ui.calendar.json.LoadCalendarDayResponse;
import com.example.savehometraining.ui.community.ConnectNoticeboard;
import com.example.savehometraining.ui.community.MyAdapterBoard;
import com.example.savehometraining.ui.community.json.LoadCommunityData;
import com.example.savehometraining.ui.community.json.LoadCommunityResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CalendarFragment extends Fragment {
    public List<LoadCalendarDayResponse.response> dayinfo;
    private FloatingActionButton button_fab;
    private FloatingActionButton button_logout;
    //public TextView tv_day;
    public String temp;
    MaterialCalendarView materialCalendarView;
    ServiceApi service = RetrofitClient.getClient().create(ServiceApi.class);
    public static Context context_calendarFragment;
    private CalendarViewModel calendarViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.main_fragment_calendar, container, false); // 레이아웃을 불러오는 뷰
        ArrayList<String> userinfo= ((MainActivity)MainActivity.context_main).Userinfo;
        materialCalendarView =(MaterialCalendarView)view.findViewById(R.id.calendarView);
        button_fab =(FloatingActionButton)view.findViewById(R.id.calendarfab);
        button_logout=(FloatingActionButton)view.findViewById(R.id.logout);

        //tv_day=(TextView)view.findViewById(R.id.textview_day);

        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017, 0, 1))
                .setMaximumDate(CalendarDay.from(2030, 11, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();



        materialCalendarView.addDecorators(
                new SundayDecorator(),      //일요일 빨간색
                new SaturdayDecorator(),    //토요일 파랑색
                new OneDayDecorator()     //오늘 날짜 형광색
        );
        LoadCalendarDay(new LoadCalendarDayData(userinfo.get(3)));



        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() { // 클릭 이벤트
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {


                //String tv_day.setText(String.format("%d-%d-%d",date.getYear(),date.getMonth()+1,date.getDay()));

                Intent schedule=new Intent(getActivity(), Calendar_Schedule.class);
                schedule.putExtra("dayinfo",String.format("%d년%d월%d일",date.getYear(),date.getMonth()+1,date.getDay()));
                startActivity(schedule);

            }
        });

        button_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent writing=new Intent(getActivity(), Calendar_write.class);
                startActivity(writing);
            }

        });

        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout=new Intent(getActivity(), MainActivity.class);
                System.runFinalization();
                startActivity(logout);
                System.exit(0);
                // 로그아웃 구현


                // Intent logout=new Intent(getActivity(), MainActivity.class);
                //startActivity(logout);
            }
        });








        return view;
    }
    private void LoadCalendarDay(LoadCalendarDayData data) {
        service.LoadCalendarDay(data).enqueue(new Callback<LoadCalendarDayResponse>() {
            @Override
            public void onResponse(Call<LoadCalendarDayResponse> call, Response<LoadCalendarDayResponse> response) {
                LoadCalendarDayResponse result = response.body();
                Log.e("연결 성공", result.getMessage());
                if(result.getResult()=="true"){
                    dayinfo=result.getResponse();
                    for(int i=0;i<dayinfo.size();i++){
                        String[] year=dayinfo.get(i).getCalendar_date().toString().split("년");
                        String[] month=year[1].split("월");
                        String[] day=month[1].split("일");
                        materialCalendarView.addDecorators(new EventDecorator(Color.RED, Collections.singleton(CalendarDay.from(Integer.parseInt(year[0]),Integer.parseInt(month[0])-1,Integer.parseInt(day[0])))));
                    }
                }
            }
            @Override
            public void onFailure(Call<LoadCalendarDayResponse> call, Throwable t) {
                Log.e("로그인 에러 발생", t.getMessage());
            }
        });
    }
}