package com.example.savehometraining;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button SignUp;
    Button Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SignUp=(Button)findViewById(R.id.ButtonSignup);
        Login=(Button)findViewById(R.id.Buttonlogin);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup=new Intent(getApplicationContext(), Connectsignup.class);
                startActivity(signup);

            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent login=new Intent(getApplicationContext(),???.class);
                //startActivity(login);

            }
        });
    }
}