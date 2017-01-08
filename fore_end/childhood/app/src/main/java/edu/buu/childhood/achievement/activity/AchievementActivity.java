package edu.buu.childhood.achievement.activity;
/**
 * Created by lcc on 2016/7/1.
 */

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.achievement.adapter.MyFragmentPagerAdapter;
import edu.buu.childhood.achievement.pojo.UserKPI;
import edu.buu.childhood.achievement.service.AchievementServiceImpl;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.util.AsyncImageLoader;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.URLUtil;
import edu.buu.childhood.view.CircleImageView;

public class AchievementActivity extends BaseAvtivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener, CallBack {

    private RadioGroup rg;
    private RadioButton track;
    private RadioButton medal;
    private RadioButton record;
    private ViewPager vpager;
    private ImageView cursor;
    private TextView praise;
    private TextView tvGameLevel;
    private TextView name;
    private TextView lvName;
    private TextView lPoint;
    private TextView rPoint;
    private ProgressBar progressBar;
    // 动画图片偏移量
    private static float offset = 0;
    // 当前页卡编号
    private static float currIndex = 0;
    // 动画图片宽度
    private static float bmpW;
    private MyFragmentPagerAdapter mAdapter;

    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    //动画偏移量
    private static float one = 0;
    private static float two = 0;
    //注册广播
    private IntentFilter filter;
    private MyReceiver mReceiver;
    //获得用户成就相关KPI信息
    private String kpiUrl;
    private CircleImageView civHeadImage;
    private AsyncImageLoader imageLoader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.achievement);
        mReceiver = new MyReceiver();
        filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        imageLoader = new AsyncImageLoader(this);
        bindViews();
        InitImageView();
        track.setChecked(true);
    }

    private void bindViews() {
        rg = (RadioGroup) findViewById(R.id.achievement_rg);
        track = (RadioButton) findViewById(R.id.achievementTrack);
        medal = (RadioButton) findViewById(R.id.achievementMedal);
        civHeadImage = (CircleImageView) findViewById(R.id.achievement_head_image);
        record = (RadioButton) findViewById(R.id.achievementRecord);
        praise = (TextView) findViewById(R.id.praise);
        LinearLayout llPersionParise = (LinearLayout) findViewById(R.id.persion_parise);
        assert llPersionParise != null;
        llPersionParise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AchievementActivity.this, PraiseDetail.class));
            }
        });
        name = (TextView) findViewById(R.id.name);
        tvGameLevel = (TextView) findViewById(R.id.gameLevel);
        lvName = (TextView) findViewById(R.id.LevelName);
        lPoint = (TextView) findViewById(R.id.currentLevelPoin);
        rPoint = (TextView) findViewById(R.id.nextLevelPoint);
        rg.setOnCheckedChangeListener(this);
        vpager = (ViewPager) this.findViewById(R.id.vPager);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        vpager.setAdapter(mAdapter);
        //vpager.setCurrentItem(0);
        vpager.addOnPageChangeListener(this);
        //vpager.setOffscreenPageLimit(0);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.achievementTrack:
                vpager.setCurrentItem(PAGE_ONE);
                break;
            case R.id.achievementMedal:
                vpager.setCurrentItem(PAGE_TWO);
                break;
            case R.id.achievementRecord:
                vpager.setCurrentItem(PAGE_THREE);
                break;
        }
    }

    /**
     * 初始化动画
     */
    private void InitImageView() {
        cursor = (ImageView) findViewById(R.id.cursor);
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.progress)
                .getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 3 - bmpW) / 2;// 计算偏移量

        one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
        two = one * 2;// 页卡1 -> 页卡3 偏移量

        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        cursor.setImageMatrix(matrix);// 设置动画初始位置
    }

    //重写ViewPager页面切换的处理方法
    @Override
    public void onPageSelected(int arg0) {
        Animation animation = null;
        switch (arg0) {
            case 0:
                if (currIndex == 1) {
                    animation = new TranslateAnimation(one, 0, 0, 0);
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(two, 0, 0, 0);
                }
                break;
            case 1:
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, one, 0, 0);
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(two, one, 0, 0);
                }
                break;
            case 2:
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, two, 0, 0);
                } else if (currIndex == 1) {
                    animation = new TranslateAnimation(one, two, 0, 0);
                }
                break;
        }
        currIndex = arg0;
        if (animation != null) {
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(300);
            cursor.startAnimation(animation);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (vpager.getCurrentItem()) {
                case PAGE_ONE:
                    track.setChecked(true);
                    break;
                case PAGE_TWO:
                    medal.setChecked(true);
                    break;
                case PAGE_THREE:
                    record.setChecked(true);
                    break;

            }
        }
    }

    @Override
    protected void onResume() {
        registerReceiver(mReceiver, filter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            if (result.getDatalist().size() > 0) {
                UserKPI userKPI = (UserKPI) result.getDatalist().get(0);
                praise.setText(String.valueOf(userKPI.getTotalLikeCount()));
                name.setText(userKPI.getUserNickname());
                lvName.setText(String.valueOf(userKPI.getLevelName()));
                tvGameLevel.setText(String.valueOf(userKPI.getUserLevel() + 1));
                lPoint.setText(String.valueOf(userKPI.getAchievementPoint()));
                rPoint.setText(String.valueOf(userKPI.getNextLevelPoint()));
                progressBar.setProgress(userKPI.getAchievementPoint() - userKPI.getCurrentLevelPoint());
                progressBar.setMax(userKPI.getNextLevelPoint() - userKPI.getCurrentLevelPoint());
                String imageUrl = userKPI.getUserHeadImage();
                if (!TextUtils.isEmpty(imageUrl)) {
                    Bitmap bitmap = imageLoader.loadImage(civHeadImage, imageUrl);
                    if (bitmap != null) {
                        civHeadImage.setImageBitmap(bitmap);
                    }
                }
            }
        }
    }

    @Override
    public CallBackPage doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(url);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            if (url.equals(kpiUrl)) {
                AchievementServiceImpl Service = new AchievementServiceImpl();
                return Service.getUserKPIHeadInf(new String(bytes));
            }
        }
        return null;
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            GlobalVeriable globalVeriable = ((GlobalVeriable) getApplication());
            if (!checkNetWorkStatus(AchievementActivity.this)) {
                setNetwork();
            } else {
                if (globalVeriable.getUserName() != null) {
                    Map args = new HashMap();
                    args.put("userName", globalVeriable.getUserName());
                    kpiUrl = URLUtil.getURL("getUserKPI", args);
                    new NetAsyncTask(AchievementActivity.this).execute(kpiUrl);
                }
            }
        }

    }

    /*网络连接检测*/
    public static boolean checkNetWorkStatus(Context context) {
        boolean result;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnected()) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    /**
     * 网络未连接时，调用设置方法
     */
    private void setNetwork() {
        Toast.makeText(this, "wifi is closed!", Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.logo);
        builder.setTitle("提示");
        builder.setMessage("网络不可用，请打开网络！");
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = null;
                /**
                 * 判断手机系统的版本！如果API大于10 就是3.0+
                 * 因为3.0以上的版本的设置和3.0以下的设置不一样，调用的方法不同
                 */
                if (android.os.Build.VERSION.SDK_INT > 10) {
                    intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
                } else {
                    intent = new Intent();
                    ComponentName component = new ComponentName(
                            "com.android.settings",
                            "com.android.settings.WirelessSettings");
                    intent.setComponent(component);
                    intent.setAction("android.intent.action.VIEW");
                }
                startActivity(intent);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create();
        builder.show();
    }
}

