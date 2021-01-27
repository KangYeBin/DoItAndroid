package org.techtown.receiver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AutoPermissions.Companion.loadAllPermissions(this, 101);
    }

    //위험권한 요청후 사용자가 허용했는지 거부했는지 정보를 얻을 수 있는 함수
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }

    @Override
    public void onDenied(int i, String[] strings) {
        showToast("권한 거부된 것 " + strings.length);
    }

    @Override
    public void onGranted(int i, String[] strings) {
        showToast("권한 허가된 것 " + strings.length);
    }

    public void showToast(String data) {
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
    }
}