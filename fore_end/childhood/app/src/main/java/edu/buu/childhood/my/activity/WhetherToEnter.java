package edu.buu.childhood.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import edu.buu.childhood.R;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.util.CloseActivity;


/**
 * Created by lcc on 2016/9/9.
 */
public class WhetherToEnter extends BaseAvtivity {
    private Button enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.whether_to_enter);
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        enter = (Button) findViewById(R.id.enterButton);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WhetherToEnter.this, GetParRegInfo.class));
                finish();
            }
        });

    }
}
