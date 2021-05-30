package com.example.savehometraining;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.savehometraining.Login.AES128;
import com.example.savehometraining.Login.Connectsignup;
import com.example.savehometraining.Login.json.LoginData;
import com.example.savehometraining.Login.json.LoginResponse;
import com.example.savehometraining.retrofit.RetrofitClient;
import com.example.savehometraining.retrofit.ServiceApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button Button_SignUp,Button_Login,Button_Test;
    EditText Edit_ID,Edit_PW;
    String id,pw;
    CheckBox checkBox_id;
    CheckBox checkBox_pw;
    ServiceApi service = RetrofitClient.getClient().create(ServiceApi.class);
    public ArrayList<String> Userinfo;
    public static Context context_main;


    SharedPreferences setting;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        context_main=this;
        Button_SignUp=(Button)findViewById(R.id.ButtonSignup);
        Button_Login=(Button)findViewById(R.id.Buttonlogin);
        checkBox_id=(CheckBox)findViewById(R.id.checkbox_id);
        checkBox_pw=(CheckBox)findViewById(R.id.checkbox_pw);
        //Button_Test=(Button)findViewById(R.id.ButtonTest);

        Edit_ID=(EditText)findViewById(R.id.login_id);
        Edit_PW=(EditText)findViewById(R.id.login_pw);

        setting = getSharedPreferences("setting", 0);
        editor= setting.edit();
        final boolean[] a = {setting.getBoolean("Auto_Login_id_enabled", false)};
        final boolean[] b = {setting.getBoolean("Auto_Login_pw_enabled", false)};

        if(a[0]) {
            Edit_ID.setText(setting.getString("ID", ""));
            checkBox_id.setChecked(true);
        } // id저장 체크박스 체크상태 불러오기
        if(b[0]) {
            Edit_PW.setText(setting.getString("PW", ""));
            checkBox_pw.setChecked(true);
        } // pw저장 체크박스 체크상태 불러오기

        /*자동 로그인*/
        Autologin(a[0],b[0]);

        /*
        Button_Test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=Edit_ID.getText().toString();
                pw=Edit_PW.getText().toString();

                Intent main=new Intent(getApplicationContext(),TrainingActivity.class);
                startActivity(main);

            }
        });
        */ // test 로그인 임시버튼
        /* test용 id 체크 리스너
        checkBox_id.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
            {
                // 체크 되었을때 아이디 저장
            }
            else
            {
                //그렇지 않을시 공백저장
            }
        });
        */ // test check id 리스너
        /* test용 pw 체크 리스너
        checkBox_pw.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
            {
                // 체크 되었을때 비밀번호 저장
            }
            else
            {
                //그렇지 않을시 공백 저장
            }
        });
        */ // test check pw 리스너


        checkBox_id.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    a[0] =true;
                    // String ID = Edit_ID.getText().toString();

                    // editor.putString("ID", ID);
                    editor.putBoolean("Auto_Login_id_enabled", true);
                    editor.commit();
                }else{
                    /**
                     * remove로 지우는것은 부분삭제
                     * clear로 지우는것은 전체 삭제
                     */
                    a[0]=false;
                    editor.remove("ID");
                    editor.remove("Auto_Login_id_enabled");
                    //editor.clear();
                    editor.commit();
                }
            }
        }); // 아이디 저장 체크박스 리스너
        checkBox_pw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    b[0] =true;
                    //String PW = Edit_PW.getText().toString();
                    //editor.putString("PW", PW);
                    editor.putBoolean("Auto_Login_pw_enabled", true);
                    editor.commit();
                }else{
                    /**
                     * remove로 지우는것은 부분삭제
                     * clear로 지우는것은 전체 삭제
                     */
                    b[0] =false;
                    editor.remove("PW");
                    editor.remove("Auto_Login_pw_enabled");
                    //editor.clear();
                    editor.commit();
                }
            }
        }); // 비밀번호 저장 체크박스 리스너
        Button_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup=new Intent(getApplicationContext(), Connectsignup.class);
                startActivity(signup);
            }

        }); // 회원가입 버튼 클릭시 연결(버튼 클릭 리스너)
        Button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=Edit_ID.getText().toString();
                pw=Edit_PW.getText().toString();
                pw=AES128.encryptAES128(pw);

                if(a[0]){
                    String ID = Edit_ID.getText().toString();
                    editor.putString("ID", ID);
                }

                if(b[0]){
                    String PW = Edit_PW.getText().toString();
                    editor.putString("PW", PW);
                }
                editor.commit();

                startLogin(new LoginData(id,pw));

            }
        });//로그인 버튼(계정 확인 후 내부로 이동)

    }

    private void startLogin(LoginData data) {
        service.userLogin(data).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse result = response.body();
                Toast.makeText(MainActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                Userinfo=result.getUserInfo();
                if(result.getResult()=="true"){
                    Intent main=new Intent(getApplicationContext(),TrainingActivity.class);
                    startActivity(main);
                    finish();}
                //Toast.makeText(MainActivity.this, "연결 성공", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러 발생", t.getMessage());
            }
        });
    } // 로그인 실행 메소드(레트로핏, xml연결)

    void Autologin(boolean a, boolean b){
        id=Edit_ID.getText().toString();
        pw=Edit_PW.getText().toString();
        pw=AES128.encryptAES128(pw);

        if(a){
            String ID = Edit_ID.getText().toString();
            editor.putString("ID", ID);
        }

        if(b){
            String PW = Edit_PW.getText().toString();
            editor.putString("PW", PW);
        }
        editor.commit();

        startLogin(new LoginData(id,pw));
    }
}



