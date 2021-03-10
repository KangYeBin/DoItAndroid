package org.techtown.doitmission25;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {
    TextView pictureNumber;
    RecyclerView recyclerView;
    PictureAdapter adapter;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    int pictureCount = 0;
    ArrayList<PictureInfo> pictureInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pictureNumber = findViewById(R.id.pictureNumber);
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new PictureAdapter();
        recyclerView.setAdapter(adapter);

        pictureInfos = queryAllPictures();
        adapter.setItems(pictureInfos);
        adapter.notifyDataSetChanged();

        AutoPermissions.Companion.loadAllPermissions(this, 101);
    }

    private ArrayList<PictureInfo> queryAllPictures() {
        ArrayList<PictureInfo> pictures = new ArrayList<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projections = {MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME, MediaStore.MediaColumns.DATE_ADDED};

        Cursor cursor = getContentResolver().query(uri, projections, null, null, MediaStore.MediaColumns.DATE_ADDED + " desc");
        int columnImageIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);
        int columnNameIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME);
        int columnDateIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DATE_ADDED);

        pictureCount = 0;
        while(cursor.moveToNext()) {
            String image = cursor.getString(columnImageIndex);
            String name = cursor.getString(columnNameIndex);
            String date = cursor.getString(columnDateIndex);
            String addedDate = dateFormat.format(new Date(new Long(date).longValue() * 1000L));

            pictures.add(new PictureInfo(image, name, addedDate));
            pictureCount++;
        }
        pictureNumber.setText(pictureCount + "개");

        return pictures;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
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