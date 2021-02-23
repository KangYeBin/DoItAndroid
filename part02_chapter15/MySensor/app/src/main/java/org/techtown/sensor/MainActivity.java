package org.techtown.sensor;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button button, button2;
    private TextView output1;
    private SensorManager manager;
    private List<Sensor> sensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output1 = findViewById(R.id.output1);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSensorList();    //센서 리스트 가져오기
            }
        });

        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFirstSensor();   //첫번째 센서 정보 가져오기
            }
        });
    }

    public void getSensorList() {
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensors = manager.getSensorList(Sensor.TYPE_ALL); //모든 센서 정보 가져오기

        int index = 0;
        for (Sensor sensor : sensors) {
            println("#" + index + " : " + sensor.getName());
            index++;
        }
    }

    public void getFirstSensor() {
        manager.registerListener(new SensorEventListener() {
            @Override   //센서의 값이 바뀔때마다 호출됨
            public void onSensorChanged(SensorEvent event) {
                println("Sensor timestamp : " + event.timestamp);   //어느 시간대의 센서 정보인지

                for (int i=0; i<event.values.length; i++) {
                    println("    value #" + i + " : " + event.values[i]);
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        },
                sensors.get(0),
                SensorManager.SENSOR_DELAY_UI);  //센서 정보를 어떻게 가져올 것인지
    }

    public void println(String message) {
        output1.append(message + "\n");
    }
}