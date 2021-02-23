package org.techtown.push;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public MyFirebaseMessagingService() {
    }

    //등록 아이디가 갱신되었을때 호출
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        Log.d("Service", "onNewToken 호출됨 : " + token);
    }

    //푸쉬 메시지를 받았을때 호출
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d("Service", "onMessageReceived 호출됨");

        String from = remoteMessage.getFrom(); //상대방의 고유 아이디
        Map<String, String> data = remoteMessage.getData();    //상대방이 보낸 데이터
        String contents = data.get("contents");
        Log.d("Service", "from : " + from + ", contents : " + contents);

        sendToActivity(from, contents);
    }


    public void sendToActivity(String from, String contents) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);    //메인 액티비티로 보내기
        intent.putExtra("from", from);
        intent.putExtra("contents", contents);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getApplicationContext().startActivity(intent);
    }

}