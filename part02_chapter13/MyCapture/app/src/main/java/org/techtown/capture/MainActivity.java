package org.techtown.capture;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {
    private CameraSurfaceView cameraSurfaceView;
    private Button button;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout container = findViewById(R.id.container);
        cameraSurfaceView = new CameraSurfaceView(this);
        container.addView(cameraSurfaceView);   //container에 cameraSurfaceView 추가

        imageView = findViewById(R.id.imageView);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capture();
            }
        });

        AutoPermissions.Companion.loadAllPermissions(this, 101);
    }

    public void capture() { //사진찍는 기능이 동작하도록
        cameraSurfaceView.capture(new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                //사진이 찍혔을때 jpeg를 byte 형태로 전달
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                imageView.setImageBitmap(bitmap);
            }
        });

    }

    @Override
    public void onDenied(int i, String[] strings) {
        Toast.makeText(this, "permission denied : " + strings.length, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGranted(int i, String[] strings) {
        Toast.makeText(this, "permission granted : " + strings.length, Toast.LENGTH_SHORT).show();
    }
}