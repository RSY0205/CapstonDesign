package com.example.savehometraining.ui.Routine;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.savehometraining.R;
import com.squareup.picasso.Picasso;

public class RoutineFragment extends Fragment {

    ImageButton Recommendroutine,Myroutine,Fitnesshistory;
    TextView Cheeringmessage;
    int randomimg=(int)((Math.random()*10000)%3)+1;
    static int sel_page=2;//0은 추천,1은 커스텀, 2는 루틴 기본


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.main_fragment_routine, container, false);
        String[] message = getResources().getStringArray(R.array.message);
        int randommessage=(int)((Math.random()*10000)%message.length);
        sel_page=2;
        Recommendroutine=(ImageButton) view.findViewById(R.id.ImageButton_reocmmendroutine);
        Myroutine=(ImageButton) view.findViewById(R.id.ImageButton_myroutine);
        Fitnesshistory=(ImageButton) view.findViewById(R.id.ImageButton_fitnesshistory);
        Cheeringmessage=(TextView)view.findViewById(R.id.cheeringtext);
        Cheeringmessage.setText(message[randommessage]);

        String url="http://52.78.221.27/exerciseimg/routine"+Integer.toString(randomimg);
        Picasso.get().load(url+"-1.jpg").error(R.drawable.mainlogo).into(Recommendroutine);
        Picasso.get().load(url+"-2.jpg").error(R.drawable.mainlogo).into(Myroutine);
        Picasso.get().load("http://52.78.221.27/exerciseimg/Finesshistory.jpg").error(R.drawable.mainlogo).into(Fitnesshistory);

        Recommendroutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RecommendRoutine=new Intent(getActivity(), Routine_ConnectRecommendRoutine.class);
                startActivity(RecommendRoutine);
            }

        });
        Myroutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MyRoutine=new Intent(getActivity(), Routine_ConnectCustomRoutine.class);
                startActivity(MyRoutine);
            }

        });
        Fitnesshistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Statistic=new Intent(getActivity(),Routine_statistic.class);
                startActivity(Statistic);;
            }
        });


        return view;
    }
}