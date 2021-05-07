package com.example.savehometraining.ui.calendar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.savehometraining.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyAdaptSchedule extends BaseAdapter {

    /* 아이템을 세트로 담기 위한 어레이 */
    private ArrayList<MyItemed> mItems = new ArrayList<>();

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public MyItemed getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();
        final int pos = position;

        /* 'listview_custom' Layout을 inflate하여 convertView 참조 획득 */
        if (convertView == null) {



            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.calendarlistview_custom, parent, false);
        }

        /* 'listview_custom'에 정의된 위젯에 대한 참조 획득 */
        //ImageView iv_img = (ImageView) convertView.findViewById(R.id.iv_img) ;
        TextView tv_schedule = (TextView) convertView.findViewById(R.id.tv_schedule) ;
        TextView tv_scheduletext = (TextView) convertView.findViewById(R.id.tv_scheduletext) ;

        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        MyItemed myItem = getItem(pos);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */
        tv_schedule.setText(myItem.getDate().toString());
        tv_scheduletext.setText(myItem.getContents());

        /* (위젯에 대한 이벤트리스너를 지정하고 싶다면 여기에 작성하면된다..)  */


        return convertView;
    }

    /* 아이템 데이터 추가를 위한 함수. 자신이 원하는대로 작성 */
    public void addItem(String dates , String contents) {

        MyItemed mItem = new MyItemed();

        /* MyItem에 아이템을 setting한다. */
        mItem.setDate(dates);
        mItem.setContents(contents);

        /* mItems에 MyItem을 추가한다. */
        mItems.add(mItem);

    }
}










