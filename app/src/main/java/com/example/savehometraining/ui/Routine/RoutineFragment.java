package com.example.savehometraining.ui.Routine;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.savehometraining.R;
import com.example.savehometraining.ui.community.ConnectWrite;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class RoutineFragment extends Fragment {

    ImageButton Recommendroutine,Myroutine,Finesshistory;
    TextView Cheeringmessage;
    int random=(int)((Math.random()*10000)%3)+1;;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.main_fragment_routine, container, false);
        Recommendroutine=(ImageButton) view.findViewById(R.id.ImageButton_reocmmendroutine);
        Myroutine=(ImageButton) view.findViewById(R.id.ImageButton_myroutine);
        Finesshistory=(ImageButton) view.findViewById(R.id.ImageButton_fitnesshistory);
        Cheeringmessage=(TextView)view.findViewById(R.id.cheeringtext);

        String url="http://52.78.221.27/exerciseimg/routine"+Integer.toString(random);
        Picasso.get().load(url+"-1.jpg").error(R.drawable.mainlogo).into(Recommendroutine);
        Picasso.get().load(url+"-2.jpg").error(R.drawable.mainlogo).into(Myroutine);

        Recommendroutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RecommendRoutine=new Intent(getActivity(), ConnectRecommendRoutine.class);
                startActivity(RecommendRoutine);
            }

        });
        Myroutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MyRoutine=new Intent(getActivity(), ConnectCustomRoutine.class);
                startActivity(MyRoutine);
            }

        });


        return view;
    }
}