package org.techtown.doitmission17;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class CustomerItemView extends LinearLayout {
    ImageView imageView;
    TextView name, phone, address;

    public CustomerItemView(Context context) {
        super(context);
        init(context);
    }

    public CustomerItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.customer_item, this, true);

        imageView = findViewById(R.id.imageView);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
    }

    public void setImageView(int imageView) {
        this.imageView.setImageResource(imageView);
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setPhone(String phone) {
        this.phone.setText(phone);
    }

    public void setAddress(String address) {
        this.address.setText(address);
    }
}
