package org.techtown.doitmission13;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText editTextName, editTextDate, editTextPhone;
    private TextView textViewNumber;
    private Button button;
    private RecyclerView recyclerView;
    private CustomerAdapter adapter;
    private int number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextDate = findViewById(R.id.editTextDate);
        editTextPhone = findViewById(R.id.editTextPhone);
        textViewNumber = findViewById(R.id.textViewNumber);
        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new CustomerAdapter();
        recyclerView.setAdapter(adapter);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String date = editTextDate.getText().toString();
                String phone = editTextPhone.getText().toString();

                Customer item = new Customer(name, date, phone);
                adapter.addItem(item);
                number = adapter.getItemCount();
                adapter.notifyDataSetChanged();
                textViewNumber.setText(number + "명");
            }
        });
    }
}