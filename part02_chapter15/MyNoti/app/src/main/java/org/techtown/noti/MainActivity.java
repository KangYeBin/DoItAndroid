package org.techtown.noti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private static String CHANNEL_ID = "channel1";
    private static String CHANNEL_NAME = "Channel1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNoti1();    //상단에 알림 띄우기
            }
        });
    }

    public void showNoti1() {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //이전 단말까지 지원하려면 compat
        NotificationCompat.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (manager.getNotificationChannel(CHANNEL_ID) == null) {
                manager.createNotificationChannel(new NotificationChannel(
                        CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT));
            }
            builder = new NotificationCompat.Builder(this, CHANNEL_ID);

        } else {    //이전 버전은 channel이 필요없다
            builder = new NotificationCompat.Builder(this);
        }

        builder.setContentTitle("간단 알림");
        builder.setContentText("간단 메시지입니다");
        builder.setSmallIcon(android.R.drawable.ic_menu_view);

        Notification noti = builder.build();
        manager.notify(1, noti);
    }
}