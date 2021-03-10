package org.techtown.doitmission29;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private TextView friendNumber;
    private EditText editTextContents;
    private Button sendButton;
    RecyclerView recyclerView;
    FriendAdapter adapter;
    FriendInfo item1, item2;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        friendNumber = findViewById(R.id.friendNumber);
        editTextContents = findViewById(R.id.editTextContents);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        adapter = new FriendAdapter();
        item1 = new FriendInfo("eyVOiO6qszQ:APA91bHQzKRDTK34jlhYn8rNeK3RVoH2HfrWjQRLqtCYCWR8lcykcgh4H5lpZWox61yvW6W4X4SpFDddX30KI-4SPjVQxlKEUozDvZbHtPGDja9ktPaZiGWkqR4dClBWY-xXxZiojbpc", "친구1", "010-1111-1111");
        item2 = new FriendInfo("eyVOiO6qszQ:APA91bHQzKRDTK34jlhYn8rNeK3RVoH2HfrWjQRLqtCYCWR8lcykcgh4H5lpZWox61yvW6W4X4SpFDddX30KI-4SPjVQxlKEUozDvZbHtPGDja9ktPaZiGWkqR4dClBWY-xXxZiojbpc", "친구2", "010-2222-2222");
        adapter.addItem(item1);
        adapter.addItem(item2);
        friendNumber.setText(adapter.items.size() + "명");

        adapter.setOnItemClickListener(new OnFriendItemClickListner() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d("onItemClick", "클릭됨");
                Toast.makeText(getApplicationContext(), "아이템 선택됨 / 이름 : " + adapter.getItem(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);

        sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contents = editTextContents.getText().toString();
                send(contents);
            }
        });

        queue = Volley.newRequestQueue(getApplicationContext());

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult result) {
                String newToken = result.getToken();

                println("등록 id : " + newToken);
            }
        });

        new Thread() {
            public void run() {
                String instanceId = FirebaseInstanceId.getInstance().getId();
                println("확인된 인스턴스 id : " + instanceId);
            }
        }.start();

        Intent intent = getIntent();
        if (intent != null) {
            processIntent(intent);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        println("onNewIntent() called.");

        if (intent != null) {
            processIntent(intent);
        }

        super.onNewIntent(intent);
    }

    public void send(String contents) {
        JSONObject requestData = new JSONObject();

        try {
            requestData.put("priority", "high");

            JSONObject dataObj = new JSONObject();
            dataObj.put("contents", contents);
            requestData.put("data", dataObj);

            JSONArray idArray = new JSONArray();
            for (int i = 0; i < adapter.getItemCount(); i++) {
                FriendInfo item = adapter.getItem(i);
                String regId = item.getRegId();
                println("regId #" + i + " : " + regId);

                idArray.put(i, regId);
            }
            requestData.put("registration_ids", idArray);

        } catch(Exception e) {
            e.printStackTrace();
        }

        sendData(requestData, new SendResponseListener() {
            @Override
            public void onRequestCompleted() {
                println("onRequestCompleted() 호출됨");

                Toast.makeText(getApplicationContext(), "공지사항 전송함", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRequestStarted() {
                println("onRequestStarted() 호출됨.");
            }

            @Override
            public void onRequestWithError(VolleyError error) {
                println("onRequestWithError() 호출됨.");
            }
        });
    }

    public interface SendResponseListener {
        public void onRequestStarted();
        public void onRequestCompleted();
        public void onRequestWithError(VolleyError error);
    }

    public void sendData(JSONObject requestData, final SendResponseListener listener) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "https://fcm.googleapis.com/fcm/send",
                requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onRequestCompleted();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onRequestWithError(error);
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String,String>();
                headers.put("Authorization","key=AAAA_b17X-4:APA91bEOAH0oYbwFx2x4aeNJpz-X-i6BBsEaKz4Khyet7Q9xTGW6SIgJec66_TBPqi4ZxUbCtJ87ZcFNbLweg0IzubLbsfKRtcvmaFxV-6kqcZVFJVhzvDHExfeK4rltvfhWA83MoxOPVhTUFnEOUnt9xiC6nt_Ypg");

                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        request.setShouldCache(false);
        listener.onRequestStarted();
        queue.add(request);
    }

    private void processIntent(Intent intent) {
        String from = intent.getStringExtra("from");
        if (from == null) {
            println("from is null.");
            return;
        }

        String contents = intent.getStringExtra("contents");

        println("DATA : " + from + ", " + contents);
        Toast.makeText(getApplicationContext(), "공지사항 수신함 : " + contents, Toast.LENGTH_LONG).show();
    }

    public void println(String data) {
        Log.d("FMS", data);
    }

}