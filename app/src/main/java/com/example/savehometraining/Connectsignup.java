package com.example.savehometraining;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Connectsignup extends AppCompatActivity{

    Button MakeAccount,CheckID,ChecknickName,CheckBirth;
    DatePickerDialog.OnDateSetListener callbackMethod;//달력 기능
    EditText Edit_name,Edit_birth,Edit_id,Edit_pw,Edit_subpw,Edit_nickname,Edit_phone,Edit_height,Edit_weight;
    RadioGroup Radio_gender;
    String User[]=new String[10];
    Boolean User_Check[]=new Boolean[10];
    String[] User_kr=new String[]{"이름","생년월일","성별","ID","PW","PW","별명","전화번호","신장","몸무계"};
    String error=null;          //  0       1        2     3    4     5    6       7        8       9
    AccountCheckInDB function=new AccountCheckInDB();
    User user_register=new User();

    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.signup);

        MakeAccount=(Button)findViewById(R.id.buttonMakeAccount);
        CheckID=(Button)findViewById(R.id.buttoncheckid);
        ChecknickName=(Button)findViewById(R.id.buttonchecknickname);
        CheckBirth=(Button)findViewById(R.id.ButtonBirtyday);
        //버튼 가져오기

        Edit_name=(EditText)findViewById(R.id.User_name);
        Edit_birth=(EditText)findViewById(R.id.User_birth);
        Edit_id=(EditText)findViewById(R.id.User_id);
        Edit_pw=(EditText)findViewById(R.id.User_pwd);
        Edit_subpw=(EditText)findViewById(R.id.Sub_pwd);//비밀번호 확인용
        Edit_nickname=(EditText)findViewById(R.id.User_nickname);
        Edit_phone=(EditText)findViewById(R.id.User_phone);
        Edit_height=(EditText)findViewById(R.id.User_height);
        Edit_weight=(EditText)findViewById(R.id.User_Weight);
        //테스트 에디터값 가져오기

        Radio_gender=(RadioGroup)findViewById((R.id.genderGroup));
        //라디오 그룹


        MakeAccount.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){

                User[0]=Edit_name.getText().toString();
                User[1]=Edit_birth.getText().toString();
                //User[2] gender(radio로 사용)
                User[3]=Edit_id.getText().toString();
                User[4]=Edit_pw.getText().toString();
                User[5]=Edit_subpw.getText().toString();
                User[6]=Edit_nickname.getText().toString();
                User[7]=Edit_phone.getText().toString();
                User[8]=Edit_height.getText().toString();
                User[9]=Edit_weight.getText().toString();
                //각 Edit String 배열에 입력
                int Radio_id=Radio_gender.getCheckedRadioButtonId();
                RadioButton rb=(RadioButton)findViewById(Radio_id);
                //라디오그룹


                function.RadioCheck(Radio_id,rb,User,User_Check);
                function.BlankCheck(User,User_Check);//공백(" ",빈칸) 검사
                function.CheckPWD(User,User_Check);//비밀번호 sub비밀번호 일치 확인
                function.LangeOut(User,User_Check);//값범위 검사
                error=function.ErrorMessage(User_kr,User_Check);//에러 Edit 찾기
                //아무 값도 입력안됬을시 오류 출력
                if(error!=null){Toast.makeText(Connectsignup.this, error+"을(를) 확인하세요", Toast.LENGTH_SHORT).show();}
                user_register.makeUser(User);

                //finish();
            }
        });

        CheckBirth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //생년월일 선택
                callbackMethod = new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Edit_birth.setText(year + "년" + monthOfYear + "월" + dayOfMonth + "일");
                    }
                };

                DatePickerDialog dialog=new DatePickerDialog(Connectsignup.this,callbackMethod,2019,5,24);
                dialog.show();
            }
        });


        CheckID.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //서버에서 중복 아이디 확인
            }
        });

        ChecknickName.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //서버에서 중복 닉네임 확인
            }
        });

    }
}
