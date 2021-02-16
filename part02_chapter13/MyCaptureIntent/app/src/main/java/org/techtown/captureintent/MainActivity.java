package org.techtown.captureintent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {
    private Button button;
    private ImageView imageView;
    private File file = null;
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    takePicture();  //사진을 찍는 함수
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //위험권한 부여
        AutoPermissions.Companion.loadAllPermissions(this, 101);
    }

    public void takePicture() throws IOException {
        //사진의 크기가 크기 때문에 찍은 사진을 받아오지 않고 사진 파일의 위치를 받아온다
        if (file == null) {
            file = createFile();    //file이 초기화되어있지 않다면 새로 만들기
        }

        //file을 이용해서 Uri 만들기
        if(Build.VERSION.SDK_INT >= 24) {
            fileUri = FileProvider.getUriForFile(this, "org.techtown.captureintent.fileprovider", file);
        } else {
            fileUri = Uri.fromFile(file);
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);    //카메라 앱 띄우기
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);  //사진 찍은 것을 fileUri에 저장

        //API30부터는 intent.resolveActivity(getPackageManger())가 null 값이 나오므로 manifests에 camera queries 추가 필요
        if (intent.resolveActivity(getPackageManager()) != null) {  //액티비티가 존재하면 실행
            startActivityForResult(intent, 101);
        }
        // res/xml/external.xml에서 외부 폴더를 지정하고 manifests에서 provider를 추가
    }

    public File createFile() throws IOException {
        String filename = "capture";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(filename, ".jpg", storageDir);

        return imageFile; //새로 만들어진 file 객체 반환
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inSampleSize = 6;   //일정 비율로 크기 줄이기
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);  //파일의 경로에서 decodeFile
            Log.d("bitmap", String.valueOf(BitmapFactory.decodeFile(file.getAbsolutePath())));

            imageView.setImageBitmap(bitmap);
        }
    }


    //위험권한 부여
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