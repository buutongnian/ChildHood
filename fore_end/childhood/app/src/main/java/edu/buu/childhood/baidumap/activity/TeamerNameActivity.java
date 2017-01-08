package edu.buu.childhood.baidumap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.baidumap.adapter.MembersAdapter;
import edu.buu.childhood.baidumap.pojo.InfoList;
import edu.buu.childhood.baidumap.pojo.MemberInfo;
import edu.buu.childhood.baidumap.pojo.MembersBean;
import edu.buu.childhood.baidumap.service.OnekeyServiceImpl;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.common.activity.UserInfoActivity;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.URLUtil;

/**
 * Created by lcc on 2016/7/4.
 */
public class TeamerNameActivity extends BaseAvtivity implements CallBack {
    private ListView listView;
    private ArrayList<MembersBean> membersDatalist = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.teamer_name);
        listView = (ListView) findViewById(R.id.teamer_name_listview);
        Intent intent = getIntent();
        String gameId = intent.getStringExtra("gameId");
        if (gameId != null) {
            Map map = new HashMap();
            map.put("gameId", gameId);
            new NetAsyncTask(this).execute(URLUtil.getURL("getTeamMembers", map));
        } else {
            Map map = new HashMap();
            map.put("gameId", ((GlobalVeriable) getApplication()).getGameId());
            new NetAsyncTask(this).execute(URLUtil.getURL("getTeamMembers", map));
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MembersBean membersBean = membersDatalist.get(position);
                Intent intent = new Intent(TeamerNameActivity.this, UserInfoActivity.class);
                intent.putExtra("userName", membersBean.getUserName());
                intent.putExtra("userNickName", membersBean.getMemberName());
                intent.putExtra("imageUrl", membersBean.getMemberImage());
                startActivity(intent);
            }
        });
    }


    @Override
    public CallBackPage doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(url);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            OnekeyServiceImpl onekeyService = new OnekeyServiceImpl();
            return onekeyService.getMembersInfo(new String(bytes));
        }
        return null;
    }

    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            membersDatalist = new ArrayList<MembersBean>();
            InfoList<MemberInfo> infoList = (InfoList<MemberInfo>) result.getDatalist().get(0);
            for (int i = 0; i < infoList.getDataList().size(); i++) {
                MemberInfo memberInfo = infoList.getDataList().get(i);
                MembersBean membersBean = new MembersBean();
                membersBean.setUserName(memberInfo.getUserName());
                membersBean.setMemberName(memberInfo.getUsernNickname());
                membersBean.setMemberImage(memberInfo.getUserHeadImage());
                membersDatalist.add(membersBean);
            }
            MembersAdapter membersAdapter = new MembersAdapter(this, membersDatalist);
            listView.setAdapter(membersAdapter);
        }


    }
}

