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

        TextView taohltext = findViewById(R.id.taohl_view);
        taohltext.setMovementMethod(ScrollingMovementMethod.getInstance());

        BufferedReader reader;
        String content;

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
            for (String anInfo : info) {
                Process process = Runtime.getRuntime().exec("getprop " + anInfo);
                reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                int read;
                char[] buffer = new char[1024];
                StringBuilder buf = new StringBuilder();

                while ((read = reader.read(buffer)) > 0)
                    buf.append(buffer, 0, read);
                reader.close();
                content = buf.toString();
                content = content.replaceAll("\r|\n", "");

                map.put(anInfo, content);
            }

            // 把 map 的 key 和 value 都输出到所创建的 textview 上。
            StringBuilder buf1 = new StringBuilder();
            for (String key : map.keySet())
                buf1 = buf1.append(key).append(" = ").append(map.get(key)).append("\n");
            taohltext.setText(buf1.toString());

            for (String key : map.keySet()) {
                String buf_value = map.get(key);

                // get sn
                if (key.equals(info[0])) {
                    TextView textView = findViewById(R.id.textview_sn);
                    textView.setText(map.get(key));
                }
                // get psn
                if (key.equals(info[1])) {
                    TextView textView = findViewById(R.id.textview_psn);
                    textView.setText(map.get(key));
                }
                // get imei1
                if (key.equals(info[2])) {
                    TextView textView = findViewById(R.id.textview_imei1);
                    textView.setText(map.get(key));
                }
                // get imei2
                if (key.equals(info[3])) {
                    TextView textView = findViewById(R.id.textview_imei2);
                    textView.setText(map.get(key));
                }
                // get meid
                if (key.equals(info[4])) {
                    TextView textView = findViewById(R.id.textview_meid);
                    textView.setText(map.get(key));
                }
                // get dailybuild version
                if (key.equals(info[5])) {
                    TextView textView = findViewById(R.id.textview_flymever);
                    textView.setText(map.get(key));
                }
                // get pcb version
                if (key.equals(info[6])) {
                    TextView textView = findViewById(R.id.textview_hwver);
                    switch (buf_value) {
                        case "0x2":
                            textView.setText(getString(R.string.hwver_b0));
                            break;
                        case "0x3":
                            textView.setText(getString(R.string.hwver_b1));
                            break;
                        case "0x4":
                            textView.setText(getString(R.string.hwver_b2));
                            break;
                        case "0x5":
                            textView.setText(getString(R.string.hwver_b2_1));
                            break;
                        case "0x6":
                            textView.setText(getString(R.string.hwver_npi));
                            break;
                        case "0x7":
                            textView.setText(getString(R.string.hwver_mp));
                            break;
                        default:
                            textView.setText(getString(R.string.na));
                    }
                }
                // get user or userdebug or eng
                if (key.equals(info[7])) {
                    TextView textView = findViewById(R.id.textview_buildtype);
                    textView.setText(map.get(key));
                }
                // get secure  1：开启 0：未开启
                if (key.equals(info[8])) {
                    TextView textView = findViewById(R.id.textview_hwsec);
                    switch (buf_value) {
                        case "0":
                            textView.setText(getString(R.string.unsec));
                            break;
                        case "1":
                            textView.setText(getString(R.string.sec));
                            break;
                        default:
                            textView.setText(getString(R.string.na));
                    }
                }
                // get lock status 1: unlock 0:lock
                if (key.equals(info[9])) {
                    TextView textView = findViewById(R.id.textview_bllock);
                    switch (buf_value) {
                        case "0":
                            textView.setText(getString(R.string.lock));
                            break;
                        case "1":
                            textView.setText(getString(R.string.unlock));
                            break;
                        default:
                            textView.setText(getString(R.string.na));
                    }
                }
                // get root status 1: root 0: unroot
                if (key.equals(info[10])) {
                    TextView textView = findViewById(R.id.textview_root);
                    switch (buf_value) {
                        case "0":
                            textView.setText(getString(R.string.unroot));
                            break;
                        case "1":
                            textView.setText(getString(R.string.root));
                            break;
                        default:
                            textView.setText(getString(R.string.na));
                    }
                }
            }
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
}
