package org.techtown.doitmission20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {
    Button button;
    RecyclerView recyclerView;
    RSSAdapter adapter;
    ArrayList<RSSItem> itemArrayList;
    Handler handler = new Handler();
    ProgressDialog progressDialog;
    private static String rssUrl = "http://api.sbs.co.kr/xml/news/rss.jsp?pmDiv=entertainment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);
        adapter = new RSSAdapter();
        recyclerView.setAdapter(adapter);

        itemArrayList = new ArrayList<>();

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRSS(rssUrl);
            }
        });
    }

    public void showRSS(String rssUrl) {
        progressDialog = ProgressDialog.show(this, "RSS Refresh", "RSS 정보 업데이트 중...", true, true);
        showThread thread = new showThread(rssUrl);
        thread.start();
    }

    private class showThread extends Thread {
        String rssUrl;
        public showThread(String rssUrl) {
            this.rssUrl = rssUrl;
        }

        @Override
        public void run() {
            try {
                DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = builderFactory.newDocumentBuilder();

                URL urlForHttp = new URL(rssUrl);
                InputStream instream = getInputStreamUsingHTTP(urlForHttp);
                Document document = builder.parse(instream);
                int countItem = processDocument(document);

                handler.post(updateRSSRunnable);

            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public InputStream getInputStreamUsingHTTP(URL url) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);

        int resCode = conn.getResponseCode();
        InputStream instream = conn.getInputStream();
        return instream;
    }

    private int processDocument(Document doc) {
        itemArrayList.clear();

        Element docEle = doc.getDocumentElement();
        NodeList nodelist = docEle.getElementsByTagName("item");
        int count = 0;
        if ((nodelist != null) && (nodelist.getLength() > 0)) {
            for (int i = 0; i < nodelist.getLength(); i++) {
                RSSItem newsItem = dissectNode(nodelist, i);
                if (newsItem != null) {
                    itemArrayList.add(newsItem);
                    count++;
                }
            }
        }

        return count;
    }

    private RSSItem dissectNode(NodeList nodelist, int index) {
        RSSItem newsItem = null;

        try {
            Element entry = (Element) nodelist.item(index);

            Element title = (Element) entry.getElementsByTagName("title").item(0);
            Element description = (Element) entry.getElementsByTagName("description").item(0);

            String titleValue = null;
            if (title != null) {
                Node firstChild = title.getFirstChild();
                if (firstChild != null) {
                    titleValue = firstChild.getNodeValue();
                }
            }

            String descriptionValue = null;
            if (description != null) {
                Node firstChild = description.getFirstChild();
                if (firstChild != null) {
                    descriptionValue = firstChild.getNodeValue();
                }
            }
            newsItem = new RSSItem(titleValue, descriptionValue);

        } catch (DOMException e) {
            e.printStackTrace();
        }
        return newsItem;
    }


    Runnable updateRSSRunnable = new Runnable() {
        public void run() {
            try {
                 for (int i = 0; i < itemArrayList.size(); i++) {
                    RSSItem newsItem = itemArrayList.get(i);
                    adapter.addItem(newsItem);
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    };
}