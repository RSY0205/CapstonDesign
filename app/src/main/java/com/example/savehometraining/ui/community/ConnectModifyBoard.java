package com.example.savehometraining.ui.community;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.savehometraining.Login.json.LoginData;
import com.example.savehometraining.MainActivity;
import com.example.savehometraining.R;
import com.example.savehometraining.retrofit.RetrofitClient;
import com.example.savehometraining.retrofit.ServiceApi;
import com.example.savehometraining.ui.community.json.ModifyBoardData;
import com.example.savehometraining.ui.community.json.ModifyBoardRespose;
import com.example.savehometraining.ui.community.json.WriteCommunityData;
import com.example.savehometraining.ui.community.json.WriteCommunityResponse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnectModifyBoard extends AppCompatActivity {
    AlertDialog alertDialog;
    ImageView close,ok,photo;
    EditText title,content;
    ImageButton Button_close,Button_color,Button_ok,Button_photo,Button_align;
    ServiceApi service = RetrofitClient.getClient().create(ServiceApi.class);
    ArrayList<String> userinfo= ((MainActivity)MainActivity.context_main).Userinfo;
    //0.이름, 1.생년월일, 2.성별, 3.아이디, 4.비밀번호(null), 5.중복 비밀번호(null), 6.닉네임, 7.전화번호, 8.키, 9.체중, 10.힘, 11.목표

    Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다
    // 년월일시분초 14자리 포멧
    SimpleDateFormat fourteen_format = new SimpleDateFormat("yyyy년MM월dd일HH시mm분ss초");
    //System.out.println(fourteen_format.format(date_now)); // 14자리 포멧으로 출력한다
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.community_modify);
        title=(EditText) findViewById(R.id.modifytitle);
        content=(EditText) findViewById(R.id.modifycontent);
        Button_close = (ImageButton) findViewById(R.id.button_modifyclose);
        Button_color = (ImageButton) findViewById(R.id.button_modifycolor);
        Button_ok = (ImageButton) findViewById(R.id.button_modifyok);
        Button_photo = (ImageButton) findViewById(R.id.button_modifyphoto);
        Button_align = (ImageButton) findViewById(R.id.button_modifyalign);

        title.setText(getIntent().getStringExtra("title"));
        content.setText(getIntent().getStringExtra("context"));

        int Board_number=getIntent().getIntExtra("Board_Id",9999);

        Button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });


        Button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyBoard(new ModifyBoardData(Board_number,title.getText().toString(),content.getText().toString(),fourteen_format.format(date_now)));
                finish();

            }

        });

        Button_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ConnectModifyBoard.this, "아직 지원하지 않는 기능입니다.", Toast.LENGTH_SHORT).show();
            }

        });
        Button_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ConnectModifyBoard.this, "아직 지원하지 않는 기능입니다.", Toast.LENGTH_SHORT).show();
            }

        });
        Button_align.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ConnectModifyBoard.this, "아직 지원하지 않는 기능입니다.", Toast.LENGTH_SHORT).show();
            }

        });
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("작성중인 내용을 저장하지 않고 나가시겠습니까?");
        builder.setPositiveButton("확인", dialogListener);
        builder.setNegativeButton("취소", null);
        alertDialog = builder.create();
        alertDialog.show();
    }

    DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            finish();
        }
    };

    private void ModifyBoard(ModifyBoardData data){
        service.ModifyBoard(data).enqueue(new Callback<ModifyBoardRespose>() {
            @Override
            public void onResponse(Call<ModifyBoardRespose> call, Response<ModifyBoardRespose> response) {
             ModifyBoardRespose result=response.body();
                Toast.makeText(ConnectModifyBoard.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                if(result.getResult()=="true"){
                    finish();}
            }

            @Override
            public void onFailure(Call<ModifyBoardRespose> call, Throwable t) {
                Toast.makeText(ConnectModifyBoard.this, "연결 오류 발생", Toast.LENGTH_SHORT).show();
                Log.e("연결 오류 발생", t.getMessage());
            }
        });
    }
}
