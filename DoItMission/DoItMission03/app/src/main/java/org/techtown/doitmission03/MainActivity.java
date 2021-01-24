package org.techtown.doitmission03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private Button button1, button2;
    private ImageView imageView1, imageView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView1 = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);

        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageUp();
            }
        });

        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageDown();
            }
        });
    }

    public void imageDown() {
        imageView1.setImageResource(0);
        imageView2.setImageResource(R.drawable.ic_launcher_foreground);
        imageView1.invalidate();
        imageView2.invalidate();
    }
    public void imageUp() {
        imageView1.setImageResource(R.drawable.ic_launcher_foreground);
        imageView2.setImageResource(0);
        imageView1.invalidate();
        imageView2.invalidate();
    }
}