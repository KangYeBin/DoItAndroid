package org.techtown.contacts;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {
    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectContacts();   //연락처 가져오기
            }
        });

        AutoPermissions.Companion.loadAllPermissions(this, 101);
    }

    public void selectContacts() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, 101);    //연락처를 선택하는 화면으로 전환
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                Uri contactsUri = data.getData();
                String id = contactsUri.getLastPathSegment();

                //아이디에 해당하는 연락처 정보 가져오기
                getContacts(id);
            }
        }
    }

    public void getContacts(String id) {
        Cursor cursor = null;
        String name = "";

        cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI, null,
                ContactsContract.Data.CONTACT_ID + "=?", new String[] {id}, null);

        if (cursor.moveToFirst()) { //커서를 맨 처음으로 옮기고
            name = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
            println("Name : " + name);

            String columns[] = cursor.getColumnNames();
            for (String column : columns) {
                int index = cursor.getColumnIndex(column);
                String columnOutput = "#" + index + " : [" + column + "] " + cursor.getString(index);
                println(columnOutput);
            }
            cursor.close();
        }
    }

    public void println(String data) {
        textView.append(data);
    }

    @Override
    public void onDenied(int i, String[] strings) {
        Toast.makeText(this, "permissions denied : " + strings.length, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGranted(int i, String[] strings) {
        Toast.makeText(this, "permissions granted : " + strings.length, Toast.LENGTH_SHORT).show();
    }
}