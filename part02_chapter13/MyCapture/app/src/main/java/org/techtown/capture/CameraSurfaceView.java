package org.techtown.capture;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.io.IOException;

public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder holder;
    Camera camera = null;

    public CameraSurfaceView(Context context) {
        super(context);
        init(context);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) { //초기화 부분
        //SurfaceHolder가 SurfaceView를 컨트롤
        holder = getHolder();
        holder.addCallback(this);   //현재 액티비티에서 구현한 Callback에 대해


    }

    @Override   //메모리에서 만들어지는 시점
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        camera = Camera.open(); //카메라 open
        try {
            camera.setPreviewDisplay(holder);   //하드웨어 카메라로 들어온 영상이 holder로 전달
                                                //holder에서 SurfaceHolder에 표시
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override   //화면의 크기가 변경되는 시점
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        camera.startPreview();  //미리보기
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    public boolean capture(Camera.PictureCallback callback) {
        if (camera != null) {
            camera.takePicture(null, null, callback);
            return true;
        } else {
            return false;
        }
    }
}
