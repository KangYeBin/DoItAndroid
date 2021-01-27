package org.techtown.receiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SmsActivity extends AppCompatActivity {
    private TextView textView2, textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);

        Intent intent = getIntent();
        processIntent(intent);

    }

    //이미 메모리에 올라가 있는 activity일 경우
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        processIntent(intent);
    }

    public void processIntent(Intent intent) {
        if (intent != null) {
            String sender = intent.getStringExtra("sender");
            String contents = intent.getStringExtra("contents");

            textView2.setText(sender);
            textView3.setText(contents);
        }
    }
}