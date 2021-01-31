package org.techtown.sliding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private LinearLayout page;
    private Animation translateLeftAnim, translateRightAnim;
    private boolean isPageOpen = false;
    private SlidingAnimationListener animListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        page = findViewById(R.id.page);
        translateLeftAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_left);
        translateRightAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_right);

        animListener = new SlidingAnimationListener();
        translateLeftAnim.setAnimationListener(animListener);
        translateRightAnim.setAnimationListener(animListener);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPageOpen) {
                    page.startAnimation(translateRightAnim);
                } else {
                    page.startAnimation(translateLeftAnim);
                }
            }
        });
    }

    public class SlidingAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        //animation 동작이 끝났을때ㅣ는게 그렇게
        public void onAnimationEnd(Animation animation) {
            if (isPageOpen) {
                button.setText("열기");
                page.setVisibility(View.INVISIBLE);
                isPageOpen = false;
            } else {
                button.setText("닫기");
                page.setVisibility(View.VISIBLE);
                isPageOpen = true;
            }

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}