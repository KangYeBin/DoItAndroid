package org.techtown.doitmission17;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    CustomerItemView itemView1, itemView2;
    Animation inAnimation, outAnimation;
    Handler handler = new Handler();
    int selectedView = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = findViewById(R.id.frameLayout);
        itemView1 = new CustomerItemView(this);
        itemView1.setName("홍길동");
        itemView1.setPhone("010-1000-1000");
        itemView1.setAddress("서울");
        frameLayout.addView(itemView1);

        itemView2 = new CustomerItemView(this);
        itemView2.setName("가나다");
        itemView2.setPhone("010-2000-2000");
        itemView2.setAddress("부산");
        frameLayout.addView(itemView2);

        inAnimation = AnimationUtils.loadAnimation(this, R.anim.in);
        outAnimation = AnimationUtils.loadAnimation(this, R.anim.out);

        AnimationThread thread = new AnimationThread();
        thread.start();

    }

    private class AnimationThread extends Thread {
        @Override
        public void run() {
            while(true){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (selectedView == 1) {
                            itemView1.startAnimation(inAnimation);
                            itemView2.startAnimation(outAnimation);
                            itemView1.setVisibility(View.VISIBLE);
                            itemView2.setVisibility(View.INVISIBLE);
                            selectedView = 2;
                        }
                        else if (selectedView == 2) {
                            itemView1.startAnimation(outAnimation);
                            itemView2.startAnimation(inAnimation);
                            itemView1.setVisibility(View.INVISIBLE);
                            itemView2.setVisibility(View.VISIBLE);
                            selectedView = 1;
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
}