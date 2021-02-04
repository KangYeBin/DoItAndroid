package org.techtown.volley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

//Volley는 핸들러와 스레드가 보이지 않고 내부에서 알아서 처리

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private TextView textView;
    private Button button;
    static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        editText.setText("http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888&targetDt=20120101");
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlStr = editText.getText().toString();
                request(urlStr);
            }
        });
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public void request (String urlStr) {
        //먼저 요청 객체 만들기
        StringRequest request = new StringRequest(Request.Method.GET, urlStr
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                println("응답 : " + response);
                //응답받은 문자열을 처리하는 함수
                processResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                println("에러 : " + error.toString());
            }
        }) {
            //요청 파라미터(웹->서버로 데이터를 보내는 방법)를 처리하는 메소드
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };

        request.setShouldCache(false);  //기존 응답을 가져오지 않도록
        requestQueue.add(request);      //요청 객체를 넣으면 자동으로 요청/응답을 처리
        println("요청 보냄");
    }

    public void processResponse(String response) {  //GSON 라이브러리 사용
        Gson gson = new Gson();
        MovieList movieList = gson.fromJson(response, MovieList.class);   //파싱할 클래스 지정
        println("영화 정보의 수 : " + movieList.boxOfficeResult.dailyBoxOfficeList.size());
    }

    public void println(String data) {
        textView.append(data + "\n");
    }
}