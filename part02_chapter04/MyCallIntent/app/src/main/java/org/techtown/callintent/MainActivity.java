package org.techtown.callintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010-0000-1111"));
                startActivity(intent);

//                Intent intent = new Intent();
//                ComponentName name = new ComponentName("org.techtown.callintent",
//                        "org.techtown.callintent.MenuActivity");
//                intent.setComponent(name);
//                startActivityForResult(intent, 101);
            }
        });
    }
}