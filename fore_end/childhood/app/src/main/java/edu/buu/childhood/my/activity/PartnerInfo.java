package edu.buu.childhood.my.activity;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
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
public class PartnerInfo extends BaseAvtivity implements CallBack {
    private String url;
    //定义基本信息
    private TextView nickname;
    private TextView province;
    private TextView city;
    private TextView district;
    private TextView detailAddr;
    private TextView email;
    private TextView rank;
    private TextView achievement;
    private LinearLayout parent;
    private LinearLayout child;
    //定义家长dialog
    private Dialog parentDialog;
    //定义孩子dialog
    private Dialog childDialog;
    //家长dialog的基本信息
    private TextView mName;
    private TextView mTell;
    private TextView fName;
    private TextView fTell;
    //孩子dialog的基本信息
    private TextView cName;
    private TextView cSex;
    private TextView cBirthday;
    private TextView cEducation;
    private TextView cSchool;
    private TextView cGrade;
    //孩子名字信息dialog


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.partner_info);
        nickname = (TextView) findViewById(R.id.partnerInfoNi);
        province = (TextView) findViewById(R.id.partnerInfoProvince);
        city = (TextView) findViewById(R.id.partnerInfoCity);
        district = (TextView) findViewById(R.id.partnerInfoDistrict);
        detailAddr = (TextView) findViewById(R.id.partnerInfoDetailAddr);
        email = (TextView) findViewById(R.id.partnerInfoEmail);
        rank = (TextView) findViewById(R.id.partnerInfoRank);
        achievement = (TextView) findViewById(R.id.partnerInfoAchievement);
        parent = (LinearLayout) findViewById(R.id.parentInfo);
        child = (LinearLayout) findViewById(R.id.childInfo);

        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        Map args = new HashMap();
        args.put("useName", ((GlobalVeriable) getApplication()).getUserName());
        args.put("targetUser", name);
        url = URLUtil.getURL("getUserInfo", args);
        new NetAsyncTask(PartnerInfo.this).execute(url);
    }

    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            UserInfo userInfo = (UserInfo) result.getDatalist().get(0);
            nickname.setText(userInfo.getUserNickname());
            province.setText(userInfo.getBelongingProvince());
            city.setText(userInfo.getBelongingCity());
            district.setText(userInfo.getBelongingDistrict());
            detailAddr.setText(userInfo.getDetailAddr());
            email.setText(userInfo.getEmail());
            rank.setText(userInfo.getUserLevel());
            achievement.setText(userInfo.getAchievementPoint());
            //孩子信息
            cName.setText(userInfo.getChildInf().get(0).getChildName());

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

    public void ParentDialog() {
        LayoutInflater inflater = LayoutInflater.from(PartnerInfo.this);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.parent_diolag_model, null);
        parentDialog = new AlertDialog.Builder(PartnerInfo.this).create();
        parentDialog.setCancelable(false);
        parentDialog.show();
        parentDialog.getWindow().setContentView(layout);
        mName = (TextView) findViewById(R.id.PartnerInfo_mName);
        mTell = (TextView) findViewById(R.id.PartnerInfo_mTell);
        fName = (TextView) findViewById(R.id.PartnerInfo_fName);
        fTell = (TextView) findViewById(R.id.PartnerInfo_fTell);
    }

    public void ChildDialog() {
        LayoutInflater inflater = LayoutInflater.from(PartnerInfo.this);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.child_dialog_model, null);
        childDialog = new AlertDialog.Builder(PartnerInfo.this).create();
        childDialog.setCancelable(false);
        childDialog.show();
        childDialog.getWindow().setContentView(layout);
        cName = (TextView) findViewById(R.id.childInfoName);
        cSex = (TextView) findViewById(R.id.childInfoSex);
        cBirthday = (TextView) findViewById(R.id.childInfoBir);
        cEducation = (TextView) findViewById(R.id.childInfoEducation);
        cSchool = (TextView) findViewById(R.id.childInfoSchool);
        cGrade = (TextView) findViewById(R.id.childInfoGrade);
    }

    public void ChildNameDialog() {

    }
}