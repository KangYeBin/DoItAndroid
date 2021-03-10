package org.techtown.doitmission21;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {
    EditText editTextTitle, editTextAuthor, editTextContents;
    Button saveButton;
    BookDatabase database;
    boolean isOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextAuthor = findViewById(R.id.editTextAuthor);
        editTextContents = findViewById(R.id.editTextContents);

        if (database != null) {
            database.close();
            database = null;
        }

        database = BookDatabase.getInstance(this);

        isOpen = database.open();

        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String author = editTextAuthor.getText().toString();
                String contents = editTextContents.getText().toString();

                if (isOpen) {
                    database.insertRecord(title, author, contents);
                    Toast.makeText(MainActivity.this, "책 정보를 추가했습니다.", Toast.LENGTH_SHORT).show();
                    editTextTitle.setText(null);
                    editTextAuthor.setText(null);
                    editTextContents.setText(null);
                }
            }
        });

        AutoPermissions.Companion.loadAllPermissions(this, 101);
    }

    @Override
    public void onDenied(int i, String[] strings) {

    }

    @Override
    public void onGranted(int i, String[] strings) {

    }
}