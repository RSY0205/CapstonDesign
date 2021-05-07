package com.example.savehometraining.ui.community;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.savehometraining.Login.json.LoginData;
import com.example.savehometraining.Login.json.LoginResponse;
import com.example.savehometraining.MainActivity;
import com.example.savehometraining.R;
import com.example.savehometraining.TrainingActivity;
import com.example.savehometraining.retrofit.RetrofitClient;
import com.example.savehometraining.retrofit.ServiceApi;
import com.example.savehometraining.ui.community.json.LoadCommentData;
import com.example.savehometraining.ui.community.json.LoadCommentResponse;
import com.example.savehometraining.ui.community.json.LoadCommunityData;
import com.example.savehometraining.ui.community.json.LoadCommunityResponse;
import com.example.savehometraining.ui.community.json.WriteCommentData;
import com.example.savehometraining.ui.community.json.WriteCommentResponse;
import com.example.savehometraining.ui.community.json.WriteCommunityResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityFragment extends Fragment {
    SwipeRefreshLayout refreshLayout;
    public List<LoadCommunityResponse.response>board;
    ListView listview;
    FloatingActionButton button_fab;
    FloatingActionButton button_refresh;
    ServiceApi service = RetrofitClient.getClient().create(ServiceApi.class);
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.main_fragment_community, container, false);


        refreshLayout=(SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(CommunityFragment.this).attach(CommunityFragment.this).commit();

                refreshLayout.setRefreshing(false);
            }
        });



        listview =(ListView)view.findViewById(R.id.listView);
        button_fab =(FloatingActionButton) view.findViewById(R.id.fab);
        // button_refresh=(FloatingActionButton)view.findViewById(R.id.refresh);
        LoadBoard(new LoadCommunityData("Admin"));


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getContext(),Integer.toString(position), Toast.LENGTH_SHORT).show();
                Intent noticeboard=new Intent(getActivity(),ConnectNoticeboard.class);

                noticeboard.putExtra("nickname",board.get(position).getUser_nickname());
                noticeboard.putExtra("date",board.get(position).getBoard_date());
                noticeboard.putExtra("title",board.get(position).getBoard_title());
                noticeboard.putExtra("context",board.get(position).getBoard_context());
                noticeboard.putExtra("Board_Id",board.get(position).getBoard_number());
                noticeboard.putExtra("User_id",board.get(position).getUser_id());
                noticeboard.putExtra("Img_num",board.get(position).getImg_num());

                startActivity(noticeboard);
            }
        });//개시글로 이동

        button_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent writing=new Intent(getActivity(), ConnectWrite.class);
                startActivity(writing);
            }

        });//글작성 버튼
        /*
        button_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(CommunityFragment.this).attach(CommunityFragment.this).commit();
            }

        });//새로고침 버튼
        */

        return view;
    }



    private void dataSetting(){

        MyAdapterBoard mMyAdapter = new MyAdapterBoard();


        for (int i=0; i<20; i++) {
            mMyAdapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.icon), "name_" + i, "contents_" + i);
        }

        /* 리스트뷰에 어댑터 등록 */
        listview.setAdapter(mMyAdapter);
    }

    private void LoadBoard(LoadCommunityData data) {
        MyAdapterBoard mMyAdapter = new MyAdapterBoard();
        service.LoadCommunity(data).enqueue(new Callback<LoadCommunityResponse>() {
            @Override
            public void onResponse(Call<LoadCommunityResponse> call, Response<LoadCommunityResponse> response) {
                LoadCommunityResponse result = response.body();
                //Toast.makeText(CommunityFragment.This, result.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("연결 성공", result.getMessage());
                if(result.getResult()=="true"){
                    board=result.getResponse();
                    for(int i = 0; i<result.getResponse().size(); i++){
                        String context=result.getResponse().get(i).getBoard_context();
                        String[] arr = context.split("\n", 2);

                        mMyAdapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.icon), result.getResponse().get(i).getBoard_title(), arr[0]+"...");
                    }
                    listview.setAdapter(mMyAdapter);
                    setListViewHeightBasedOnItems(listview);
                }
            }
            @Override
            public void onFailure(Call<LoadCommunityResponse> call, Throwable t) {
                Log.e("로그인 에러 발생", t.getMessage());
            }
        });
    }

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
        }
    }//리스트뷰 크기 조절

}