package edu.buu.childhood.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import edu.buu.childhood.R;

/**
 * Created by lcc on 2016/7/26.
 */
public class ParentLayout extends Activity {
    private ImageView changePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_layout);
        changePassword = (ImageView) findViewById(R.id.parent_layout_changePassword);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParentLayout.this, ChangePassword.class));
            }
        });
        findViewById(R.id.parent_layout_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
