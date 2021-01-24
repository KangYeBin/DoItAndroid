package org.techtown.doitmission04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private EditText editText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "전송할 메시지 : " + editText.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        //EditText에서 입력값이 변화할 때마다 특정 작업을 수행해야할 경우
        //TextWatcher 인터페이스를 구현해서 EditText의 이벤트 리스너로 등록하여 처리한다.
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Text가 변경되었을 때 호출
                byte[] bytes = null;
                try {
                    bytes = s.toString().getBytes("KSC5601");   //KSC5601 규격에서 바이트 크기 계산
                    int strCount = bytes.length;
                    textView.setText(strCount + " / 80바이트");
                } catch(UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                try {
                    byte[] strBytes = str.getBytes("KSC5601");
                    if(strBytes.length > 80) {
                        s.delete(s.length()-2, s.length()-1);
                    }
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

    }
}