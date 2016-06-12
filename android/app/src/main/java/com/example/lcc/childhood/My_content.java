package com.example.lcc.childhood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by lcc on 2016/6/7.
 */
public class My_content extends Activity {
    private RelativeLayout relativeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_content);
        relativeLayout = (RelativeLayout) findViewById(R.id.my_content_r2);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(My_content.this, Head_pc.class));
            }
        });
        findViewById(R.id.my_content_image6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
