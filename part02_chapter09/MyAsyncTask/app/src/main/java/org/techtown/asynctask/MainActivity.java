package org.techtown.asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private Button button, button2;
    int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);

        //AsyncTask를 이용해서 백그라운드로 작업 진행
        BackgroundTask task = new BackgroundTask();
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.execute();     //task 실행
            }
        });

        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.cancel(true);
            }
        });



    }

    class BackgroundTask extends AsyncTask<Integer, Integer, Integer> {
        //스레드로 실행되는 상태
        @Override
        protected Integer doInBackground(Integer... integers) {
            while (isCancelled() == false) {
                value++;
                if (value>100) {
                    break;
                }
                publishProgress(value); //onProgressUpdate 호출
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return value;
        }

        //스레드로 실행되기 전 상태
        @Override
        protected void onPreExecute() {
            value = 0;
            progressBar.setProgress(value);
        }

        //스레드로 실행된 후 상태
        @Override
        protected void onPostExecute(Integer integer) {
            progressBar.setProgress(0);
        }

        //스레드 실행중 UI를 업데이트를 하고 싶을 때
        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0].intValue());
        }
    }
}