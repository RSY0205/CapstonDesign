package com.example.savehometraining;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Connectsignup extends AppCompatActivity {

    Button MakeAccount,CheckID,ChecknickName;

    EditText Edit_name,Edit_birth,Edit_id,Edit_pw,Edit_nickname,Edit_phone,Edit_height,Edit_weight;
    //RadioButton Radio_Man,Radio_Woman;
    RadioGroup Radio_gender;
    String name,birth,gender,id,pw,nickname,phone,height,weight;

    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.signup);

        MakeAccount=(Button)findViewById(R.id.buttonMakeAccount);
        CheckID=(Button)findViewById(R.id.buttoncheckid);
        ChecknickName=(Button)findViewById(R.id.buttonchecknickname);
        //버튼 가져오기

        Edit_name=(EditText)findViewById(R.id.User_name);
        Edit_birth=(EditText)findViewById(R.id.User_birth);
        Edit_id=(EditText)findViewById(R.id.User_id);
        Edit_pw=(EditText)findViewById(R.id.User_pwd);
        Edit_nickname=(EditText)findViewById(R.id.User_nickname);
        Edit_phone=(EditText)findViewById(R.id.User_phone);
        Edit_height=(EditText)findViewById(R.id.User_height);
        Edit_weight=(EditText)findViewById(R.id.User_Weight);
        //테스트 에디터값 가져오기

        //Radio_Man=(RadioButton) findViewById(R.id.User_Man);
        //Radio_Woman=(RadioButton) findViewById(R.id.User_Woman);
        //RadioButton 값 가져오기
        Radio_gender=(RadioGroup)findViewById((R.id.genderGroup));
        //라디오 그룹

        name=Edit_name.getText().toString();
        birth=Edit_birth.getText().toString();
        id=Edit_id.getText().toString();
        pw=Edit_pw.getText().toString();
        nickname=Edit_nickname.getText().toString();
        phone=Edit_phone.getText().toString();
        height=Edit_height.getText().toString();
        weight=Edit_weight.getText().toString();
        //문자열로 형변환



        MakeAccount.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                //서버에 정보전달,중복이나 문제있을시 에러 메세지와 틀린 부분 알려주기

                int id=Radio_gender.getCheckedRadioButtonId();
                RadioButton rb=(RadioButton)findViewById(id);
                gender=rb.getText().toString();
                Toast.makeText(getApplication(),gender, Toast.LENGTH_SHORT).show();//테스트용
                //성별 등록
                
                finish();
            }
        });

        CheckID.setOnClickListener((new View.OnClickListener(){
            public void onClick(View view){
                //서버에서 중복 아이디 확인
            }
        }));

        ChecknickName.setOnClickListener((new View.OnClickListener(){
            public void onClick(View view){
                //서버에서 중복 닉네임 확인
            }
        }));

    }
}
