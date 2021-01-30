package org.techtown.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    PersonAdapter personAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        //recyclerView를 보여주는 방식을 조정하는 manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
//        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);   //컬럼 2개짜리
        recyclerView.setLayoutManager(layoutManager);

        personAdapter = new PersonAdapter();
        personAdapter.addItem(new Person("김민수", "010-1000-1000"));
        personAdapter.addItem(new Person("김하늘", "010-1001-1001"));
        personAdapter.addItem(new Person("홍길동", "010-1002-1002"));
        recyclerView.setAdapter(personAdapter);

        personAdapter.setOnItemClickListener(new OnPersonItemClickListener() {
            @Override
            public void onItemClick(PersonAdapter.ViewHolder holder, View view, int position) {
                Person item = personAdapter.getItem(position);
                showToast("아이템 선택됨 " + item.getName());
            }
        });
    }

    public void showToast(String data) {
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
    }
}