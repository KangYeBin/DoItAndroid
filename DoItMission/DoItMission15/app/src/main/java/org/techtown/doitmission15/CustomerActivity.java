package org.techtown.doitmission15;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CustomerActivity extends AppCompatActivity {
    private EditText editTextName, editTextAge, editTextDate;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextDate = findViewById(R.id.editTextDate);
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String age = editTextAge.getText().toString();
                String date = editTextDate.getText().toString();

                Toast.makeText(getApplicationContext(), "이름 : " + name + ", 나이 : " + age + ", 생년월일 : " + date, Toast.LENGTH_SHORT).show();

                finish();
                overridePendingTransition(R.anim.entry_back, R.anim.exit_back);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.entry_back, R.anim.exit_back);
    }
}