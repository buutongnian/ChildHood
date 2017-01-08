package edu.buu.childhood.my.activity;

import android.os.Bundle;
import android.view.WindowManager;

import edu.buu.childhood.R;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.util.CloseActivity;

/**
 * Created by lcc on 2016/9/25.
 */
public class AboutUs extends BaseAvtivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.about_us);
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
