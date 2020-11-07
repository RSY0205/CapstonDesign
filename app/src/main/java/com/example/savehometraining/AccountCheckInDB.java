package com.example.savehometraining;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AccountCheckInDB {

    Context context;

    private void context(Context context){
        this.context=context;
    }

    public void RadioCheck(int Radio_id, RadioButton rb,String User[],Boolean User_Check[]){
        if(Radio_id==-1){
            User_Check[2]=Boolean.FALSE;
            User[2]=" ";
        }
        else{User[2]=rb.getText().toString(); }
    }//라디오체크확인
    public void BlankCheck(String User[],Boolean User_Check[]){
        for(int i=0;i<User.length;i++){
            if(User[i].indexOf(' ')>-1||User[i].length()==0){ User_Check[i]=Boolean.FALSE; }
            else User_Check[i]=Boolean.TRUE; }
    }//공백 검사
    public void CheckPWD(String User[],Boolean User_Check[]){
        if(User[4].equals(User[5])==false){User_Check[5]=Boolean.FALSE;}
    }
    public void LangeOut(String User[],Boolean User_Check[]){
        if(User[3].length()<4)User_Check[3]=Boolean.FALSE;//ID최소길이 4
        if(User[4].length()<7)User_Check[4]=Boolean.FALSE;//비밀번호 최소 7
        if(User[5].length()<7)User_Check[5]=Boolean.FALSE;//Sub비밀번호 최소 7
        if(User[7].length()<11)User_Check[7]=Boolean.FALSE;//전화번호 길이
        if(Integer.parseInt(User[8])<120||Integer.parseInt(User[8])>250)User_Check[8]=Boolean.FALSE;//120<키<250
        if(Integer.parseInt(User[9])<20||Integer.parseInt(User[9])>200)User_Check[9]=Boolean.FALSE;//20<체중<200
    }//각 값의 범위 확인
    public String ErrorMessage(String User_kr[],Boolean User_Check[]){
        String error=null;
        for(int i=0;i<User_kr.length;i++){
            if(User_Check[i]==Boolean.FALSE){
                error=User_kr[i];
                break;
            }
        }//에러 위치 Toast발생
        return error;
    }//에러 유무 확인

}

