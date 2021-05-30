package com.example.savehometraining;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import android.widget.EditText;
import org.w3c.dom.Text;
import java.io.DataOutputStream;

public class ArdunioTest extends AppCompatActivity {
    TextView receiveText;
    Button button ;
    Button CountResetButton ;
    Socket socket = null;
    static int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aduinotest);

        receiveText = (TextView) findViewById(R.id.receiveText);
        button = (Button)findViewById((R.id.button));
        CountResetButton = (Button)findViewById((R.id.button2));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //messageText.setText("");
                TimerTask tt = new TimerTask() { // 타이머를 줘서 연속적인 소켓통신을 함으로써 버튼변수 대기
                    @Override
                    public void run() {
                        MyClientTask myClientTask = new MyClientTask("192.168.0.12", // 라즈베리파이의 ip주소로 8091포트에 ...이라는 텍스트를 보냅니다.
                                Integer.parseInt("8091"), "send");
                        myClientTask.execute(); // 처음 버튼 클릭했을시에 소켓통신에 연결
                    }
                };
                Timer timer = new Timer(); // 타이머 시작
                timer.schedule(tt,0,200); // 타아머의 속도는 0.2초로 설정(버튼이 눌리는 시간)
            }
        });
        CountResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimerTask tt = new TimerTask() {

                    @Override
                    public void run() {
                        MyClientTask myClientTask = new MyClientTask("192.168.0.12",Integer.parseInt("8091"), "reset");
                        myClientTask.execute();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(tt,0,200);
            }
        });
    }
    public class MyClientTask extends AsyncTask<Void, Void, Void> {
        String dstAddress;
        int dstPort;
        String response = "";
        String myMessage = "";

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
                out.write("send".getBytes());

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
                    response += byteArrayOutputStream.toString("UTF-8");
                }
                response = "sever:" + response;
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
        @Override
        protected void onPostExecute(Void result) {
            receiveText.setText(response);
            super.onPostExecute(result);
            if (receiveText.getText().toString().contains("push")) { //소캣서버에서 push라는 값이 들어올경우에 이벤트 생성
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    String Noti_Channel_ID = "Noti";
                    String Noti_Channel_Group_ID = "Noti_Group";
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    NotificationChannel notificationChannel = new NotificationChannel(Noti_Channel_ID, Noti_Channel_Group_ID, importance);
                    if (notificationManager.getNotificationChannel(Noti_Channel_ID) != null) {
                    } else {                                                                 //채널이 없을시에 채널을 생성해줍니다.
                        notificationManager.createNotificationChannel(notificationChannel);
                    }
                    notificationManager.createNotificationChannel(notificationChannel);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), Noti_Channel_ID) //알림 설정 하는부분
                            .setLargeIcon(null).setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setWhen(System.currentTimeMillis()).setShowWhen(true)
                            .setAutoCancel(true).setPriority(NotificationCompat.PRIORITY_MAX)
                            .setContentTitle("버튼 감지")
                            .setDefaults(Notification.DEFAULT_VIBRATE)
                            .setDefaults(Notification.DEFAULT_SOUND)
                            .setContentText("버튼을 눌렀습니다.");
                    notificationManager.notify(0, builder.build()); // 알림 생성하기

                }
            }
        }
    }
}