package com.example.savehometraining;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button Button_SignUp,Button_Login;
    EditText Edit_ID,Edit_PW;
    String id,pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button_SignUp=(Button)findViewById(R.id.ButtonSignup);
        Button_Login=(Button)findViewById(R.id.Buttonlogin);

        Edit_ID=(EditText)findViewById(R.id.login_id);
        Edit_PW=(EditText)findViewById(R.id.login_pw);

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
}

