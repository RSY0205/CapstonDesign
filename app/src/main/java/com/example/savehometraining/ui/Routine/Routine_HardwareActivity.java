package com.example.savehometraining.ui.Routine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import android.widget.Toast;

import com.example.savehometraining.MainActivity;
import com.example.savehometraining.R;
import com.example.savehometraining.retrofit.RetrofitClient;
import com.example.savehometraining.retrofit.ServiceApi;
import com.example.savehometraining.ui.calendar.Calendar_write;
import com.example.savehometraining.ui.calendar.json.WriteFitnessData;
import com.example.savehometraining.ui.calendar.json.WriteFitnessResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Routine_HardwareActivity extends AppCompatActivity {
    TextView tv_receiveText;
    Button okButton;
    Button finishButton;
    Button exitButton;

    TextView tv_exCountText;
    TextView tv_leCountText;
    TextView tv_exNameText;
    TextView tv_countText;
    TextView tv_setText;
    TextView tv_hardSetText;

    int exListCount = 0; //운동 Length 카운트 하여 주입 후 다음 운동 갱신 시 사용

    Socket socket = null;
    static int counter = 0;

    int year,month,day;
    String Syear, Smonth, Sday;
    String todayDate = "";

    String dstAddress;
    int dstPort;
    String response = "";
    String myMessage = "";

    String saveCount = "";      //운동한 개수 저장
    String saveActivity = "";   //운동의 이름 저장

    static TimerTask cTask;
    static TimerTask rTask;

    GregorianCalendar today = new GregorianCalendar();

    ServiceApi service = RetrofitClient.getClient().create(ServiceApi.class);
    // 운동 배열 받아온 후 배열 선언하여 저장 및 textView 에 적용
    ArrayList<MyItemRoutine> mItems = MyadapterRoutine.mItems;
    ArrayList<String> userinfo= ((MainActivity)MainActivity.context_main).Userinfo;

    public int getCount() { return mItems.size(); }
    public MyItemRoutine getItem(int position) { return mItems.get(position); }
    public static int select_position= 0;
    MyItemRoutine myItem = new MyItemRoutine();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routine_socket);

        year=today.get(today.YEAR);
        month=today.get(today.MONTH);
        day=today.get(today.DAY_OF_MONTH);

        //년
        Syear = String.valueOf(year);

        //월
        if ((month + 1) <= 9)
            Smonth = "0" + String.valueOf(month + 1);
        else
            Smonth = String.valueOf(month + 1);

        //일
        if (day <= 9)
            Sday = "0" + String.valueOf(day);
        else
            Sday = String.valueOf(day);

        todayDate = Syear + "년" + Smonth + "월" + Sday + "일";


        okButton = (Button) findViewById((R.id.Button_fitness_ok));
        finishButton = (Button) findViewById(R.id.Button_fitness_finish);
        exitButton = (Button) findViewById(R.id.Button_fitness_exit);
        tv_exNameText = (TextView) findViewById(R.id.tv_exname);        //운동 이름

        tv_exCountText = (TextView) findViewById(R.id.tv_excount);      //총 운동 개수
        tv_leCountText = (TextView) findViewById(R.id.tv_lecount);      //남은 운동

        tv_countText = (TextView) findViewById(R.id.tv_count);          //총 카운트
        tv_receiveText = (TextView) findViewById(R.id.tv_hardcount);    //수행한 카운트**

        tv_setText = (TextView) findViewById(R.id.tv_set);              //총 세트 수
        tv_hardSetText = (TextView) findViewById(R.id.tv_hardset);      //수행한 세트 수


        //소켓통신 테스크 생성
        //0.2초의 속도로 소켓통신 서버에 "send"라는 값을 보냄
        //타이머 생성
        final Timer timer = new Timer(); // 타이머 시작
        cTask = socketTTMaker();
        timer.schedule(cTask, 500, 200);

        cTask.cancel();
        rTask = countResetTTMaker();
        timer.schedule(rTask, 0); // 즉시 resetTask 발동
        cTask = socketTTMaker();
        timer.schedule(cTask, 500, 200); // 타아머의 속도는 0.2초로 설정(버튼이 눌리는 시간)

        tv_exCountText.setText(String.valueOf(getCount())); //운동개수 갱신
        tv_leCountText.setText(String.valueOf(getCount())); //남은운동 갱신

        //루틴에서 첫 운동 불러오기
        myItem = getItem(select_position);
        select_position++;

        if(myItem.getName().equals("플랭크")||myItem.getName().equals("러닝")||myItem.getName().equals("홈사이클"))
        {
            // Dialog 생성
            AlertDialog.Builder dig = new AlertDialog.Builder(Routine_HardwareActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog);  // 새 AlertDialog 생성
            dig.setMessage("플랭크, 러닝, 홈사이클은 갯수 측정이 불가능합니다.")  // 메시지 설정
                    .setTitle("경고");  // 제목 설정
            // 버튼 설정
            dig.setNeutralButton("측정 취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cTask.cancel();
                            select_position =0;
                            finish();
                        }
                    }
//                        .setPositiveButton("다음 운동", new DialogInterface.OnClickListener(){
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String stLecount = tv_leCountText.getText().toString();
//                        int inLecount = Integer.parseInt(stLecount);
//                        inLecount = inLecount - 1;
//                        tv_leCountText.setText(Integer.toString(inLecount)); //남은 운동 수 -1
//                        //배열에서 운동 갱신하기
//                        myItem = getItem(select_position);
//                        tv_exNameText.setText(myItem.getName());
//                        tv_countText.setText(myItem.getCount());
//                        tv_setText.setText(myItem.getSet());
//                        select_position++;
//
//                    }
//                })
            );
            dig.setCancelable(false);
            dig.show();
        }
        else{
            tv_exNameText.setText(myItem.getName());
            tv_countText.setText(myItem.getCount());
            tv_setText.setText(myItem.getSet());
        }
//        myItem = getItem(select_position);
//        tv_exNameText.setText(myItem.getName());
//        tv_countText.setText(myItem.getCount());
//        tv_setText.setText(myItem.getSet());
//        select_position++;

        //다음 세트버튼 클릭시 현재 카운트값 저장 후 소켓통신 테스크 캔슬 후 카운팅 리셋 테스크 발동 뒤 소켓통신 테스크 생성
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stHardCount = tv_receiveText.getText().toString();
                int inHardCount = Integer.parseInt(stHardCount);
                String stCount = tv_countText.getText().toString();
                int inCount = Integer.parseInt(stCount);

                if(inHardCount >= inCount) { //운동 개수를 충족하는 경우
                    saveCount = tv_receiveText.getText().toString(); //카운팅 값 int로 캐스팅하여 저장
                    saveActivity = tv_exNameText.getText().toString(); //운동 이름 저장
                    String stSet = tv_hardSetText.getText().toString();
                    int inSet = Integer.parseInt(stSet);
                    inSet = inSet + 1;
                    tv_hardSetText.setText(Integer.toString(inSet)); //수행한 세트 수 +1
                    SaveCalendar(new WriteFitnessData(userinfo.get(3),todayDate,saveActivity,saveCount,"0","0","From Hardware activity"));//content 내용);
                    cTask.cancel();
                    rTask = countResetTTMaker();
                    timer.schedule(rTask, 0); // 즉시 resetTask 발동
                    cTask = socketTTMaker();
                    timer.schedule(cTask, 500, 200); // 타아머의 속도는 0.2초로 설정(버튼이 눌리는 시간)
                    //서버에 1세트 수행 값 전송 (saveActivity, saveCount)
                }
                else { //운동 개수를 충족하지 못는 경우
                    Toast.makeText(Routine_HardwareActivity.this,"먼저 운동을 완료해 주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stHardSet = tv_hardSetText.getText().toString();
                String stSet = tv_setText.getText().toString();
                int inHardSet = Integer.parseInt(stHardSet);
                int inSet = Integer.parseInt(stSet);

                if(inHardSet >= inSet) { //세트 개수를 충족하는 경우
                    // Dialog 생성
                    AlertDialog.Builder dlg = new AlertDialog.Builder(Routine_HardwareActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog);  // 새 AlertDialog 생성
                    dlg.setMessage("추가 SET를 진행하겠습니까??")  // 메시지 설정
                            .setTitle("경고");  // 제목 설정

                    // 버튼 설정
                    // "예" 를 눌렀을 때
                    dlg.setPositiveButton("예", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String stHardCount = tv_receiveText.getText().toString();
                            int inHardCount = Integer.parseInt(stHardCount);
                            String stCount = tv_countText.getText().toString();
                            int inCount = Integer.parseInt(stCount);

                            if(inHardCount >= inCount) { //운동 개수를 충족하는 경우
                                saveCount = tv_receiveText.getText().toString(); //카운팅 값 int로 캐스팅하여 저장
                                saveActivity = tv_exNameText.getText().toString(); //운동 이름 저장
                                String stSet = tv_hardSetText.getText().toString();
                                int inSet = Integer.parseInt(stSet);
                                inSet = inSet + 1;
                                tv_hardSetText.setText(Integer.toString(inSet)); //수행한 세트 수 +1
                                SaveCalendar(new WriteFitnessData(userinfo.get(3),todayDate,saveActivity,saveCount,"0","0","From Hardware activity"));//content 내용);
                                cTask.cancel();
                                rTask = countResetTTMaker();
                                timer.schedule(rTask, 0); // 즉시 resetTask 발동
                                cTask = socketTTMaker();
                                timer.schedule(cTask, 500, 200); // 타아머의 속도는 0.2초로 설정(버튼이 눌리는 시간)
                                //서버에 1세트 수행 값 전송 (saveActivity, saveCount)
                            }
                            else { //운동 개수를 충족하지 못하는 경우
                                Toast.makeText(Routine_HardwareActivity.this,"추가 SET를 수행한 뒤 '예'를 눌러 주세요",Toast.LENGTH_SHORT).show();
                            }
                        }
                        // "아니요" 를 눌렀을 때
                    }) .setNeutralButton("아니요", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if(tv_leCountText.getText().toString().equals("1")) { //마지막 운동일 경우
                                tv_exNameText.setText("Finish");
                                tv_exCountText.setText("0"); //총 운동 개수 값 초기화
                                tv_setText.setText("0"); //불러온 세트 값 초기화
                                tv_hardSetText.setText("0"); //측정한 세트 값 초기화
                                tv_countText.setText("0"); //불러온 카운트 값 초기화
                                String stLecount = tv_leCountText.getText().toString();
                                int inLecount = Integer.parseInt(stLecount);
                                inLecount = inLecount - 1;
                                tv_leCountText.setText(Integer.toString(inLecount)); //남은 운동 수 -1
                                cTask.cancel(); //측정한 카운트 값 초기화
                                rTask = countResetTTMaker();
                                timer.schedule(rTask, 0); // 즉시 resetTask 발동
                                cTask = socketTTMaker();
                                timer.schedule(cTask, 500, 200); // 타아머의 속도는 0.2초로 설정(버튼이 눌리는 시간)
                                select_position = 0;
                                Toast.makeText(Routine_HardwareActivity.this,"루틴을 완료하였습니다.",Toast.LENGTH_SHORT).show();
                                ///////////////// 서버에 루틴 완료 값 전송
                            }
                            else {// 다음 운동이 존재할 경우
                                cTask.cancel();
                                rTask = countResetTTMaker();
                                timer.schedule(rTask, 0); // 즉시 resetTask 발동
                                cTask = socketTTMaker();
                                timer.schedule(cTask, 500, 200);
                                tv_hardSetText.setText("0");
                                String stLecount = tv_leCountText.getText().toString();
                                int inLecount = Integer.parseInt(stLecount);
                                inLecount = inLecount - 1;
                                tv_leCountText.setText(Integer.toString(inLecount)); //남은 운동 수 -1
                                //배열에서 운동 갱신하기
                                myItem = getItem(select_position);
                                select_position++;
                                if(myItem.getName().equals("플랭크")||myItem.getName().equals("러닝")||myItem.getName().equals("홈사이클"))
                                {
                                    // Dialog 생성
                                    AlertDialog.Builder dag = new AlertDialog.Builder(Routine_HardwareActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog);  // 새 AlertDialog 생성
                                    dag.setMessage("플랭크, 러닝, 홈사이클은 갯수 측정이 불가능합니다.")  // 메시지 설정
                                            .setTitle("경고");  // 제목 설정

                                    // 버튼 설정
                                    dag.setNeutralButton("측정 취소", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            cTask.cancel();
                                            select_position =0;
                                            finish();
                                        }
//                                        .setPositiveButton("다음 운동", new DialogInterface.OnClickListener(){
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            String stLecount = tv_leCountText.getText().toString();
//                                            int inLecount = Integer.parseInt(stLecount);
//                                            inLecount = inLecount - 1;
//                                            tv_leCountText.setText(Integer.toString(inLecount)); //남은 운동 수 -1
//                                            //배열에서 운동 갱신하기
//                                            myItem = getItem(select_position);
//                                            tv_exNameText.setText(myItem.getName());
//                                            tv_countText.setText(myItem.getCount());
//                                            tv_setText.setText(myItem.getSet());
//                                            select_position++;
//
//                                        }
//                                    })
                                    });
                                    dag.setCancelable(false);
                                    dag.show();
                                }
                                else{
                                    tv_exNameText.setText(myItem.getName());
                                    tv_countText.setText(myItem.getCount());
                                    tv_setText.setText(myItem.getSet());
                                }
                            }
                        }
                    });

                    dlg.setCancelable(false);  // Dialog 를 띄웠을 때 뒤로가기 버튼 무효
                    dlg.show();  // Dialog 띄우기
                }
                else { //세트 개수를 충족하지 못는 경우
                    Toast.makeText(Routine_HardwareActivity.this,"먼저 SET를 완료해 주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dialog 생성
                AlertDialog.Builder dlg = new AlertDialog.Builder(Routine_HardwareActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog);  // 새 AlertDialog 생성
                dlg.setMessage("남은 운동이 있다면 먼저 운동을 완료해 주세요, 정말 종료하시겠습니까?")  // 메시지 설정
                        .setTitle("!경고!");  // 제목 설정

                // 버튼 설정
                // "예" 를 눌렀을 때
                dlg.setPositiveButton("종료", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cTask.cancel();
                        select_position =0;
                        Toast.makeText(Routine_HardwareActivity.this,"종료되었습니다",Toast.LENGTH_SHORT).show();
                        finish();  // Activity 종료하기
                    }
                    // "아니요" 를 눌렀을 때
                }) .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Dialog 창이 닫히고 원래의 Activity 로 돌아감
                    }
                });
                dlg.setCancelable(false);
                dlg.show();
            }
        });
    }

    public void SaveCalendar(WriteFitnessData data) {
        service.WriteFitness(data).enqueue(new Callback<WriteFitnessResponse>() {
            @Override
            public void onResponse(Call<WriteFitnessResponse> call, Response<WriteFitnessResponse> response) {
                WriteFitnessResponse result = response.body();
                Toast.makeText(Routine_HardwareActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<WriteFitnessResponse> call, Throwable t) {
                Toast.makeText(Routine_HardwareActivity.this, "에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("에러 발생", t.getMessage());
            }
        });
    }

    public void onBackPressed() {
        Toast.makeText(this, "뒤로가기를 누르셨습니다.", Toast.LENGTH_SHORT).show();
        cTask.cancel();
        select_position =0;
        super.onBackPressed();
    }

    public TimerTask socketTTMaker() {
        TimerTask tempTask = new TimerTask() {
            @Override
            public void run() {
                MyClientTask socketTask = new MyClientTask("192.168.0.31", Integer.parseInt("8091"), "send");
                socketTask.execute(); // "send" 값 전송하여 소켓 통신 서버에 연결
            }
        };
        return tempTask;
    }

    public TimerTask countResetTTMaker() {
        TimerTask tempTask = new TimerTask() {
            @Override
            public void run() {
                MyClientTask resetTask = new MyClientTask("192.168.0.31", Integer.parseInt("8091"), "reset");
                resetTask.execute(); // 리셋 버튼 클릭시 소켓통신 서버에 "reset" 값 전송
            }
        };
        return tempTask;
    }

    //송수신하는 데이터를 받아오는 통신속도와 어떤 형식으로 받을지 결정
    public class MyClientTask extends AsyncTask<Void, Void, Void> {

        //constructor
        MyClientTask(String addr, int port, String message) {
            dstAddress = addr;
            dstPort = port;
            myMessage = message;
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            Socket socket = null;
            myMessage = myMessage.toString();
            try {
                socket = new Socket(dstAddress, dstPort);
                //송신
                OutputStream out = socket.getOutputStream();
                out.write(myMessage.getBytes());

                //수신
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
                byte[] buffer = new byte[1024];
                int bytesRead;
                InputStream inputStream = socket.getInputStream();
                /*
                 * notice:
                 * inputStream.read() will block if no data return
                 */
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
//                    response += byteArrayOutputStream.toString("UTF-8");
                    response = byteArrayOutputStream.toString("UTF-8");
                }
                response = response; //라즈베리에서 받아온 값 출력부


            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "IOException: " + e.toString();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        //일정한 값이 들어올 경우 테스크 설치
        @Override
        protected void onPostExecute(Void result) {
            tv_receiveText.setText(response);
            super.onPostExecute(result);
            if (tv_receiveText.getText().toString().contains("push")) { //소캣서버에서 push라는 값이 들어올경우에 이벤트 생성
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    String Noti_Channel_ID = "Noti";
                    String Noti_Channel_Group_ID = "Noti_Group";
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    NotificationChannel notificationChannel = new NotificationChannel(Noti_Channel_ID, Noti_Channel_Group_ID, importance);
                    if (notificationManager.getNotificationChannel(Noti_Channel_ID) != null) {
                    } else {
                        notificationManager.createNotificationChannel(notificationChannel);
                    }
                    notificationManager.createNotificationChannel(notificationChannel);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), Noti_Channel_ID)
                            .setLargeIcon(null).setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setWhen(System.currentTimeMillis()).setShowWhen(true)
                            .setAutoCancel(true).setPriority(NotificationCompat.PRIORITY_MAX)
                            .setContentTitle("버튼 감지")
                            .setDefaults(Notification.DEFAULT_VIBRATE)
                            .setDefaults(Notification.DEFAULT_SOUND)
                            .setContentText("버튼을 눌렀습니다.");
                    notificationManager.notify(0, builder.build());

                }
            }
        }
    }
}