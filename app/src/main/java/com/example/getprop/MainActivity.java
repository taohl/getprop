package com.example.getprop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView taohltext = (TextView) findViewById(R.id.taohl_view);
        taohltext.setMovementMethod(ScrollingMovementMethod.getInstance());

        BufferedReader reader = null;
        String content = "";

        try {
            Process process = Runtime.getRuntime().exec("getprop ro.build.inside.id");
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            StringBuffer output = new StringBuffer();
            int read;
            char[] buffer = new char[1024];

            while ((read = reader.read(buffer)) > 0 ) {
                output.append(buffer, 0, read);
            }
            reader.close();
            content = output.toString();
            Log.e("taohl", content); // 打印 content

            taohltext.setText("dailybuild ver ： " + content);

        } catch (IOException e) {
                e.printStackTrace();
        }
    }
}
