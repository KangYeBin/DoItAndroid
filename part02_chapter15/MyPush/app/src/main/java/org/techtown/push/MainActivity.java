package org.techtown.push;
//push를 받는 입장

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView output1, output2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output1 = findViewById(R.id.output1);
        output2 = findViewById(R.id.output2);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(
                this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String token = instanceIdResult.getToken(); //등록 ID
                println("등록 id : " + token);
            }
        });

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String instanceId = FirebaseInstanceId.getInstance().getId();
                println("확인된 인스턴스 id : " + instanceId);
            }
        });

        //MainActivity 화면이 떠있지 않은 상황에서 푸쉬 데이터를 전달받았을때
        Intent intent = getIntent();
        processIntent(intent);  //인텐트 처리
    }

    //이미 MainActivity 화면이 띄워져서 메모리에 만들어져있는 상황이라면
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        processIntent(intent);  //인텐트 처리
    }

    public void processIntent(Intent intent) {
        if (intent != null) {
            String from = intent.getStringExtra("from");
            String contents = intent.getStringExtra("contents");

            println("DATA : " + from + ", " + contents);
            output1.setText("수신 데이터 : " + contents);
        }
    }

    public void println(String message) {
        output2.append(message + "\n");
        Log.d("Main", message);
    }
}