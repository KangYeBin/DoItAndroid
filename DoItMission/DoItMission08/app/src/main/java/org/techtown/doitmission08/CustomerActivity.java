package org.techtown.doitmission08;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.zip.Inflater;

public class CustomerActivity extends AppCompatActivity {
    private Button backMenu, backLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        Intent recieveIntent = getIntent();
        String title = recieveIntent.getStringExtra("title");

        backMenu = findViewById(R.id.backMenu);
        backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("message", "result message is OK!");
                setResult(Activity.RESULT_OK, resultIntent);
                Toast.makeText(CustomerActivity.this, title + "에서 back", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        backLogin = findViewById(R.id.backLogin);
        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Toast.makeText(CustomerActivity.this, title + "에서 back", Toast.LENGTH_SHORT).show();
                startActivityForResult(intent, 0);
            }
        });
    }
}