package com.example.savehometraining.ui.community;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.savehometraining.MainActivity;
import com.example.savehometraining.R;
import com.example.savehometraining.retrofit.RetrofitClient;
import com.example.savehometraining.retrofit.ServiceApi;
import com.example.savehometraining.ui.community.json.DelBoardData;
import com.example.savehometraining.ui.community.json.DelBoardResponse;
import com.example.savehometraining.ui.community.json.LoadCommentData;
import com.example.savehometraining.ui.community.json.LoadCommentResponse;
import com.example.savehometraining.ui.community.json.LoadCommunityData;
import com.example.savehometraining.ui.community.json.LoadCommunityResponse;
import com.example.savehometraining.ui.community.json.WriteCommentData;
import com.example.savehometraining.ui.community.json.WriteCommentResponse;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnectNoticeboard extends AppCompatActivity {
    //게시판
    TextView textview_nickname,textview_date,textview_maintitle,textview_maintext;

    //댓글 목록 불러오기
    ListView listview;

    //댓글작성및 버튼, 개시글 작성/삭제 버튼
    Button button_sendcomment,button_modifyboard,button_delboard;
    Button button_modifycommnet,button_delcomment;
    EditText edtittext_comment;
    ImageView imageview1,imageview2,imageview3;
    String writer_id;//작성자 id

    ServiceApi service = RetrofitClient.getClient().create(ServiceApi.class);

    //유저 정보
    ArrayList<String> userinfo= ((MainActivity)MainActivity.context_main).Userinfo;
    //0.이름, 1.생년월일, 2.성별, 3.아이디, 4.비밀번호(null), 5.중복 비밀번호(null), 6.닉네임, 7.전화번호, 8.키, 9.체중, 10.힘, 11.목표

    Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다
    // 년월일시분초 14자리 포멧
    SimpleDateFormat fourteen_format = new SimpleDateFormat("yyyy년MM월dd일HH시mm분");
    //System.out.println(fourteen_format.format(date_now)); // 14자리 포멧으로 출력한다
    SwipeRefreshLayout refreshLayout;
    //새로고침

    protected void onCreate(Bundle saveInstanceState) {


        super.onCreate(saveInstanceState);
        setContentView(R.layout.community_noticeboard);

        //댓글 목록,댓글 작성
        listview = (ListView)findViewById(R.id.Listview_comment);
        button_sendcomment=(Button)findViewById(R.id.button_sendcomment);
        edtittext_comment=(EditText)findViewById(R.id.edittext_comment);

        //게시판 수정,삭제 버튼
        button_modifyboard=(Button)findViewById(R.id.button_modifyboard);
        button_delboard=(Button)findViewById(R.id.button_delyboard);

        //댓글 수정, 삭제 버튼
        button_modifycommnet=(Button)findViewById(R.id.button_modifycomment);
        button_delcomment=(Button)findViewById(R.id.button_delcomment);

        //이미지 뷰 연결
        imageview1=(ImageView)findViewById((R.id.imageView_noticeboard1));
        imageview2=(ImageView)findViewById((R.id.imageView_noticeboard2));
        imageview3=(ImageView)findViewById((R.id.imageView_noticeboard3));
/*
        String url="http://52.78.221.27/img/123.png";
        Picasso.get()
                .load(url).
                error(R.drawable.mainlogo).
                into(imageview1);

 */

        //댓글 목록 불러오기
        int Board_number=getIntent().getIntExtra("Board_Id",9999);
        LoadComment(new LoadCommentData(Board_number));


        //개시글 불러오기
        textview_nickname=(TextView)findViewById(R.id.textview_nickname);
        textview_date=(TextView)findViewById(R.id.textview_date);
        textview_maintitle=(TextView)findViewById(R.id.textview_maintitle);
        textview_maintext=(TextView)findViewById(R.id.textview_maintext);
        textview_nickname.setText(getIntent().getStringExtra("nickname"));

        /*시간*/
        String date=getIntent().getStringExtra("date");
        String[] splitdate =date.split("분");
        splitdate[0]+="분";
        textview_date.setText(splitdate[0]);

        //내용 가져오기
        textview_maintitle.setText(getIntent().getStringExtra("title"));
        textview_maintext.setText(getIntent().getStringExtra("context"));
        writer_id=getIntent().getStringExtra("User_id");
        String user_id=getIntent().getStringExtra("User_id");
        int number=getIntent().getIntExtra("Board_Id",9999);
        int Img_num=getIntent().getIntExtra("Img_num",0);
        Log.e("Img_num:", Integer.toString(Img_num));

        String url="http://52.78.221.27/communityimg/"+date+user_id;
        //이미지 등록
        for(int i=1;i<=Img_num;i++){
            if(i==1){Picasso.get().load(url+"0.png").error(R.drawable.mainlogo).into(imageview1);}
            if(i==2){Picasso.get().load(url+"1.png").error(R.drawable.mainlogo).into(imageview2);}
            if(i==3){Picasso.get().load(url+"2.png").error(R.drawable.mainlogo).into(imageview3);}
            Log.e("i=",Integer.toString(i));

        }


        /*유저 정보 일치 여부확인 및 버튼 작동 제어*/
        if(userinfo.get(3).equals(writer_id))
        {
            button_modifyboard.setVisibility(View.VISIBLE);
            button_delboard.setVisibility(View.VISIBLE);
            button_modifyboard.setEnabled(true);
            button_delboard.setEnabled(true);
            Log.e("true", userinfo.get(3)+"/"+writer_id);
        }
        else
        {
            button_modifyboard.setVisibility(View.INVISIBLE);
            button_delboard.setVisibility(View.INVISIBLE);
            button_modifyboard.setEnabled(false);
            button_delboard.setEnabled(false);
            Log.e("false", userinfo.get(3)+"/"+writer_id);

        }

        refreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_layout_comment);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startActivity(getIntent());
                finish();
                refreshLayout.setRefreshing(false);
            }
        });//새로고침


        button_delboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(ConnectNoticeboard.this);
                dlg.setTitle("게시글 삭제!"); //제목
                dlg.setMessage("게시글을 삭제 하시 겠습니까?"); // 메시지
                //버튼 클릭시 동작
                dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        DelBoard(new DelBoardData(Board_number));
                        finish();
                    }
                });
                dlg.setNegativeButton("취소",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        //토스트 메시지
                        Toast.makeText(getApplicationContext(),"취소를 눌르셨습니다.",Toast.LENGTH_LONG).show();
                        Log.e("취소", "onClick: 취소 되었습니다.");
                    }
                });
                dlg.show();
            }
        });//게시글 삭제 버튼 이벤트

        button_modifyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Modifyboard=new Intent(getApplicationContext(),ConnectModifyBoard.class);

                Modifyboard.putExtra("title",getIntent().getStringExtra("title"));
                Modifyboard.putExtra("context",getIntent().getStringExtra("context"));
                Modifyboard.putExtra("Board_Id",number);

                startActivity(Modifyboard);

            }
        });//게시글 수정 버튼 이벤트

        button_sendcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ConnectNoticeboard.this, edtittext_comment.getText(), Toast.LENGTH_SHORT).show();
                startWriteComment(new WriteCommentData(Board_number, userinfo.get(3), userinfo.get(6), fourteen_format.format(date_now),edtittext_comment.getText().toString()),edtittext_comment );
                startActivity(getIntent());
                finish();

            }
        });//댓글 작성 버튼 이벤트


    }

    /*retrofit*/
    private void startWriteComment(WriteCommentData data, EditText text) {
        service.WriteComment(data).enqueue(new Callback<WriteCommentResponse>() {
            @Override
            public void onResponse(Call<WriteCommentResponse> call, Response<WriteCommentResponse> response) {
                WriteCommentResponse result = response.body();
                Toast.makeText(ConnectNoticeboard.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                if(result.getResult()=="true"){
                    text.setText("");
                }
            }
            @Override
            public void onFailure(Call<WriteCommentResponse> call, Throwable t) {
                Toast.makeText(ConnectNoticeboard.this, "연결 오류 발생", Toast.LENGTH_SHORT).show();
                Log.e("연결 오류 발생", t.getMessage());
            }
        });
    }//댓글 작성

    private void LoadComment(LoadCommentData data) {
        MyAdapterComment mMyAdapter = new MyAdapterComment();
        service.LoadComment(data).enqueue(new Callback<LoadCommentResponse>() {
            @Override
            public void onResponse(Call<LoadCommentResponse> call, Response<LoadCommentResponse> response) {
                LoadCommentResponse result = response.body();
                //Toast.makeText(CommunityFragment.This, result.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("연결 성공", result.getMessage());
                if(result.getResult()=="true"){
                    for(int i = 0; i<result.getResponse().size(); i++){
                        mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon), result.getResponse().get(i).getUser_nickname(),result.getResponse().get(i).getComments_date(),result.getResponse().get(i).getComments_context(),result.getResponse().get(i).getUser_id(),result.getResponse().get(i).getComments_number());

                        //댓글 불러오도록 수정 하기
                    }
                    listview.setAdapter(mMyAdapter);
                    setListViewHeightBasedOnItems(listview);
                }
            }
            @Override
            public void onFailure(Call<LoadCommentResponse> call, Throwable t) {
                Log.e("로그인 에러 발생", t.getMessage());
            }
        });
    }//댓글 불러오기

    private void DelBoard(DelBoardData data) {
        service.DelBoard(data).enqueue(new Callback<DelBoardResponse>() {
            @Override
            public void onResponse(Call<DelBoardResponse> call, Response<DelBoardResponse> response) {
                DelBoardResponse result=response.body();
                if(result.getResult()=="true"){
                    //Log.e("연결 성공",result.getMessage());
                    //Log.e("확인", "onClick: 삭제 되었습니다.");
                    Toast.makeText(getApplicationContext(),"게시글이 삭제 되었습니다.",Toast.LENGTH_LONG).show();}
            }

            @Override
            public void onFailure(Call<DelBoardResponse> call, Throwable t) {
                Log.e("연결 실패",t.getMessage());
            }
        });
    }//게시글 삭제

    private void LoadBoard(LoadCommunityData data, TextView textview_nickname, TextView textview_date, TextView textview_maintitle, TextView textview_maintext ) {
        MyAdapterBoard mMyAdapter = new MyAdapterBoard();
        service.LoadCommunity(data).enqueue(new Callback<LoadCommunityResponse>() {
            @Override
            public void onResponse(Call<LoadCommunityResponse> call, Response<LoadCommunityResponse> response) {
                LoadCommunityResponse result = response.body();
                //Toast.makeText(CommunityFragment.This, result.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("연결 성공", result.getMessage());
                if(result.getResult()=="true"){
                    //textview_nickname textview_date textview_maintitle textview_maintext

                }
            }
            @Override
            public void onFailure(Call<LoadCommunityResponse> call, Throwable t) {
                Log.e("로그인 에러 발생", t.getMessage());
            }
        });
    }//게시글 불러오기(미사용)

    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                float px = 500 * (listView.getResources().getDisplayMetrics().density);
                item.measure(View.MeasureSpec.makeMeasureSpec((int) px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);
            // Get padding
            int totalPadding = listView.getPaddingTop() + listView.getPaddingBottom();

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + totalPadding;
            listView.setLayoutParams(params);
            listView.requestLayout();
            //setDynamicHeight(listView);
            return true;

        } else {
            return false;
        }//리스트뷰 크기 조절
    }

}
