package com.example.savehometraining.ui.Routine;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savehometraining.R;
import com.example.savehometraining.ui.community.MyItem;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyadapterRecommendRoutine extends BaseAdapter{
    public static Context context_main;
    public static ArrayList<String> sel_routineinfo=new ArrayList<>();

    /* 아이템을 세트로 담기 위한 어레이 */
    public static  ArrayList<MyItemRecommendRoutine> mItems = new ArrayList<>();


    public int getCount() {
        return mItems.size();
    }


    public MyItemRecommendRoutine getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static int select_position=-1;//position의 시작은 0부터임, 0으로 초기값 시 충돌

    public View getView(int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();

        /* 'listview_custom' Layout을 inflate하여 convertView 참조 획득 */
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.routine_listview_routinelist_custom, parent, false);
        }

        /* 'listview_custom'에 정의된 위젯에 대한 참조 획득 */
        TextView exercise_name = (TextView) convertView.findViewById(R.id.textview_exercisename) ;
        TextView exercise_count = (TextView) convertView.findViewById(R.id.textview_exercisecount) ;
        TextView exercise_set = (TextView) convertView.findViewById(R.id.textview_exerciseset) ;

        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        MyItemRecommendRoutine myItem = getItem(position);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */
        exercise_name.setText(myItem.getName());
        exercise_count.setText(myItem.getCount());
        exercise_set.setText(myItem.getSet());


        LinearLayout routinedata=(LinearLayout)convertView.findViewById(R.id.LinearLayout_routineList);

        //버튼 중복 방지
        if(select_position==-1||select_position==position) {
            routinedata.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (select_position==-1) {
                        routinedata.setBackground(ContextCompat.getDrawable(context, R.drawable.select));
                        select_position = position;
                    }
                    else if(select_position==position){
                        routinedata.setBackground(ContextCompat.getDrawable(context, R.drawable.border));
                        select_position = -1;
                    }
                    Log.e(Integer.toString(select_position), "선택 포지션");
                    Log.e(Integer.toString(position), "현재 포지션");

                }
            });
        }

        routinedata.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                dlg.setTitle("운동 삭제"); //제목
                dlg.setMessage("현재 선택한 운동을 삭제 하시겠습니까?"); // 메시지
//                버튼 클릭시 동작
                dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        //삭제 기능 구현
                        mItems.remove(position);
                        //토스트 메시지
                        notifyDataSetChanged();
                        Toast.makeText(context,"삭제 되었습니다.",Toast.LENGTH_SHORT).show();
                    }
                });
                dlg.setNegativeButton("취소",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        //토스트 메시지
                        Toast.makeText(context,"취소 되었습니다.",Toast.LENGTH_SHORT).show();
                    }
                });
                dlg.show();

                return true;
            }
        });

        return convertView;
    }
    public static void UpdateRoutine(){
        if(select_position==-1)
        {
            MyItemRecommendRoutine mItem = new MyItemRecommendRoutine();
            mItem.setName(sel_routineinfo.get(0));
            mItem.setCount(sel_routineinfo.get(1));
            mItem.setSet(sel_routineinfo.get(2));
            mItems.add(mItem);
        }
        else{
        mItems.get(select_position).setName(sel_routineinfo.get(0));
        mItems.get(select_position).setCount(sel_routineinfo.get(1));
        mItems.get(select_position).setSet(sel_routineinfo.get(2));}
    }

    /* 아이템 데이터 추가를 위한 함수. 자신이 원하는대로 작성 */
    public void addItem(String name,String count,String set) {
        MyItemRecommendRoutine mItem = new MyItemRecommendRoutine();

        /* MyItem에 아이템을 setting한다. */
        mItem.setName(name);
        mItem.setCount(count);
        mItem.setSet(set);

        /* mItems에 MyItem을 추가한다. */
        mItems.add(mItem);

    }
}
