package com.example.getprop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView taohltext = (TextView) findViewById(R.id.taohl_view);
        taohltext.setMovementMethod(ScrollingMovementMethod.getInstance());

        BufferedReader reader = null;
        String content = "";

        String[] info = {
                "ro.meizu.hardware.sn",     //SN
                "ro.boot.psn",              //PSN
                "ro.meizu.hardware.imei1",  //IMEI1
                "ro.meizu.hardware.imei2",  //IMEI2
                "ro.meizu.hardware.meid",   //MEID
                "ro.build.inside.id",       //Flyme dailybuild 版本号
                "ro.meizu.pcb.version",     //硬件版本：B0,B1,B2,NPI,MP
                "ro.build.type",            //user or userdebug
                "ro.secure",                //手机是否开启security 1:开启，0：未开启
                "ro.meizu.bl_unlock",       //手机锁状态 1：unlock 0:lock
                "ro.flyme.root.state",      //手机root状态 1：root 0:unroot
        };

        Map<String, String> map = new LinkedHashMap<>();

        try {
            for (int i = 0; i < info.length; i++) {
                Process process = Runtime.getRuntime().exec("getprop " + info[i]);
                reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                StringBuffer output = new StringBuffer();
                char[] buffer = new char[1024];
                int read;

                while ((read = reader.read(buffer)) > 0)
                    output.append(buffer, 0, read);
                reader.close();
                content = output.toString();

                map.put(info[i], content);
            }

            // 把 map 的 key 和 value 都输出到所创建的 textview 上。
            String buffer1 = "";
            for (String key : map.keySet())
                buffer1 = buffer1 + key + " = " + map.get(key);
            taohltext.setText(buffer1);
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
}
