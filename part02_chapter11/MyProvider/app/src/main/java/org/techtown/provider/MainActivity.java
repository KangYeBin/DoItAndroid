package org.techtown.provider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button insertButton, queryButton, updateButton, deleteButton;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        insertButton = findViewById(R.id.insertButton);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertPerson();
            }
        });

        queryButton = findViewById(R.id.queryButton);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryPerson();
            }
        });

        updateButton = findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePerson();
            }
        });

        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePerson();
            }
        });

    }

    public void insertPerson() {
        println("insertPerson 호출됨");

        String uriStr = "content://org.techtown.provider/person";
        Uri uri = new Uri.Builder().build().parse(uriStr);
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        String[] columns = cursor.getColumnNames();
        for (int i=0; i<columns.length; i++) {
            println("#" + i + " : " + columns[i]);
        }

        ContentValues values = new ContentValues();
        values.put("name", "john");
        values.put("age", 20);
        values.put("mobile", "010-0000-1000");

        uri = getContentResolver().insert(uri, values);
        println("insert 결과 : " + uri.toString());
    }

    public void queryPerson() {
        println("queryPerson 호출됨");

        String urlStr = "content://org.techtown.provider/person";
        Uri uri = new Uri.Builder().build().parse(urlStr);

        String[] columns = new String[] {"name", "age", "mobile"};
        Cursor cursor = getContentResolver().query(uri, columns, null, null, "name ASC");
        println("query 결과 : " + cursor.getCount()); //레코드 개수

        int index = 0;
        while (cursor.moveToNext()) {   //next가 있는 경우 계속해서 출력
            String name = cursor.getString(cursor.getColumnIndex(columns[0]));
            int age = cursor.getInt(cursor.getColumnIndex(columns[1]));
            String mobile = cursor.getString(cursor.getColumnIndex(columns[2]));
            println("#" + index + " : " + name + ", " + age + ", " + mobile);
            index++;
        }
    }

    public void updatePerson() {
        println("updatePerson 호출됨");

        String uriStr = "content://org.techtown.provider/person";
        Uri uri = new Uri.Builder().build().parse(uriStr);

        //조건문
        String selection = "mobile = ?";
        String[] selectionArgs = new String[] {"010-0000-1000"};

        ContentValues updateValues = new ContentValues();
        updateValues.put("mobile", "010-2000-2000");

        int count = getContentResolver().update(uri, updateValues, selection, selectionArgs);
        println("update 결과 : " + count);
    }

    public void deletePerson() {
        println("deletePerson 호출됨");

        String uriStr = "content://org.techtown.provider/person";
        Uri uri = new Uri.Builder().build().parse(uriStr);

        //조건문
        String selection = "name = ?";
        String[] selectionArgs = new String[] {"john"};

        int count = getContentResolver().delete(uri, selection, selectionArgs);
        println("delete 결과 : " + count);
    }

    public void println(String data) {
        textView.append(data + "\n");
    }
}