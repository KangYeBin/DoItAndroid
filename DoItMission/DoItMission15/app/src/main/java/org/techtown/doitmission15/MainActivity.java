package org.techtown.doitmission15;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
                goToCustomerActivity();
            }
        });
    }

    public void goToCustomerActivity() {
        Intent intent = new Intent(getApplicationContext(), CustomerActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.entry, R.anim.exit);
        //바뀌는 activity의 animation, 원래 activity의 animation
    }
}