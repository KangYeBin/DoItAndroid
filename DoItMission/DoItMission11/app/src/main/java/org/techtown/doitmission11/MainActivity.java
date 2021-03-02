package org.techtown.doitmission11;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText input;
    TextView output;
    Button button;

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
                String message = input.getText().toString();
                sendToService(message); //메시지를 서비스로 보내기
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) { //액티비티가 이미 만들어져있을때 인텐트 처리
        processIntent(intent);
        super.onNewIntent(intent);
    }

    public void processIntent(Intent intent) {
        Log.d("main", "processIntent 호출됨");
        if (intent != null) {
            String message = intent.getStringExtra("message");
            output.setText(message);
        }
    }

    public void sendToService(String message) {
        Intent intent = new Intent(getApplicationContext(), MyService.class);
        intent.putExtra("message", message);
        startService(intent);   //서비스 시작
    }
}