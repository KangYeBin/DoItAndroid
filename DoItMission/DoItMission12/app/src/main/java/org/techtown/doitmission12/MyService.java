package org.techtown.doitmission12;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Service", "onStartCommand 호출됨");
        if (intent != null) {
            String data = intent.getStringExtra("data");
            sendToActivity(data);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void sendToActivity(String data) {   //브로드캐스트 수신자를 통해 data 전달
        Log.d("Service", "sendToActivity 호출됨");
        Intent intent = new Intent();
        intent.setAction("org.techtown.broadcast.SHOW");
        intent.putExtra("data", data);

        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}