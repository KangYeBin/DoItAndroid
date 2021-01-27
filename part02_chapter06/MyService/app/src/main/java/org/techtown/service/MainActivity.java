package org.techtown.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();

                Intent intent = new Intent(getApplicationContext(), MyService.class);
                intent.putExtra("command", "show");
                intent.putExtra("name", name);
                startService(intent);
            }
        });

        Intent intent = getIntent();
        processIntent(intent);
    }

    //MainActivity가 메모리에 올려져있는 상황이면 onCreate()가 호출되지 않기 때문에 onNewIntent() 실행
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        processIntent(intent);
    }

    public void processIntent(Intent intent) {
        if (intent != null) {
            String command = intent.getStringExtra("command");
            String name = intent.getStringExtra("name");
            Toast.makeText(this, "command : " + command + ", name : " + name, Toast.LENGTH_LONG).show();
        }
    }
}