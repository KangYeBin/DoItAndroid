package org.techtown.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

//manifests에 추가된 것을 확인 가능
//서비스는 비정상종료되면 다시 만들어진다
public class MyService extends Service {
    private final static String TAG = "MyService";

    public MyService() {
    }

    @Override   //서비스 객체가 메모리에 만들어질때
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate 호출됨");
    }

    @Override
    //startService는 서비스를 시작시키기보다 데이터 전달 목적으로 사용
    //이때 데이터가 들어있는 intent와 함께 onStartCommand가 호출
    //command에 따라 구분해서 동작을 수행시킬 수 있다
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand 호출됨");

        if (intent != null) {
            processCommand(intent); //인텐트 처리 함수
        } else {
            return Service.START_STICKY;    //재시작
        }


        return super.onStartCommand(intent, flags, startId);
    }

    public void processCommand(Intent intent) {
        String command = intent.getStringExtra("command");
        String name = intent.getStringExtra("name");

        Log.d(TAG, "command : " + command + ", name : " + name);

        try {
            Thread.sleep(5000); //5초 쉬고
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent showIntent = new Intent(getApplicationContext(), MainActivity.class);
        showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|
                Intent.FLAG_ACTIVITY_SINGLE_TOP|    //원래 띄어져있는 화면을 그대로
                Intent.FLAG_ACTIVITY_CLEAR_TOP);
        showIntent.putExtra("command", command);
        showIntent.putExtra("name" , name + " from service");
        startActivity(showIntent);


    }

    @Override   //서비스 객체가 메모리에서 사라질때
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy 호출됨");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}