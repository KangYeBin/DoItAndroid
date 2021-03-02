package org.techtown.doitmission18;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class PictureItemView extends LinearLayout {
    ImageView imageView;
    TextView date;
    BitmapFactory.Options options = new BitmapFactory.Options();

    public PictureItemView(Context context) {
        super(context);
        init(context);
    }

    public PictureItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.picture_item, this, true);

        imageView = findViewById(R.id.imageView);
        date = findViewById(R.id.date);
    }

    public void setImageView(int imageView) {
        this.imageView.setImageResource(imageView);
    }
    public void setImageView(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        imageView.setImageBitmap(bitmap);
    }
    public void setDate(String date) {
        this.date.setText(date);
    }

}
