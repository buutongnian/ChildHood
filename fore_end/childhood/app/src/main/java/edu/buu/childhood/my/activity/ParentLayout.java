package edu.buu.childhood.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.parent.activity.SubmitGameTime;
import edu.buu.childhood.parent.ad.AdContent;
import edu.buu.childhood.parent.ad.CycleViewPager;
import edu.buu.childhood.parent.pojo.ADInfo;
import edu.buu.childhood.util.AsyncImageLoader;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NewCallBack;
import edu.buu.childhood.util.NewNetAsyncTask;
import edu.buu.childhood.util.URLUtil;

/**
 * Created by lcc on 2016/7/26.
 */
public class ParentLayout extends BaseAvtivity implements NewCallBack {
    private Button changePassword;
    private Button submitGameTime;
    private Button findPartner;
    private Button Shield;
    private Boolean isShield;
    private SharedPreferences sp;

    private CycleViewPager cycleViewPager;
    private List<ImageView> imgList = new ArrayList<ImageView>();
    private List<ADInfo> adList;
    private AsyncImageLoader asyncImageLoader;

    private Gson json = new Gson();

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {
        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                if (info.getAdUrl() != null) {
                    Intent intent = new Intent(ParentLayout.this, AdContent.class);
                    intent.putExtra("adName", info.getAdName());
                    intent.putExtra("adUrl", info.getAdUrl());
                    startActivity(intent);

                    Map<String, String> args = new HashMap<String, String>();
                    args.put("userName", ((GlobalVeriable) getApplication()).getUserName());
                    args.put("adId", String.valueOf(info.getAdId()));
                    String adClickUrl = URLUtil.getURL("clickAD", args);
                    new NewNetAsyncTask(ParentLayout.this).execute(adClickUrl);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.parent_layout);
        //获得是否可以一键呼唤的数据
        isShield = ((GlobalVeriable) getApplication()).getShield();
        changePassword = (Button) findViewById(R.id.parent_layout_changePassword);

        asyncImageLoader = new AsyncImageLoader(this);

        cycleViewPager = (CycleViewPager) getFragmentManager()
                .findFragmentById(R.id.fragment_cycle_viewpager_content);

        Map<String, String> args = new HashMap<String, String>();
        args.put("userName", ((GlobalVeriable) getApplication()).getUserName());
        String adUrl = URLUtil.getURL("getADList", args);
        new NewNetAsyncTask(this).execute(adUrl);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(getFilesDir(), ((GlobalVeriable) getApplication()).getUserName());
                //如果文件存在，则读取
                if (file.exists()) {
                    startActivity(new Intent(ParentLayout.this, IsRightParentLock.class));
                } else {
                    startActivity(new Intent(ParentLayout.this, ChangePassword.class));
                }
            }
        });

        submitGameTime = (Button) findViewById(R.id.submit_game_time);
        submitGameTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParentLayout.this, SubmitGameTime.class));
            }
        });

        findPartner = (Button) findViewById(R.id.findPartner);
        findPartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParentLayout.this, WhetherToEnter.class));
            }
        });
        Shield = (Button) findViewById(R.id.parent_info_shield);
        //背景图的设置
        if (isShield) {
            Shield.setBackgroundResource(R.drawable.parent_allow_play);
        } else {
            Shield.setBackgroundResource(R.drawable.parent_prevent_play);
        }
        Shield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获得是否可以一键呼唤的数据
                isShield = ((GlobalVeriable) getApplication()).getShield();
                //背景图的设置
                if (isShield) {
                    Shield.setBackgroundResource(R.drawable.parent_allow_play);
                } else {
                    Shield.setBackgroundResource(R.drawable.parent_prevent_play);
                }
                if (isShield) {
                    sp = getSharedPreferences("shield", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("isShield", false);
                    editor.commit();
                    ((GlobalVeriable) ParentLayout.this.getApplication()).setShield(false);
                    Shield.setBackgroundResource(R.drawable.parent_prevent_play);
                } else {
                    sp = getSharedPreferences("shield", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("isShield", true);
                    editor.commit();
                    ((GlobalVeriable) ParentLayout.this.getApplication()).setShield(true);
                    Shield.setBackgroundResource(R.drawable.parent_allow_play);
                }
            }
        });
    }

    @Override
    public void getResult(String jsonStr) {
        if (jsonStr != null) {
            Message reciveMsg = json.fromJson(jsonStr, new TypeToken<Message>() {
            }.getType());
            if (reciveMsg.getMessageCode() != null && reciveMsg.getMessageCode() instanceof String) {
                switch (reciveMsg.getMessageCode()) {
                    case C.pub.GET_ADLIST_SUCCESS:
                        reciveMsg = json.fromJson(jsonStr, new TypeToken<Message<List<ADInfo>>>() {
                        }.getType());
                        adList = ((Message<List<ADInfo>>) reciveMsg).getMessageContent();
                        initialize();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * 新回调函数，传入url数组，一般情况只传入一个，即url[0]
     * 为兼容旧回调函数故做此处理
     * 2016/11/03
     *
     * @param url 所传入url数组
     * @return 返回请求API接口所得到的json数据
     */
    @Override
    public String doInBackground(String... url) {
        HttpUtil httpUtil = new HttpUtil(url[0]);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            return new String(bytes);
        }
        return null;
    }

    private void initialize() {
        cycleViewPager = (CycleViewPager) getFragmentManager()
                .findFragmentById(R.id.fragment_cycle_viewpager_content);

        ImageView lastImageView = new ImageView(this);
        lastImageView.setImageResource(R.drawable.lod);
        imgList.add(lastImageView);
        for (int i = 0; i < adList.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.lod);
            imgList.add(imageView);
        }
        ImageView firstImageView = new ImageView(this);
        firstImageView.setImageResource(R.drawable.lod);
        imgList.add(firstImageView);

        // 设置循环，在调用setData方法前调用
        cycleViewPager.setCycle(true);
        // 在加载数据前设置是否循环
        cycleViewPager.setData(imgList, adList, mAdCycleViewListener);
        //设置轮播
        cycleViewPager.setWheel(true);
        // 设置轮播时间，默认5000ms
        cycleViewPager.setTime(2000);
        //设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorCenter();
    }
}
