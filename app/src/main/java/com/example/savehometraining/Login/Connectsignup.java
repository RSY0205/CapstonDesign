package com.example.savehometraining.Login;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.savehometraining.Login.json.CheckIdData;
import com.example.savehometraining.Login.json.CheckIdResponse;
import com.example.savehometraining.Login.json.CheckNicknameData;
import com.example.savehometraining.Login.json.CheckNicknameResponse;
import com.example.savehometraining.Login.json.JoinData;
import com.example.savehometraining.Login.json.JoinResponse;
import com.example.savehometraining.R;
import com.example.savehometraining.retrofit.RetrofitClient;
import com.example.savehometraining.retrofit.ServiceApi;
import com.example.savehometraining.ui.calendar.Calendar_ConnectNoticeboard;

import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Connectsignup extends AppCompatActivity{

    Button MakeAccount,CheckID,ChecknickName,CheckBirth;
    DatePickerDialog.OnDateSetListener callbackMethod;//달력 기능
    EditText Edit_name,Edit_birth,Edit_id,Edit_pw,Edit_subpw,Edit_nickname,Edit_phone,Edit_height,Edit_weight;
    RadioGroup Radio_gender;
    String User[]=new String[10];
    Boolean User_Check[]=new Boolean[10];
    Boolean CheckOverlap[]={Boolean.FALSE,Boolean.FALSE};
    String[] User_kr=new String[]{"이름","생년월일","성별","ID","PW","PW","별명","전화번호","신장","몸무계"};
    String error=null;          //  0       1        2     3    4     5    6       7        8       9
    AccountCheckInDB function=new AccountCheckInDB();
    com.example.savehometraining.Login.User user_register=new User();
    ServiceApi service = RetrofitClient.getClient().create(ServiceApi.class);

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.login_signup);

        MakeAccount = (Button) findViewById(R.id.buttonMakeAccount);
        CheckID = (Button) findViewById(R.id.buttoncheckid);
        ChecknickName = (Button) findViewById(R.id.buttonchecknickname);
        CheckBirth = (Button) findViewById(R.id.ButtonBirtyday);
        //버튼 가져오기

        Edit_name = (EditText) findViewById(R.id.User_name);
        Edit_birth = (EditText) findViewById(R.id.User_birth);
        Edit_id = (EditText) findViewById(R.id.User_id);
        Edit_pw = (EditText) findViewById(R.id.User_pwd);
        Edit_subpw = (EditText) findViewById(R.id.Sub_pwd);//비밀번호 확인용
        Edit_nickname = (EditText) findViewById(R.id.User_nickname);
        Edit_phone = (EditText) findViewById(R.id.User_phone);
        Edit_height = (EditText) findViewById(R.id.User_height);
        Edit_weight = (EditText) findViewById(R.id.User_Weight);
        //테스트 에디터값 가져오기

        GregorianCalendar today=new GregorianCalendar();
        int year=today.get(today.YEAR);
        int month=today.get(today.MONTH);
        int day=today.get(today.DAY_OF_MONTH);
        //달력 오늘 날짜

        Radio_gender = (RadioGroup) findViewById((R.id.genderGroup));
        //라디오 그룹


        MakeAccount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                User[0] = Edit_name.getText().toString();
                User[1] = Edit_birth.getText().toString();
                //User[2] gender(radio로 사용)
                User[3] = Edit_id.getText().toString();
                User[4] = Edit_pw.getText().toString();
                User[5] = Edit_subpw.getText().toString();
                User[6] = Edit_nickname.getText().toString();
                User[7] = Edit_phone.getText().toString();
                User[8] = Edit_height.getText().toString();
                User[9] = Edit_weight.getText().toString();
                //각 Edit String 배열에 입력
                int Radio_id = Radio_gender.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(Radio_id);
                //라디오그룹


                function.RadioCheck(Radio_id, rb, User, User_Check);//라디오 체크 유무
                function.BlankCheck(User, User_Check);//공백(" ",빈칸) 검사
                function.CheckPWD(User, User_Check);//비밀번호 sub비밀번호 일치 확인
                function.LangeOut(User, User_Check);//값범위 검사
                error = function.ErrorMessage(User_kr, User_Check);//에러 Edit 찾기
                //아무 값도 입력안됬을시 오류 출력
                if (error != null) {
                    Toast.makeText(Connectsignup.this, error + "을(를) 확인하세요", Toast.LENGTH_SHORT).show();
                }
                else if(CheckOverlap[0]==Boolean.FALSE&&CheckOverlap[1]==Boolean.FALSE){
                    Toast.makeText(Connectsignup.this,"중복 확인하세요", Toast.LENGTH_SHORT).show();}
                else {
                    User[4] = AES128.encryptAES128(User[4]);
                    User[5] = AES128.encryptAES128(User[5]);

                    Intent part_preference = new Intent(getApplicationContext(), Part_preference.class);
                    //part_preference.putExtra("user_id", User[3]);
                    part_preference.putExtra("user_name", User[0]);
                    part_preference.putExtra("user_birth", User[1]);
                    part_preference.putExtra("user_gender", User[2]);
                    part_preference.putExtra("user_id", User[3]);
                    part_preference.putExtra("user_pw", User[4]);
                    part_preference.putExtra("user_pwcheck", User[5]);
                    part_preference.putExtra("user_nick", User[6]);
                    part_preference.putExtra("user_phone", User[7]);
                    part_preference.putExtra("user_cm", User[8]);
                    part_preference.putExtra("user_kg", User[9]);

                    startActivity(part_preference);

                    //Toast.makeText(Connectsignup.this, User[4], Toast.LENGTH_SHORT).show();
                    //user_register.User(User);
                    //startJoin(new JoinData(User));
                }

                finish();
            }
        });//회원 가입 버튼

        CheckBirth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //생년월일 선택
                callbackMethod = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Edit_birth.setText(year + "년" + (monthOfYear+1) + "월" + dayOfMonth + "일");
                    }
                };

                DatePickerDialog dialog = new DatePickerDialog(Connectsignup.this, callbackMethod, year, month, day);
                dialog.show();
            }
        });//생년월일 달력 입력


        CheckID.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //서버에서 중복 아이디 확인
                User[3] = Edit_id.getText().toString();
                startCheckId(new CheckIdData(User[3]));
            }
        });//아이디 중복 확인 버튼

        ChecknickName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //서버에서 중복 닉네임 확인
                User[6] = Edit_nickname.getText().toString();
                startCheckNickname(new CheckNicknameData(User[6]));
            }
        });//이름 중복 확인 버튼
    }


    private void startJoin(JoinData data) {
        service.userJoin(data).enqueue(new Callback<JoinResponse>() {
            @Override
            public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {
                JoinResponse result = response.body();
                Toast.makeText(Connectsignup.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                if(result.getResult()=="true")
                {
                    Toast.makeText(Connectsignup.this, "가입을 환영합니다!!", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                Toast.makeText(Connectsignup.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러 발생", t.getMessage());
            }
        });
    }   //회원가입 서버 통신

    private void startCheckId(CheckIdData data) {
        service.checkId(data).enqueue(new Callback<CheckIdResponse>() {
            @Override
            public void onResponse(Call<CheckIdResponse> call, Response<CheckIdResponse> response) {
                CheckIdResponse result = response.body();
                Toast.makeText(Connectsignup.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                if(result.getResult()=="true")
                {
                    //Toast.makeText(Connectsignup.this, "사용가능한 ID입니다!!", Toast.LENGTH_SHORT).show();
                    CheckOverlap[0]=Boolean.TRUE;
                }
                else if(result.getResult()=="false")
                {
                    //Toast.makeText(Connectsignup.this, "사용할 수 없는 ID입니다!!", Toast.LENGTH_SHORT).show();
                    CheckOverlap[0]=Boolean.FALSE;
                }


            }

            @Override
            public void onFailure(Call<CheckIdResponse> call, Throwable t) {
                Toast.makeText(Connectsignup.this, "에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("에러 발생", t.getMessage());
            }
        });
    }   //아이디 중복 검사 서버 통신
    private void startCheckNickname(CheckNicknameData data) {
        service.checkNickName(data).enqueue(new Callback<CheckNicknameResponse>() {
            @Override
            public void onResponse(Call<CheckNicknameResponse> call, Response<CheckNicknameResponse> response) {
                CheckNicknameResponse result = response.body();
                Toast.makeText(Connectsignup.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                if(result.getResult()=="true")
                {
                    //Toast.makeText(Connectsignup.this, "사용가능한 ID입니다!!", Toast.LENGTH_SHORT).show();
                    CheckOverlap[1]=Boolean.TRUE;
                }
                else if(result.getResult()=="false")
                {
                    //Toast.makeText(Connectsignup.this, "사용할 수 없는 ID입니다!!", Toast.LENGTH_SHORT).show();
                    CheckOverlap[1]=Boolean.FALSE;
                }


            }

            @Override
            public void onFailure(Call<CheckNicknameResponse> call, Throwable t) {
                Toast.makeText(Connectsignup.this, "에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("에러 발생", t.getMessage());
            }
        });
    }   //닉네임 중복 검사 서버 통신
}