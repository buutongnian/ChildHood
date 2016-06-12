package com.example.lcc.childhood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.widget.RelativeLayout;
import android.widget.Toast;

public class My extends Activity {

    private RelativeLayout relativeLayout;
    private RelativeLayout relativeLayout1;//收藏跳转

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my);
        relativeLayout = (RelativeLayout) findViewById(R.id.my_myr2);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(My.this, My_content.class));
            }
        });
        relativeLayout1 = (RelativeLayout) findViewById(R.id.my_myr5);
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(My.this, Collect.class));
            }
        });
        findViewById(R.id.my_image1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



}
