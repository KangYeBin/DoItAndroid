package org.techtown.pushsend;
//push를 보내는 입장

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText input1;
    private TextView output1;
    private Button button;
    static RequestQueue requestQueue;
    static String regId;
    //push에서의 등록 ID, 원래는 인터넷을 통해서 보내주는 것이 일반적

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input1 = findViewById(R.id.input1);
        output1 = findViewById(R.id.output1);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = input1.getText().toString();
                send(input);

            }
        });

        //requestQueue에 관한 인스턴스 객체
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public void send(String input) {
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("priority", "high");    //중요도

            JSONObject dataObj = new JSONObject();
            dataObj.put("contents", input);
            requestData.put("data", dataObj);

            JSONArray idArray = new JSONArray();
            idArray.put(0, regId);
            requestData.put("registration_ids", idArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(      //요청
                Request.Method.POST,
                "https://fcm.googleapis.com/fcm/send",
                requestData,
                new Response.Listener<JSONObject>() {           //응답
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) { //요청을 보낼때 헤더정보를 포함해서 보내야할 경우


            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "key=OOOO");
                //key는 Firebase -> 클라우드 메시징에서 서버 키 확인

                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };

        request.setShouldCache(false);
        requestQueue.add(request);
        println("요청 보냄");
    }

    public void println(String message) {
        output1.append(message + "\n");
    }
}