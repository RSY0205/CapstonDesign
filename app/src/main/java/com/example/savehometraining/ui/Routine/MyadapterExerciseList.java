package com.example.savehometraining.ui.Routine;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.savehometraining.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyadapterExerciseList extends BaseAdapter {
    private ArrayList<MyItemExerciseList> mItems = new ArrayList<>();
    public int getCount() {
        return mItems.size();
    }


    public MyItemExerciseList getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();

        /* 'listview_custom' Layout을 inflate하여 convertView 참조 획득 */
        if (convertView == null) {



            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.routine_listview_exercise_custom, parent, false);
        }

        /* 'listview_custom'에 정의된 위젯에 대한 참조 획득 */
        ImageButton exceriset_button1=(ImageButton)convertView.findViewById(R.id.imageButton_excercise1) ;
        ImageButton exceriset_button2=(ImageButton)convertView.findViewById(R.id.imageButton_excercise2) ;
        TextView exercise_name1 = (TextView) convertView.findViewById(R.id.textview_execerisename1) ;
        TextView exercise_name2 = (TextView) convertView.findViewById(R.id.textview_execerisename2) ;
        LinearLayout LinearLayout_ImageView2=(LinearLayout)convertView.findViewById(R.id.LinearLayout_secondexerciseView);

        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        MyItemExerciseList myItem = getItem(position);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */
        //exceriset_button1.setText(myItem.getName());
        //exceriset_button1.setText(myItem.getCount());
        if(myItem.getExcericsename2()=="0")
        {
            Picasso.get().load(myItem.getImageURL1()).error(R.drawable.mainlogo).into(exceriset_button1);
            exercise_name1.setText(myItem.getExcericsename1());
            LinearLayout_ImageView2.setVisibility(View.INVISIBLE);
            LinearLayout_ImageView2.setEnabled(false);
        }

        else {
            Picasso.get().load(myItem.getImageURL1()).error(R.drawable.mainlogo).into(exceriset_button1);
            Picasso.get().load(myItem.getImageURL2()).error(R.drawable.mainlogo).into(exceriset_button2);
            exercise_name1.setText(myItem.getExcericsename1());
            exercise_name2.setText(myItem.getExcericsename2());
            LinearLayout_ImageView2.setVisibility(View.VISIBLE);
            LinearLayout_ImageView2.setEnabled(true);}


        /* (위젯에 대한 이벤트리스너를 지정하고 싶다면 여기에 작성하면된다..)  */
        Dialog dialog;
        dialog=new Dialog(context);
        dialog.setContentView(R.layout.routine_excerisebutton_dialog);

        exceriset_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog(dialog, myItem.getImageURL1(),myItem.getExcericsename1());
            }
        });
        exceriset_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog(dialog, myItem.getImageURL2(),myItem.getExcericsename2());
            }
        });

        return convertView;
    }

    /* 아이템 데이터 추가를 위한 함수. 자신이 원하는대로 작성 */
    public void addItem(String name1,String name2) {
        MyItemExerciseList mItem = new MyItemExerciseList();

        /* MyItem에 아이템을 setting한다. */
        if(name2=="0")
        {String URL1="http://52.78.221.27/exerciseimg/list/"+name1.replaceAll("\\p{Z}", "")+".jpg";
            mItem.setImageURL1(URL1);
            //mItem.setImageURL2("NULL");

        }

        else {
            Log.e("", "http://52.78.221.27/exerciseimg/list/" + name1.replaceAll("\\p{Z}", "") + ".jpg");
            String URL1 = "http://52.78.221.27/exerciseimg/list/" + name1.replaceAll("\\p{Z}", "") + ".jpg";
            String URL2 = "http://52.78.221.27/exerciseimg/list/" + name2.replaceAll("\\p{Z}", "") + ".jpg";
            mItem.setImageURL1(URL1);
            mItem.setImageURL2(URL2);
        }

        mItem.setExcericsename1(name1);
        mItem.setExcericsename2(name2);

        /* mItems에 MyItem을 추가한다. */
        mItems.add(mItem);
    }

    public void showdialog(Dialog dialog,String ImageURL,String FitnessName){
        ImageView FitnessImgageVIew;
        TextView FitnessNameTextView;
        Button OK,NO;
        EditText Count,Set;
        TextView X_Min,Sec;
        dialog.setContentView(R.layout.routine_excerisebutton_dialog);
        FitnessImgageVIew=dialog.findViewById(R.id.ImageView_Dialog);
        FitnessNameTextView=dialog.findViewById(R.id.Textview_dialog);
        Count=dialog.findViewById(R.id.EditText_dialog_count_min);
        Set=dialog.findViewById(R.id.EditText_dialog_set_sec);
        OK=dialog.findViewById(R.id.Button_DialogOK);
        NO=dialog.findViewById(R.id.Button_DialogNO);
        X_Min= dialog.findViewById(R.id.Textview_min_x);
        Sec=dialog.findViewById(R.id.Textview_sec);
        Picasso.get().load(ImageURL).error(R.drawable.mainlogo).into(FitnessImgageVIew);
        FitnessNameTextView.setText(FitnessName);


        //만약 분초가 필요하면 editview 수정
        if(FitnessName.equals("플랭크")||FitnessName.equals("러닝")||FitnessName.equals("홈사이클"))
        {
            X_Min.setText("분");
            Sec.setText("초");
            Count.setHint("분");
            Set.setHint("초");
        }

        dialog.show();
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Count.getText().toString().equals("") || Count.getText().toString() == null|| Count.getText().toString().equals("0")){
                    Toast.makeText(v.getContext(), "갯수를 입력해주세요!", Toast.LENGTH_SHORT).show();
                }
                else if(Set.getText().toString().equals("") || Set.getText().toString() == null|| Set.getText().toString().equals("0")){
                    Toast.makeText(v.getContext(), "세트를 입력해주세요!", Toast.LENGTH_SHORT).show();

                }
                else {
                    MyadapterRoutine.sel_routineinfo.clear();
                    MyadapterRoutine.sel_routineinfo.add(FitnessNameTextView.getText().toString());
                    MyadapterRoutine.sel_routineinfo.add(Count.getText().toString());
                    MyadapterRoutine.sel_routineinfo.add(Set.getText().toString());
                    MyadapterRoutine.UpdateRoutine(RoutineFragment.sel_page);

                    dialog.dismiss(); // 다이얼로그 닫기
                }
            }
        });
        NO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // 다이얼로그 닫기
            }
        });
    }
}
