package org.techtown.doitmission08;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {
    private Button buttonCustomer, buttonSell, buttonProduct;
    public static final int REQUEST_CODE_CUSTOMER = 201;
    public static final int REQUEST_CODE_SELL = 202;
    public static final int REQUEST_CODE_PRODUCT = 203;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Activity를 추가하면 manifests에도 추가되어있는지 확인
        buttonCustomer = findViewById(R.id.buttonCustomer);
        buttonCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CustomerActivity.class);
                intent.putExtra("title", "고객 관리");
                startActivityForResult(intent, REQUEST_CODE_CUSTOMER);
            }
        });

        buttonSell = findViewById(R.id.buttonSell);
        buttonSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SellActivity.class);
                intent.putExtra("title", "매출 관리");
                startActivityForResult(intent, REQUEST_CODE_SELL);
            }
        });

        buttonProduct = findViewById(R.id.buttonProduct);
        buttonProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
                intent.putExtra("title", "상품 관리");
                startActivityForResult(intent, REQUEST_CODE_PRODUCT);
            }
        });
    }
}