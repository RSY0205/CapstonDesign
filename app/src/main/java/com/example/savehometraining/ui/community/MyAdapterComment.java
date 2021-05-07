package com.example.savehometraining.ui.community;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.savehometraining.MainActivity;
import com.example.savehometraining.R;
import com.example.savehometraining.retrofit.RetrofitClient;
import com.example.savehometraining.retrofit.ServiceApi;
import com.example.savehometraining.ui.community.json.DelBoardResponse;
import com.example.savehometraining.ui.community.json.DelCommentData;
import com.example.savehometraining.ui.community.json.DelCommentResponse;
import com.example.savehometraining.ui.community.json.ModifyCommentData;
import com.example.savehometraining.ui.community.json.ModifyCommentResponse;
import com.example.savehometraining.ui.community.json.WriteCommentData;
import com.example.savehometraining.ui.community.json.WriteCommunityData;
import com.example.savehometraining.ui.community.json.WriteCommunityResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAdapterComment extends BaseAdapter {
    /* 아이템을 세트로 담기 위한 어레이 */
    private ArrayList<MyItem> mItems = new ArrayList<>();
    ArrayList<String> userinfo= ((MainActivity)MainActivity.context_main).Userinfo;
    Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다
    SimpleDateFormat fourteen_format = new SimpleDateFormat("yyyy년MM월dd일HH시mm분");
    ServiceApi service = RetrofitClient.getClient().create(ServiceApi.class);

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public MyItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();

        // 'listview_custom' Layout을 inflate하여 convertView 참조 획득
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.community_listview_comment_custom, parent, false);
        }

        // 'listview_custom'에 정의된 위젯에 대한 참조 획득
        ImageView iv_img = (ImageView) convertView.findViewById(R.id.imageView_comment);
        final TextView textview_commentnickname = (TextView) convertView.findViewById(R.id.textview_commentnickname);
        final TextView textview_commentdate = (TextView) convertView.findViewById(R.id.textview_commentdate);
        final TextView textview_comment=(TextView) convertView.findViewById(R.id.textview_comment);
        final Button button_modifycomment=(Button) convertView.findViewById(R.id.button_modifycomment);
        final Button button_delcomment=(Button) convertView.findViewById(R.id.button_delcomment);



        button_modifycomment.setOnClickListener(new View.OnClickListener() {
            MyItem myItem = getItem(pos);
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(context);

                ad.setTitle("수정");       // 제목 설정
                ad.setMessage("수정 내용을 입력해 주세요");   // 내용 설정

                // EditText 삽입하기
                final EditText et = new EditText(context);
                ad.setView(et);
                et.setText(myItem.getContents());

                // 확인 버튼 설정
                ad.setPositiveButton("수정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v("로그", "Yes Btn Click");

                        // Text 값 받아서 로그 남기기
                        String value = et.getText().toString();
                        Log.v("로그", value);
                        ModifyComment(new ModifyCommentData(myItem.getComments_number(),value,fourteen_format.format(date_now)));
                        dialog.dismiss();     //닫기
                        // Event
                    }
                });

                // 취소 버튼 설정
                ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v("로그","No Btn Click");
                        dialog.dismiss();     //닫기
                        // Event
                    }
                });

// 창 띄우기
                ad.show();

            }
        });

        button_delcomment.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                dlg.setTitle("게시글 삭제!"); //제목
                dlg.setMessage("게시글을 삭제 하시 겠습니까?"); // 메시지
//                버튼 클릭시 동작
                dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        MyItem myItem = getItem(pos);
                        DelComment(new DelCommentData(myItem.getComments_number()));
                    }
                });
                dlg.setNegativeButton("취소",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        //토스트 메시지
                        Toast.makeText(context,"취소를 눌르셨습니다.",Toast.LENGTH_LONG).show();
                        Log.e("취소", "onClick: 취소 되었습니다.");
                    }
                });
                dlg.show();
            }
        });


        // 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용
        MyItem myItem = getItem(pos);

        // 각 위젯에 세팅된 아이템을 뿌려줌
        iv_img.setImageDrawable(myItem.getIcon());
        textview_commentnickname.setText(myItem.getName());
        textview_commentdate.setText(myItem.getDate());
        textview_comment.setText(myItem.getContents());
        // 위젯에 대한 이벤트리스너를 지정

        if(myItem.getbutton_life()==1)
        {button_modifycomment.setVisibility(View.VISIBLE);
            button_delcomment.setVisibility(View.VISIBLE);
            button_modifycomment.setEnabled(true);
            button_delcomment.setEnabled(true);
            //Log.e("true", userinfo.get(3)+"/"+writer_id);
            }
        else{
            button_modifycomment.setVisibility(View.INVISIBLE);
            button_delcomment.setVisibility(View.INVISIBLE);
            button_modifycomment.setEnabled(false);
            button_delcomment.setEnabled(false);
            //Log.e("false", userinfo.get(3)+"/"+writer_id);
        }

        return convertView;
    }

    /* 아이템 데이터 추가를 위한 함수. 자신이 원하는대로 작성 */
    public void addItem(Drawable img, String name, String date,String contents,String User_id, int comments_number) {

        MyItem mItem = new MyItem();

        /* MyItem에 아이템을 setting한다. */
        mItem.setIcon(img);
        mItem.setName(name);
        mItem.setDate(date);
        mItem.setContents(contents);
        mItem.setComments_number(comments_number);
        if(userinfo.get(3).equals(User_id))
        {mItem.setbutton_life(1);}//같으면 생명 O
        else{mItem.setbutton_life(0);}//다르면 생명 x

        /* mItems에 MyItem을 추가한다. */
        mItems.add(mItem);
    }

    private void DelComment(DelCommentData data){
        service.DelCommnet(data).enqueue(new Callback<DelCommentResponse>() {
            @Override
            public void onResponse(Call<DelCommentResponse> call, Response<DelCommentResponse> response) {
                DelCommentResponse result=response.body();
                if(result.getResult()=="true"){
                    //Log.e("연결 성공",result.getMessage());
                    Log.e("확인", "onClick: 삭제 되었습니다.");
                    //Toast.makeText(context,"게시글이 삭제 되었습니다.",Toast.LENGTH_LONG).show();
                     }

            }

            @Override
            public void onFailure(Call<DelCommentResponse> call, Throwable t) {

            }
        });
    }
    private void ModifyComment(ModifyCommentData data){
        service.ModifyComment(data).enqueue(new Callback<ModifyCommentResponse>() {
            @Override
            public void onResponse(Call<ModifyCommentResponse> call, Response<ModifyCommentResponse> response) {
                ModifyCommentResponse result=response.body();
                Log.e("확인", "onClick: 수정 되었습니다.");
            }

            @Override
            public void onFailure(Call<ModifyCommentResponse> call, Throwable t) {

            }
        });
    }

}
