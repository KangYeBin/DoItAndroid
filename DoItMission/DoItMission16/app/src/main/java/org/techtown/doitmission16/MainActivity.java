package org.techtown.doitmission16;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button searchButton, openButton;
    private EditText inputUrl;
    private WebView webView;
    private LinearLayout urlLayout;
    private Animation openAnimation, closeAnimation;
    private Boolean isOpened = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlLayout = findViewById(R.id.urlLayout);
        openAnimation = AnimationUtils.loadAnimation(this, R.anim.open);
        closeAnimation = AnimationUtils.loadAnimation(this, R.anim.close);

        inputUrl = findViewById(R.id.inputUrl);
        webView = findViewById(R.id.webView);
        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = inputUrl.getText().toString();

                if (url == null) {
                    Toast.makeText(getApplicationContext(), "사이트 주소를 먼저 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                webView.loadUrl(url);
                urlLayout.startAnimation(closeAnimation);
                urlLayout.setVisibility(View.GONE);
                openButton.setVisibility(View.VISIBLE);
                isOpened = false;
            }
        });

        openButton = findViewById(R.id.openButton);
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpened) {
                    urlLayout.startAnimation(closeAnimation);
                    urlLayout.setVisibility(View.GONE);
                    isOpened = false;
                }
                else if (!isOpened) {
                    urlLayout.startAnimation(openAnimation);
                    urlLayout.setVisibility(View.VISIBLE);
                    isOpened = true;
                }
            }
        });
    }


}