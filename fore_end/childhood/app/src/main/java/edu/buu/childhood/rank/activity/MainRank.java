package edu.buu.childhood.rank.activity;

/**
 * Created by lcc on 2016/7/1.
 */

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import edu.buu.childhood.R;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.rank.adapter.MyRankAdapter;
import edu.buu.childhood.util.CloseActivity;

public class MainRank extends BaseAvtivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener {
    private RadioGroup rg;
    private RadioButton gameRank;
    private RadioButton personRank;
    private ViewPager vpager;
    private MyRankAdapter mAdapter;
    Integer stringValue;
    private SoundPool sp;//声明一个SoundPool
    private int music;//定义一个整型用load（）；来设置suondID

    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.main_rank);
        Intent intent = getIntent();
        stringValue = intent.getIntExtra("extra", 0);
        mAdapter = new MyRankAdapter(getSupportFragmentManager());
        bindViews();
//        sp= new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
//        music = sp.load(this, R.raw.music, 1);
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
//                sp.play(music,1,1,0,1,1);
                vpager.setCurrentItem(PAGE_ONE);
                break;
            case R.id.function_menu_rg_person:
//                sp.play(music,1,1,0,1,1);
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
