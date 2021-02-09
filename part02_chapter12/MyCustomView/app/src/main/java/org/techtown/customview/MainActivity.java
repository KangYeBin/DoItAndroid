package org.techtown.customview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //최상위 레이아웃 activity_main.xml 대신 CustomView 사용
        CustomView customView = new CustomView(this);
        setContentView(customView);
    }
}