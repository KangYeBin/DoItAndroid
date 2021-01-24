package org.techtown.doitmission07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {
    private Button button2, button3, button4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = button2.getText().toString();
                Toast.makeText(MenuActivity.this, name, Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = button3.getText().toString();
                Toast.makeText(MenuActivity.this, name, Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = button4.getText().toString();
                Toast.makeText(MenuActivity.this, name, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}