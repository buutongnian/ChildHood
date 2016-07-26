package edu.buu.childhood.my.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import edu.buu.childhood.R;
import edu.buu.childhood.baidumap.activity.teamerName;
import edu.buu.childhood.mainui.activity.Main_interface;
import edu.buu.childhood.rank.activity.MainRank;

public class My extends Activity {

    private RelativeLayout relativeLayout;
    private RelativeLayout chengjiu;
    private RelativeLayout parentInfo;
    private RelativeLayout parentLock;
    private String parentPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my);

        File file = new File(getFilesDir(), "info.txt");
        //如果文件存在，则读取
        if (file.exists()) {
            try {
                FileInputStream fin = new FileInputStream(file);
                //把字节流转化为字符流
                BufferedReader buffer = new BufferedReader(new InputStreamReader(fin));
                //读取文件中的用户名和密码
                parentPassword = buffer.readLine();

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            parentLock = (RelativeLayout) findViewById(R.id.my_parent);
            parentLock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("pw", parentPassword);
                    intent.setClass(My.this, ParentLock.class);
                    My.this.startActivity(intent);
                }
            });
        } else {
            parentLock = (RelativeLayout) findViewById(R.id.my_parent);
            parentLock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(My.this, ParentLayout.class));
                }
            });

        }
        relativeLayout = (RelativeLayout) findViewById(R.id.my_myr2);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(My.this, My_content.class));
            }
        });
        chengjiu = (RelativeLayout) findViewById(R.id.my_myr4);
        chengjiu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(My.this, MainRank.class));
            }
        });
        parentInfo = (RelativeLayout) findViewById(R.id.my_myr5);
        parentInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(My.this, ParentInfo.class));
            }
        });

        findViewById(R.id.my_image1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(My.this, Main_interface.class));
                finish();
            }
        });

    }


}
