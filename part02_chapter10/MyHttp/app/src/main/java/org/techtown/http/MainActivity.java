package org.techtown.http;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button button;
    private TextView textView;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //웹에 요청
                String urlStr = editText.getText().toString();

                //네트워킹이므로 스레드에서 동작하도록
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        request(urlStr);
                    }
                }).start();

            }
        });
    }

    public void request(String urlStr) {       //웹으로 요청하는 함수
        StringBuilder builder = new StringBuilder();    //String을 계속 붙여서 만드는 것

        try {
            URL url = new URL(urlStr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            if (httpURLConnection != null) {
                httpURLConnection.setConnectTimeout(10000); //연결이 안되면 끊어버리기, 타임아웃
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);

                int resCode = httpURLConnection.getResponseCode();  //요청하고 응답받기

                //InputStreamReader는 문자열 데이터를 주고받을때 간단하게 하도록
                //데이터를 읽어들일때 buffer를 사용해서 성능 향상
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line = null;
                while(true) {
                    line = reader.readLine();   //한줄 읽어들이기
                    if (line == null){
                        break;
                    }

                    builder.append(line + "\n");

                }
                reader.close();
                httpURLConnection.disconnect();
            }
            println("응답 -> " + builder.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void println(String data) {
        //UI 업데이트이므로 핸들러 사용
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.setText(data);
            }
        });
    }
}