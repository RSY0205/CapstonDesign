package com.example.savehometraining;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Person;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.savehometraining.Login.LoginData;
import com.example.savehometraining.Login.LoginResponse;
import com.example.savehometraining.Login.RetrofitClient;
import com.example.savehometraining.Login.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button Button_SignUp,Button_Login,Button_Test;
    EditText Edit_ID,Edit_PW;
    String id,pw;
    ServiceApi service = RetrofitClient.getClient().create(ServiceApi.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button_SignUp=(Button)findViewById(R.id.ButtonSignup);
        Button_Login=(Button)findViewById(R.id.Buttonlogin);
        Button_Test=(Button)findViewById(R.id.ButtonTest);

        Edit_ID=(EditText)findViewById(R.id.login_id);
        Edit_PW=(EditText)findViewById(R.id.login_pw);

        Button_Test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=Edit_ID.getText().toString();
                pw=Edit_PW.getText().toString();
                startLogin(new LoginData(id,pw));

            }
        });


        Button_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup=new Intent(getApplicationContext(), Connectsignup.class);
                startActivity(signup);
    //test용 주석추가
            }
        });

        Button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=Edit_ID.getText().toString();
                pw=Edit_PW.getText().toString();

                //Intent login=new Intent(getApplicationContext(),???.class);
                //startActivity(login);

            }
        });

    }
    private void startLogin(LoginData data) {
        service.userLogin(data).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse result = response.body();
                Toast.makeText(MainActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러 발생", t.getMessage());
            }
        });
    }
}


