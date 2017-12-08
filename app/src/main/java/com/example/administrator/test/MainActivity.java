package com.example.administrator.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 网络源码查看器
 */
public class MainActivity extends AppCompatActivity {

    private EditText et_url;
    private TextView tv_source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_url = (EditText) findViewById(R.id.et_url);
        tv_source = (TextView) findViewById(R.id.tv_source);

    }

    public void lookSource(View view) {
        final String path = et_url.getText().toString().trim();
        if (path.isEmpty()) {
            Toast.makeText(this, "please input the url", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(3 * 1000);
                    int responseCode = conn.getResponseCode();
                    if (responseCode == 200) {
                        InputStream inputStream = conn.getInputStream();
                        final String result = StreamUtil.streamToString(inputStream);
                        // 主线程更新UI
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_source.setText(result);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
