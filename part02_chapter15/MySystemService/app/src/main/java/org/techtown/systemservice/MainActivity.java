package org.techtown.systemservice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button button, button2;
    private TextView output1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output1 = findViewById(R.id.output1);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppList();   //설치된 앱의 리스트 가져오기
            }
        });

        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnectivity();    //네트워크 연결상태 확인
            }
        });
    }

    public void getAppList() {
        PackageManager manager = getPackageManager();
        List<ApplicationInfo> appInfoList = manager.getInstalledApplications(PackageManager.GET_META_DATA);

        for (int i=0; i<appInfoList.size(); i++) {
            ApplicationInfo info = appInfoList.get(i);
            println("#" + i + " -> " + info.loadLabel(manager).toString() + ", " + info.packageName);
        }
    }

    public void checkConnectivity() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                println("Wifi로 설정됨");
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                println("일반망으로 설정됨");
            }

            println("연결 여부 : " + info.isConnected());
        }
        else {
            println("데이터 통신이 안됩니다");
        }
    }

    public void println(String message) {
        output1.append(message + "\n");
    }
}