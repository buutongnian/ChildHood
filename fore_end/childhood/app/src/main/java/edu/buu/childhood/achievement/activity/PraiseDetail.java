package edu.buu.childhood.achievement.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.achievement.adapter.PraiseAdapter;
import edu.buu.childhood.achievement.pojo.LikeDetail;
import edu.buu.childhood.achievement.service.AchievementService;
import edu.buu.childhood.achievement.service.AchievementServiceImpl;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.game.refresh.AutoListView;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.NetWorkStatusUtil;
import edu.buu.childhood.util.URLUtil;

/**
 * Created by lcc on 2016/10/7.
 */
public class PraiseDetail extends BaseAvtivity implements CallBack, AutoListView.OnLoadListener, AutoListView.OnRefreshListener {
    private AutoListView listView;
    private List<LikeDetail> datalist = new ArrayList<LikeDetail>();
    private String getUserLikeListUrl;
    private Date date = new Date(2012 / 4 / 5);
    private Boolean isRefresh = true;
    private PraiseAdapter praiseAdapter;
    private int pageNum = 1;
    private TextView tvPrais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.praise_detail);
        listView = (AutoListView) findViewById(R.id.listView);
        tvPrais = (TextView) findViewById(R.id.praise_prompt);
        if (datalist.size() > 0) {
            praiseAdapter = new PraiseAdapter(this, datalist);
            listView.setAdapter(praiseAdapter);
        }
        listView.setOnLoadListener(this);
        listView.setOnRefreshListener(this);
        requestNetWork(1);
    }

    public void requestNetWork(int pageNum) {
        if (NetWorkStatusUtil.checkNetWorkStatus(this)) {
            Map map = new HashMap<>();
            map.put("userName", ((GlobalVeriable) getApplication()).getUserName());
            map.put("pageNum", pageNum);
            getUserLikeListUrl = URLUtil.getURL("getUserLikeList", map);
            new NetAsyncTask(this).execute(getUserLikeListUrl);
        } else {

        }
    }

    @Override
    public CallBackPage doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(url);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            AchievementService achievementService = new AchievementServiceImpl();
            return achievementService.getUserLikeList(new String(bytes));
        }
        return null;
    }

    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            if (result.getDatalist().size() > 0) {
                if (isRefresh) {
                    listView.onRefreshComplete();
                    datalist.clear();
                    datalist.addAll(result.getDatalist());
                } else {
                    listView.onLoadComplete();
                    datalist.addAll(result.getDatalist());
                }
                if (praiseAdapter == null) {
                    praiseAdapter = new PraiseAdapter(this, datalist);
                    listView.setAdapter(praiseAdapter);
                }
                if (result.getCurrentPage() == result.getTotalPage()) {
                    listView.setPageSize(result.getPageSize());
                    listView.setResultSize(result.getDatalist().size() - 1);

                } else {
                    listView.setPageSize(result.getPageSize());
                    listView.setResultSize(result.getDatalist().size());
                }
                praiseAdapter.notifyDataSetChanged();
            } else {
                tvPrais.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onLoad() {
        isRefresh = false;
        pageNum++;
        requestNetWork(pageNum);
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        pageNum = 1;
        requestNetWork(pageNum);
    }
}
