package org.techtown.customviewdrawable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public class CustomViewDrawable extends View {
    ShapeDrawable upperDrawable;
    ShapeDrawable lowerDrawable;

    public CustomViewDrawable(Context context) {
        super(context);
        init(context);
    }

    public CustomViewDrawable(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        //화면의 가로, 세로 크기를 참조할때 WindowMagager 참조
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        int displayWidth = display.getWidth();
        int displayHeight = display.getHeight();

        //
        Resources resources = getResources();
        int blackColor = resources.getColor(R.color.color01);    //res에 정의한 color 참조
        int grayColor = resources.getColor(R.color.color02);
        int darkGrayColor = resources.getColor(R.color.color03);

        //Drawable 객체를 코드에서 정의하기
        upperDrawable = new ShapeDrawable();
        RectShape rectShape = new RectShape();
        rectShape.resize(displayWidth, displayHeight*2/3);
        upperDrawable.setShape(rectShape);
        upperDrawable.setBounds(0, 0, displayWidth, displayHeight*2/3);

        //위의 2/3은 그라데이션
        LinearGradient gradient = new LinearGradient(0, 0, 0, displayHeight*2/3, grayColor, blackColor, Shader.TileMode.CLAMP); //그라데이션
        Paint paint = upperDrawable.getPaint();
        paint.setShader(gradient);

        lowerDrawable = new ShapeDrawable();
        RectShape rectShape2 = new RectShape();
        rectShape2.resize(displayWidth, displayHeight*1/3);
        lowerDrawable.setShape(rectShape2);
        lowerDrawable.setBounds(0, displayHeight*2/3, displayWidth, displayHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        upperDrawable.draw(canvas);
        lowerDrawable.draw(canvas);
    }
}
