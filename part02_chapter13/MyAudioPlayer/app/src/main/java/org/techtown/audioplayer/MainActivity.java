package org.techtown.audioplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button button, button2, button3, button4;
    String url = "http://sites.google.com/site/ubiaccessmobile/sample_audio.amr";

    int position;   //현재 타임
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //재생버튼 클릭
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudio();
            }
        });

        //중지 버튼 클릭
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAudio();
            }
        });

        //일시정지 버튼 클릭
        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseAudio();
            }
        });

        //재시작 버튼 클릭
        button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resumeAudio();
            }
        });
    }

    public void playAudio() {
        showToast("음악 파일 재생 호출됨");

        killPlayer();

        //서버에 있는 음악 파일을 가져와서 재생 -> 인터넷 권한 필요
        player = new MediaPlayer();
        try {
            player.setDataSource(url);
            player.prepare();   //플레이 준비
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
        showToast("음악 파일 중지 호출됨");

        if (player != null) {
            player.stop();
        }
    }

    public void pauseAudio() {
        showToast("음악 파일 일시중지 호출됨");

        if (player != null) {
            position = player.getCurrentPosition();
            player.pause(); //중지된 위치를 알아야 재시작이 가능
        }
    }

    public void resumeAudio() {
        showToast("음악 파일 재시작 호출됨");

        if (player != null && !player.isPlaying()) {
            player.start();
            player.seekTo(position);    //position 위치로 이동해서 시작
        }
    }

    public void showToast(String data) {
        Toast.makeText(this, data,  Toast.LENGTH_LONG).show();
    }
}