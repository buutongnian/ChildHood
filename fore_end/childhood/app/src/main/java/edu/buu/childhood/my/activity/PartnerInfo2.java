package edu.buu.childhood.my.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.my.pojo.UserInfo;
import edu.buu.childhood.my.service.PartnerInfoServiceImpl;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.URLUtil;

/**
 * Created by lcc on 2016/9/11.
 */
public class PartnerInfo2 extends BaseAvtivity implements CallBack {
    private String PartnerInfoUrl;
    //定义基本信息
    private TextView nickname;
    private TextView province;
    private TextView city;
    private TextView rank;
    private TextView achievement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.partner_info2);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        nickname = (TextView) findViewById(R.id.partnerInfoNi);
        province = (TextView) findViewById(R.id.partnerInfoProvince);
        city = (TextView) findViewById(R.id.partnerInfoCity);
        rank = (TextView) findViewById(R.id.partnerInfoRank);
        achievement = (TextView) findViewById(R.id.partnerInfoAchievement);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        Map args = new HashMap();
        args.put("userName", ((GlobalVeriable) getApplication()).getUserName());
        args.put("targetUser", name);
        PartnerInfoUrl = URLUtil.getURL("getUserInfo", args);
        new NetAsyncTask(PartnerInfo2.this).execute(PartnerInfoUrl);
    }

    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            UserInfo userInfo = (UserInfo) result.getDatalist().get(0);
            nickname.setText(userInfo.getUserNickname());
            province.setText(String.valueOf(userInfo.getBelongingProvince()));
            city.setText(String.valueOf(userInfo.getBelongingCity()));
            rank.setText(String.valueOf(userInfo.getUserLevel()));
            achievement.setText(String.valueOf(userInfo.getAchievementPoint()));
        }
    }

    @Override
    public CallBackPage doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(url);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            PartnerInfoServiceImpl service = new PartnerInfoServiceImpl();
            return service.getPartnerInfoHeadInf(new String(bytes));
        }
        return null;
    }

}