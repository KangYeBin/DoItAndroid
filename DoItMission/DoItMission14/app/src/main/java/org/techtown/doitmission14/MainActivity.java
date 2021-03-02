package org.techtown.doitmission14;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);

        adapter = new ProductAdapter();

        adapter.addItem(new Product("롱코트", "160000원", "명절 기획상품"));
        adapter.addItem(new Product("빈탄 와이셔츠", "80000원", "특가상품"));
        adapter.addItem(new Product("조깅화", "220000원", ""));
        adapter.addItem(new Product("구찌 선글라스", "800000원", ""));

        recyclerView.setAdapter(adapter);

        adapter.setOnItemListener(new onProductItemClickListener() {
            @Override
            public void onItemClick(ProductAdapter.ViewHolder holder, View view, int position) {
                Product item = adapter.getItem(position);
                Toast.makeText(MainActivity.this, "선택된 제품 : " + item.getName(), Toast.LENGTH_LONG).show();
            }
        });



    }
}