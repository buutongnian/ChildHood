package edu.buu.childhood.rank.activity;

/**
 * Created by lcc on 2016/7/1.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import edu.buu.childhood.R;
import edu.buu.childhood.rank.adapter.MyRankAdapter;

public class MainRank extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener {

    private RadioGroup rg;
    private RadioButton gameRank;
    private RadioButton personRank;
    private ViewPager vpager;
    private MyRankAdapter mAdapter;
    Integer stringValue;

    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_rank);
        Intent intent=getIntent();
       stringValue=intent.getIntExtra("extra",0);
        mAdapter = new MyRankAdapter(getSupportFragmentManager());
        bindViews();
        gameRank.setChecked(true);
    }
    private void bindViews() {
        rg = (RadioGroup) findViewById(R.id.function_menu_rg);
        personRank = (RadioButton) findViewById(R.id.function_menu_rg_person);
        gameRank = (RadioButton) findViewById(R.id.function_menu_rg_game);
        rg.setOnCheckedChangeListener(this);
        vpager = (ViewPager) this.findViewById(R.id.function_menu_vpager);
        vpager.setAdapter(mAdapter);
        //vpager.setCurrentItem(0);
        vpager.addOnPageChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.function_menu_rg_game:
                vpager.setCurrentItem(PAGE_ONE);
                break;
            case R.id.function_menu_rg_person:
                vpager.setCurrentItem(PAGE_TWO);
                break;
        }
    }


    //重写ViewPager页面切换的处理方法
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (vpager.getCurrentItem()) {
                case PAGE_ONE:
                    gameRank.setChecked(true);
                    break;
                case PAGE_TWO:
                    personRank.setChecked(true);
                    break;

            }
        }
    }
}
