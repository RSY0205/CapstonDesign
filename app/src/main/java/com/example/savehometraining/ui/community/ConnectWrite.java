package com.example.savehometraining.ui.community;

import android.Manifest;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
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
import com.example.savehometraining.ui.community.json.BoardImgResponse;
import com.example.savehometraining.ui.community.json.WriteCommunityData;
import com.example.savehometraining.ui.community.json.WriteCommunityResponse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class ConnectWrite extends AppCompatActivity {
    private static final int REQUEST_CODE = 0;
    AlertDialog alertDialog;
    ImageView close,ok,photo,imageview_add1,imageview_add2,imageview_add3;
    EditText title,content;
    ImageButton Button_close,Button_color,Button_ok,Button_photo,Button_align;
    ServiceApi service = RetrofitClient.getClient().create(ServiceApi.class);
    ArrayList<String> userinfo= ((MainActivity)MainActivity.context_main).Userinfo;
    ArrayList<Uri> filePathList=new ArrayList<Uri>();
    //0.이름, 1.생년월일, 2.성별, 3.아이디, 4.비밀번호(null), 5.중복 비밀번호(null), 6.닉네임, 7.전화번호, 8.키, 9.체중, 10.힘, 11.목표

    Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다
    // 년월일시분초 14자리 포멧
    SimpleDateFormat fourteen_format = new SimpleDateFormat("yyyy년MM월dd일HH시mm분ss초");
    //System.out.println(fourteen_format.format(date_now)); // 14자리 포멧으로 출력한다
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.community_write);
        title=(EditText) findViewById(R.id.title);
        content=(EditText) findViewById(R.id.content);
        Button_close = (ImageButton) findViewById(R.id.button_close);
        Button_color = (ImageButton) findViewById(R.id.button_color);
        Button_ok = (ImageButton) findViewById(R.id.button_ok);
        Button_photo = (ImageButton) findViewById(R.id.button_photo);
        Button_align = (ImageButton) findViewById(R.id.button_align);

        imageview_add1=(ImageView)findViewById(R.id.imageview_add1);
        imageview_add2=(ImageView)findViewById(R.id.imageview_add2);
        imageview_add3=(ImageView)findViewById(R.id.imageview_add3);


        Button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });
        Button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userinfo.get(1);
                List<MultipartBody.Part> photos = null;
                String time=fourteen_format.format(date_now);
                String img_name=time+userinfo.get(3);
                int img_num=filePathList.size();
                sendimg(img_name);//이미지 전송


                startWriteCommunity(new WriteCommunityData(userinfo.get(3), userinfo.get(6), fourteen_format.format(date_now),
                        title.getText().toString(),content.getText().toString(),"0",img_num));//게시글 작성

            }

        });
        Button_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ConnectWrite.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(ConnectWrite.this,Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    } else {
                        ActivityCompat.requestPermissions(ConnectWrite.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                1);
                    }
                }
                //Toast.makeText(ConnectWrite.this, "아직 지원하지 않는 기능입니다.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                //사진을 여러개 선택할수 있도록 한다
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),  REQUEST_CODE);
            }
        });//이미지 버튼 (갤러리로 이동)
        Button_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ConnectWrite.this, "아직 지원하지 않는 기능입니다.", Toast.LENGTH_SHORT).show();
            }

        });//컬러 버튼 사용x
        Button_align.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ConnectWrite.this, "아직 지원하지 않는 기능입니다.", Toast.LENGTH_SHORT).show();
            }

        });//aligin 버튼 사용x
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                //기존 이미지 지우기
                imageview_add1.setImageResource(0);
                imageview_add2.setImageResource(0);
                imageview_add3.setImageResource(0);

                //ClipData 또는 Uri를 가져온다
                Uri uri = data.getData();
                ClipData clipData = data.getClipData();

                //이미지 URI 를 이용하여 이미지뷰에 순서대로 세팅한다.
                if (clipData != null) {

                    for (int i = 0; i < 3; i++) {
                        if (i < clipData.getItemCount()) {
                            Uri urione = clipData.getItemAt(i).getUri();
                            switch (i) {
                                case 0:
                                    imageview_add1.setImageURI(urione);
                                    filePathList.add(urione);
                                    break;
                                case 1:
                                    imageview_add2.setImageURI(urione);
                                    filePathList.add(urione);
                                    break;
                                case 2:
                                    imageview_add3.setImageURI(urione);
                                    filePathList.add(urione);
                                    break;
                            }
                        }
                    }
                } else if (uri != null) {
                    imageview_add1.setImageURI(uri);
                }
            }
        }
    }//갤러리에서 이미지 선택 작업
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("작성중인 내용을 저장하지 않고 나가시겠습니까?");
        builder.setPositiveButton("확인", dialogListener);
        builder.setNegativeButton("취소", null);
        alertDialog = builder.create();
        alertDialog.show();
    }//뒤로가기 재설정

    DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            finish();
        }
    };

    private void startWriteCommunity(WriteCommunityData data) {
        service.WriteCommunity(data).enqueue(new Callback<WriteCommunityResponse>() {
            @Override
            public void onResponse(Call<WriteCommunityResponse> call, Response<WriteCommunityResponse> response) {
                WriteCommunityResponse result = response.body();
                Toast.makeText(ConnectWrite.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                if(result.getResult()=="true"){
                    finish();}
            }

            @Override
            public void onFailure(Call<WriteCommunityResponse> call, Throwable t) {
                Toast.makeText(ConnectWrite.this, "연결 오류 발생", Toast.LENGTH_SHORT).show();
                Log.e("연결 오류 발생", t.getMessage());
            }
        });
    }//게시글 작성
    private void sendimg(String img_name){
        // 여러 file들을 담아줄 ArrayList
        ArrayList<MultipartBody.Part> files = new ArrayList<>();
// 파일 경로들을 가지고있는 `ArrayList<Uri> filePathList`
        for (int i = 0; i < filePathList.size(); ++i) {

            // Uri 타입의 파일경로를 가지는 RequestBody 객체 생성
            String realpatch=getRealPathFromURI(filePathList.get(i));
            String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + realpatch;
            String fileuri=filePathList.get(i).toString();
            Log.e("TAG",fileuri);

            File file = null;
            try {
                file = getFile(getApplicationContext(),filePathList.get(i));
            } catch (IOException e) {
                e.printStackTrace();
            }
            RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            // 사진 파일 이름
            String fileName = img_name + i + ".png";
            // RequestBody로 Multipart.Part 객체 생성
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("photo", fileName, fileBody);

            // 추가
            files.add(filePart);

        }
        service.boardimg(files).enqueue(new Callback<BoardImgResponse>() {
            @Override
            public void onResponse(Call<BoardImgResponse> call, Response<BoardImgResponse> response) {
                BoardImgResponse result=response.body();
                Log.e("정상작동","이상 없음");
            }

            @Override
            public void onFailure(Call<BoardImgResponse> call, Throwable t) {

            }
        });

    }//이미지 전송

    /*URI에서 파일 객체를 가져 오거나 Android 10 이상 버전에서 URI를 파일 객체로 변환하는 방법
      https://www.python2.net/questions-1155628.htm*/
    public static File getFile(Context context, Uri uri) throws IOException {
        File destinationFilename = new File(context.getFilesDir().getPath() + File.separatorChar + queryName(context, uri));
        try (InputStream ins = context.getContentResolver().openInputStream(uri)) {
            createFileFromStream(ins, destinationFilename);
        } catch (Exception ex) {
            Log.e("Save File", ex.getMessage());
            ex.printStackTrace();
        }
        return destinationFilename;
    }
    public static void createFileFromStream(InputStream ins, File destination) {
        try (OutputStream os = new FileOutputStream(destination)) {
            byte[] buffer = new byte[4096];
            int length;
            while ((length = ins.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
        } catch (Exception ex) {
            Log.e("Save File", ex.getMessage());
            ex.printStackTrace();
        }
    }
    private static String queryName(Context context, Uri uri) {
        Cursor returnCursor =
                context.getContentResolver().query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }


    public String getRealPathFromURI(Uri contentUri) {

        String[] proj = { MediaStore.Images.Media.DATA };

        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        cursor.moveToNext();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
        Uri uri = Uri.fromFile(new File(path));

        cursor.close();
        return path;
    }//uri->path경로로

}
