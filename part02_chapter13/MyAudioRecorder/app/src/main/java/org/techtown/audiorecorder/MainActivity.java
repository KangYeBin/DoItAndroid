package org.techtown.audiorecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {
    private Button button, button2, button3, button4;
    private MediaRecorder recorder = null;
    private MediaPlayer player = null;
    private String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //녹음시작 버튼 클릭
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecording();
            }
        });

        //녹음중지 버튼 클릭
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecording();
            }
        });

        //재생시작 버튼 클릭
        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudio();
            }
        });

        //재생중지 버튼 클릭
        button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAudio();
            }
        });

        String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();    //sd카드의 절대경로
        filename = sdcard + File.separator + "recorded.mp4";

        AutoPermissions.Companion.loadAllPermissions(this, 101);
    }

    public void startRecording() {
        if (recorder == null) {
            recorder = new MediaRecorder();
        }
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC); //마이크를 통해 음성정보를 읽어들인다
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);   //단말의 기본 인코더 사용
        recorder.setOutputFile(filename);   //이 경로에 녹음하기

        try {
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopRecording() {
        if (recorder == null) {
            return;
        }
        
        recorder.stop();
        recorder.release();
        recorder = null;    //나중에 새로 만들 경우를 대비해서
    }

    public void playAudio() {
        killPlayer();   //플레이 중인게 있다면 종료시키기

        try {
            player = new MediaPlayer();
            player.setDataSource("file://" + filename);
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void killPlayer() {
        if (player != null) {
            player.release();
        }
    }

    public void stopAudio() {
        if (player != null) {
            player.stop();
        }
    }


    @Override
    public void onDenied(int i, String[] strings) {
        Toast.makeText(this, "permission denied : " + strings.length, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGranted(int i, String[] strings) {
        Toast.makeText(this, "permission granted : " + strings.length, Toast.LENGTH_SHORT).show();
    }
}