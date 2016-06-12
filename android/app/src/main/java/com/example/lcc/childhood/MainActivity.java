package com.example.lcc.childhood;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

/**
 * Created by lcc on 2016/6/12.
 */
public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preferences = getSharedPreferences("count",MODE_WORLD_READABLE);
        int count = preferences.getInt("count", 0);

        //判断程序与第几次运行，如果是第一次运行则跳转到引导页面
        if (count == 0) {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(),Guide.class);
            startActivity(intent);
            this.finish();
        }
        //否则进入欢迎页，跳到主页面
        else{
            PackageManager pm = getPackageManager();
            try {
                PackageInfo pi = pm.getPackageInfo("com.example.lcc.childhood", 0);
                TextView versionNumber = (TextView) findViewById(R.id.versionNumber);
                versionNumber.setText("Version " + pi.versionName);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            new Handler().postDelayed(new Runnable(){

                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this,Main_interface.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                }

            }, 2500);
        }
        SharedPreferences.Editor editor = preferences.edit();
        //存入数据
        editor.putInt("count", ++count);
        //提交修改
        editor.commit();

    }
}
