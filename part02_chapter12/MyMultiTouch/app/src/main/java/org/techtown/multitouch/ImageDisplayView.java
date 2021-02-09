package org.techtown.multitouch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class ImageDisplayView extends View {
    Paint paint;
    Matrix mMatrix;
    Bitmap mBitmap;
    Canvas mCanvas;
    int lastX, lastY;
    float displayWidth, displayHeight = 0.0F;
    int displayCenterX, displayCenterY = 0;

    float startX, startY;   //터치하기 시작한 좌표
    float oldDistance = 0.0F;

    int oldPointerCount = 0;
    Boolean isScrolling = false;
    float  distanceThreshold = 3.0F;

    float scaleRatio;
    float totalScaleRatio;

    float bitmapCenterX;
    float bitmapCenterY;

    float sourceWidth = 0.0F;
    float sourceHeight = 0.0F;


    public static float MAX_SCALE_RATIO = 5.0F;
    public static float MIN_SCALE_RATIO = 0.1F;

    Bitmap sourceBitmap;    //실제 그려질 이미지 객체의 비트맵

    public ImageDisplayView(Context context) {
        super(context);
        init(context);
    }

    public ImageDisplayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        paint = new Paint();
        mMatrix = new Matrix();

        lastX = -1;
        lastY = -1;
    }

    //view의 크기가 바뀌었을때 호출되는 함수
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (w>0 && h>0) {
            newImage(w, h); //새로운 이미지를 메모리에 만들기
        }

        redraw();   //일종의 초기화
    }

    public void redraw() {
        if (sourceBitmap == null) {
            return;
        }

        drawBackground(mCanvas);
        float originX = (displayWidth - (float)sourceBitmap.getWidth()) / 2.0F;
        float originY = (displayHeight - (float)sourceBitmap.getHeight()) / 2.0F;

        mCanvas.translate(originX, originY);
        mCanvas.drawBitmap(sourceBitmap, mMatrix, paint);
        mCanvas.translate(-originX, -originY);

        invalidate();
    }

    public void drawBackground(Canvas canvas) {
        if (canvas != null) {
            canvas.drawColor(Color.BLACK);
        }
    }

    public void newImage(int w, int h)  {
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas();
        mCanvas.setBitmap(mBitmap);

        displayWidth = (float) w;
        displayHeight = (float) h;
        displayCenterX = w / 2;
        displayCenterY = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int pointerCount = event.getPointerCount(); //몇개의 손가락으로 터치했는지

        switch(action) {
            case MotionEvent.ACTION_DOWN:
                if (pointerCount == 1) {    //하나의 손가락으로 터치하면 이동
                    startX = event.getX();
                    startY = event.getY();
                } else if (pointerCount == 2) { //두 손가락으로 터치하면 두 손가락 사이의 거리로 확대/축소
                    oldDistance = 0.0F;
                    isScrolling = true; //계속 움직이고 있는지 확인하는 FLAG 변수
                }
            case MotionEvent.ACTION_MOVE:
                if (pointerCount == 1) {
                    if (isScrolling) {  //isScrolling은 MultiTouch일 경우에만 사용
                        return true;
                    }
                    //현재 좌표값
                    float curX = event.getX();
                    float curY = event.getY();

                    if (startX == 0.0F) {
                        startX = event.getX();
                        startY = event.getY();
                        return true;
                    }
                    //이동한 거리
                    float offsetX = startX - curX;
                    float offsetY = startY - curY;

                    if (oldPointerCount != 2) {
                        if (totalScaleRatio > 1.0F) {   //터치에 너무 민감하지 않도록
                            moveImage(offsetX, offsetY);    //이동한 거리만큼 이미지 이동
                        }
                        startX = curX;
                        startY = curY;
                    }

                } else if (pointerCount == 2) {
                    //두 손가락 각각의 좌표값
                    float x1 = event.getX(0);
                    float y1 = event.getY(0);
                    float x2 = event.getX(1);
                    float y2 = event.getY(1);

                    float dx = x1-x2;
                    float dy = y1-y2;
                    //두 손가락 사이의 거리
                    float distance = new Float(Math.sqrt(new Float(dx*dx + dy*dy)));

                    float outScaleRatio = 0.0F;
                    if (oldDistance == 0.0F) {
                        oldDistance = distance;
                        break;
                    }
                    if (distance > oldDistance) {
                        if ((distance-oldDistance) < distanceThreshold) {
                            return true;
                        }
                        outScaleRatio = scaleRatio + (oldDistance/distance*0.05F);
                    } else if(distance < oldDistance) {
                        if ((distance-oldDistance) < distanceThreshold) {
                            return true;
                        }
                        outScaleRatio = scaleRatio - (oldDistance/distance*0.05F);
                    }

                    if (outScaleRatio >= MIN_SCALE_RATIO && outScaleRatio <= MAX_SCALE_RATIO) {
                        scaleImage(outScaleRatio);  //scaleRatio가 범위 내일 경우 이미지의 scale 변경
                    }
                    oldDistance = distance;
                }
                oldPointerCount = pointerCount;
                break;
                
            case MotionEvent.ACTION_UP:
                if (pointerCount == 1) {
                    //현재 좌표값
                    float curX = event.getX();
                    float curY = event.getY();
                    //이동한 거리
                    float offsetX = startX - curX;
                    float offsetY = startY - curY;

                    if (oldPointerCount == 2) {
                    } else {
                        moveImage(-offsetX, -offsetY);
                    }
                } else if (pointerCount == 2) {
                    isScrolling = false;
                }
                return true;
        }
        return true;
    }

    public void moveImage(float offsetX, float offsetY) {
        mMatrix.postTranslate(offsetX, offsetY);

        redraw();
    }

    public void scaleImage(float inScaleRatio) {
        mMatrix.postScale(inScaleRatio, inScaleRatio, bitmapCenterX, bitmapCenterY);
        mMatrix.postRotate(0);
        totalScaleRatio = totalScaleRatio + inScaleRatio;

        redraw();
    }

    public void setImageData(Bitmap image) {
        recycle();  //메모리 문제가 발생하지 않도록 하기 위해

        sourceBitmap = image;
        sourceWidth = sourceBitmap.getWidth();
        sourceHeight = sourceBitmap.getHeight();
        bitmapCenterX = sourceWidth / 2;
        bitmapCenterY = sourceHeight / 2;

        scaleRatio = 1.0F;
        totalScaleRatio = 1.0F;
    }

    public void recycle() {
        if (sourceBitmap != null) {
            sourceBitmap.recycle(); //메모리에서 해제
        }
    }
}
