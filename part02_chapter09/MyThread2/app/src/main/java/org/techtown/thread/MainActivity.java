package org.techtown.thread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView textView;
//    private MainHandler handler;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackgroundThread thread = new BackgroundThread();
                thread.start(); //start 내에서 run 실행
            }
        });

//        handler = new MainHandler();

    }

    //스레드를 상속하는 클래스를 하나 정의하고 클래스의 객체를 만들어서 스레드를 실행한다.
    class BackgroundThread extends Thread {
        int value = 0;
        //스레드를 시작하면 자동으로 실행
        public void run() {
            for (int i=0; i<100; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                value++;
                Log.d("MyThread", "value : " + value);

                //Runnable 객체로 전달해도 가능
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("값 : " + value);
                    }
                });

                //메인 스레드에 있는 뷰를 건드릴 수 없으므로 비정상종료
                //핸들러를 이용해서 해결 가능
                //textView.setText("값 : " + value);

                //message에 데이터 넣기
//                Message message = handler.obtainMessage();
//                Bundle bundle = new Bundle();
//                bundle.putInt("value", value);
//                message.setData(bundle);
//                handler.sendMessage(message);
            }
        }
    }

//    class MainHandler extends Handler {
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//
//            Bundle bundle = msg.getData();
//            int value = bundle.getInt("value");
//            textView.setText("값 : " + value);
//        }
//    }



}