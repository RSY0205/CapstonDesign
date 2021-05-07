package com.example.savehometraining.Login;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.savehometraining.R;
import com.example.savehometraining.ui.calendar.MyItemed;

import java.util.ArrayList;

public class MyAdaptExercise extends BaseAdapter {

    private ArrayList<Preperence_item> list;

    public MyAdaptExercise(ArrayList<Preperence_item> a){ // 생성자(배열 3개 1로 초기화)
        list=a;
        for(int i=0;i<size;i++)
        {
            check[i]=1;
        }
    }

    /* 아이템을 세트로 담기 위한 어레이 */
    //private ArrayList<MyIteme> mItems = new ArrayList<>();

    @Override
    public int getCount() {
        //return mItems.size();
        return list.size();
    }

    /*
    @Override
    public MyIteme getItem(int position) {
        return mItems.get(position);
    }*/
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public boolean isChecked(int position){
        return list.get(position).checked;
    }


    public int[] getCheck() {
        int position=getCount();
        int result[]=new int[position];
        //String a=String.valueOf(position);
        //Log.e("포지션:",a);
        for(int i=0;i<position;i++) {
            result[i] = check[i];
        }
        return result;
    }

    int size=50;
    int check[]=new int[size];


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();
        final int pos = position;

        /* 'listview_custom' Layout을 inflate하여 convertView 참조 획득 */
        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.exerciselistview_custom, parent, false);
        }

        /* 'listview_custom'에 정의된 위젯에 대한 참조 획득 */
        //ImageView iv_img = (ImageView) convertView.findViewById(R.id.iv_img) ;
        CheckBox cb_ex1 = (CheckBox)convertView.findViewById(R.id.checkbox_exercise_one);


        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        //MyIteme myItem = getItem(pos);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */
        cb_ex1.setChecked(list.get(position).checked);
        //cb_ex1.setChecked(myItem.getB());
        //cb_ex1.setText(myItem.getTitle().toString());
        cb_ex1.setText(list.get(position).ItemString);
        //cb_ex1.setText(myItem.getTitle().toString());


        /* (위젯에 대한 이벤트리스너를 지정하고 싶다면 여기에 작성하면된다..)  */

        /*
        cb_ex1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    check[pos]=6;
                    String a=String.valueOf(pos);
                    Log.e("sd",a);
                }else{
                    check[pos]=1;
                }
            }
        });
*/

        cb_ex1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                boolean newState=!list.get(position).isChecked();
                list.get(position).checked=newState;
                if(list.get(position).checked){
                    check[pos]=6;
                    String a=String.valueOf(pos);
                }
                else{
                    check[pos]=1;
                }
            }
        });
        cb_ex1.setChecked(isChecked(position));
        return convertView;
    }

    /* 아이템 데이터 추가를 위한 함수. 자신이 원하는대로 작성
    public void addItem(boolean b,String title) {

        MyIteme mItem = new MyIteme();

        /* MyItem에 아이템을 setting한다.

        mItem.setTitle(title);
        mItem.setB(b);


        /* mItems에 MyItem을 추가한다.
        mItems.add(mItem);

    }
    */
}










