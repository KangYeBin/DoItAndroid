package org.techtown.doitmission12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText input;
    TextView output;
    Button button;
    MyReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.input);
        output = findViewById(R.id.output);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = input.getText().toString();
                sendToService(data);    //data를 Service로
            }
        });

        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("org.techtown.broadcast.SHOW");
        registerReceiver(receiver, filter);
    }

    class MyReceiver extends BroadcastReceiver {    //브로드캐스트 수신 리시버
        @Override
        public void onReceive(Context context, Intent intent) { // sendBroadcast로 수신받으면 인텐트 처리
            processIntent(intent);
        }
    }

    public void processIntent(Intent intent) {
        Log.d("Main", "processIntent 처리됨");
        if (intent != null) {
            String data = intent.getStringExtra("data");
            output.setText("받은 결과 : " + data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(receiver);   //리시버 등록 해제
    }

    public void sendToService(String data) {
        Intent intent = new Intent(getApplicationContext(), MyService.class);
        intent.putExtra("data", data);
        startService(intent);
    }
}