package org.techtown.doitmission18;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {
    TextView textView2;
    FrameLayout frameLayout;
    PictureItemView itemView;
    Animation inAnimation, outAnimation;
    Handler handler = new Handler();
    ArrayList<ImageInfo> images;
    int allPictureCount = 0;
    int selectedCount = 0;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = findViewById(R.id.frameLayout);
        textView2 = findViewById(R.id.textView2);
        itemView = new PictureItemView(this);
        itemView.setDate("날짜");
        frameLayout.addView(itemView);

        images = getAllPictures();

        inAnimation = AnimationUtils.loadAnimation(this, R.anim.in);
        outAnimation = AnimationUtils.loadAnimation(this, R.anim.out);

        AnimationThread thread = new AnimationThread();
        thread.start();

        AutoPermissions.Companion.loadAllPermissions(this, 101);
    }

    @Override
    public void onDenied(int i, String[] strings) {

    }

    @Override
    public void onGranted(int i, String[] strings) {

    }

    private class AnimationThread extends Thread {
        @Override
        public void run() {
            while(true){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (selectedCount < getAllPictures().size()) {
                            ImageInfo image = getAllPictures().get(selectedCount);
                            itemView.setDate(image.getAddedDate());
                            itemView.setImageView(image.getPath());
                            itemView.startAnimation(inAnimation);
                            selectedCount ++;
                            textView2.setText(selectedCount + " / " + allPictureCount + " 개");
                        }
                        else {
                            selectedCount = 0;
                        }
                    }
                });

                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private ArrayList<ImageInfo> getAllPictures() {
        allPictureCount = 0;

        ArrayList<ImageInfo> result = new ArrayList<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME, MediaStore.MediaColumns.DATE_ADDED };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, MediaStore.MediaColumns.DATE_ADDED + " desc");
        int columnDataIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        int columnNameIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME);
        int columnDateIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED);

        while (cursor.moveToNext()) {
            String path = cursor.getString(columnDataIndex);
            String displayName = cursor.getString(columnNameIndex);
            String outDate = cursor.getString(columnDateIndex);
            String addedDate = dateFormat.format(new Date(new Long(outDate).longValue() * 1000L));

            if (!TextUtils.isEmpty(path)) {
                ImageInfo info = new ImageInfo(path, addedDate);
                result.add(info);
            }
            allPictureCount++;
        }
        textView2.setText(selectedCount + " / " + allPictureCount + " 개");

        return result;
    }
}