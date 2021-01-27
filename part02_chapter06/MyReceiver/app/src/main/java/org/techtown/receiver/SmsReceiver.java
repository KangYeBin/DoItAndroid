package org.techtown.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {
    //Receiver는 특정 상황이 되면 자동으로 실행
    //manifests에서 intent-filter를 통해 특정 action만 걸러낼 수 있다.

    //sms 수신 기능은 권한이 필요하다 (manifests에 추가, dependency에 외부 라이브러리 추가)
    private static final String TAG = "SmsReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive 호출됨");

        Bundle bundle = intent.getExtras();
        SmsMessage[] messages = parseSmsMessage(bundle); //bundle에서 sms message만 파싱

        if (messages != null && messages.length>0) {
            String sender = messages[0].getOriginatingAddress();    //보낸 사람의 전화번호
            String contents = messages[0].getMessageBody();

            Log.d(TAG, "sender : " + sender + ", contents : " + contents);

            //context 안에 startActivity가 정의되어있으므로
            sendToActivity(context, sender, contents);
        }
    }

    public void sendToActivity(Context context, String sender, String contents) {
        Intent intent = new Intent(context, SmsActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("sender", sender);
        intent.putExtra("contents", contents);
        context.startActivity(intent);
    }

    public SmsMessage[] parseSmsMessage(Bundle bundle) {
        Object[] objects = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[objects.length];

        int smsCount = objects.length;
        for (int i=0; i<smsCount; i++) {
            //안드로이드 버전별로 다른 코드를 넣을 수 있다
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String format = bundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[]) objects[i], format);
            } else {
                messages[i] = SmsMessage.createFromPdu((byte[]) objects[i]);
            }
        }
        return messages;
    }
}