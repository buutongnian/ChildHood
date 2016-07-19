package edu.buu.childhood.my.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.widget.RelativeLayout;

import edu.buu.childhood.R;
import edu.buu.childhood.baidumap.activity.teamerName;

public class My extends Activity {

    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my);

    relativeLayout = (RelativeLayout)findViewById(R.id.my_myr2);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(My.this, My_content.class));
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
