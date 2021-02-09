package org.techtown.customviewimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomViewImage extends View {

    Paint paint;
    Bitmap cacheBitmap;
    Canvas cacheCanvas;

    public CustomViewImage(Context context) {
        super(context);
        init(context);
    }

    public CustomViewImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    //view의 크기가 변경될때 호출
    //view가 차지하는 영역이 결정되는 시점 -> 여기서 미리 그리기
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        createCacheBitmap(w, h);
        testDrawing();
    }

    public void createCacheBitmap(int w, int h) {
        //메모리에 미리 비트맵 객체를 만들어두어서 그리는 시간을 줄인다
        cacheBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas();
        cacheCanvas.setBitmap(cacheBitmap);
    }

    public void testDrawing() {
        Bitmap srcImg = BitmapFactory.decodeResource(getResources(), R.drawable.face);
        cacheCanvas.drawBitmap(srcImg, 100, 100, paint);

        Matrix matrix = new Matrix();
        matrix.setScale(1, -1); //이미지를 뒤집어서 나오게
        Bitmap inverseBitmap = Bitmap.createBitmap(srcImg, 0, 0, srcImg.getWidth(), srcImg.getHeight(), matrix, false);
        cacheCanvas.drawBitmap(inverseBitmap, 300, 300, paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        //지연시간이 오래걸리는 작업
//        Bitmap srcImg = BitmapFactory.decodeResource(getResources(), R.drawable.face);
//        canvas.drawBitmap(srcImg, 100, 100, paint);

        if (cacheBitmap != null) {  //메모리에 있는 cacheBitmap 그리기
            canvas.drawBitmap(cacheBitmap, 0, 0, paint);
        }
    }

    public void init(Context context) {
        paint = new Paint();
    }
}
