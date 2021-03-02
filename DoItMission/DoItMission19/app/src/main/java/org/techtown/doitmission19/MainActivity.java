package org.techtown.doitmission19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView editTextUrl;
    Button button;
    TextView textView2;
    WebView webView;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUrl = findViewById(R.id.editTextUrl);
        textView2 = findViewById(R.id.textView2);
        webView = findViewById(R.id.webView);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlStr = editTextUrl.getText().toString();
                RequestThread thread = new RequestThread(urlStr);
                thread.start();
            }
        });
    }

    class RequestThread extends Thread {
        String urlStr;

        public RequestThread(String urlStr) {
            this.urlStr = urlStr;
        }

        @Override
        public void run() {
            String requestData = request(urlStr);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    textView2.setText(requestData);
                    Log.d("Main", "requestData : " + requestData);

                    webView.loadData(requestData, "text/html", "UTF8");
                }
            });

        }

        public String request(String strUrl) {
            StringBuilder builder = new StringBuilder();

            try {
                URL url = new URL(strUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                boolean redirect = false;

                if (connection != null) {
                    connection.setConnectTimeout(10000);
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    int status = connection.getResponseCode();
                    if (status != HttpURLConnection.HTTP_OK) {
                        if (status == HttpURLConnection.HTTP_MOVED_PERM || status == HttpURLConnection.HTTP_MOVED_TEMP
                                || status == HttpURLConnection.HTTP_SEE_OTHER) {
                            redirect = true;
                        }
                    }
                    System.out.println("Response Code ... " + status);

                    if (redirect) {
                        Log.d("Main", "redirect : " + redirect);

                        String newUrl = connection.getHeaderField("Location");
                        String cookies = connection.getHeaderField("Set-Cookie");
                        connection = (HttpURLConnection) new URL(newUrl).openConnection();
                        connection.setRequestProperty("Cookie", cookies);

                        System.out.println("Redirect to URL : " + newUrl);
                    }

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())) ;
                    String line = null;
                    while(true) {
                        line = reader.readLine();
                        if (line == null) {
                            break;
                        }
                        builder.append(line + "\n");
                    }
                    reader.close();
                    connection.disconnect();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return builder.toString();
        }
    }


}