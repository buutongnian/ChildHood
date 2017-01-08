package edu.buu.childhood.achievement.Fragment;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.achievement.adapter.TrackAdapter;
import edu.buu.childhood.achievement.pojo.UserEventList;
import edu.buu.childhood.achievement.service.AchievementServiceImpl;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.game.refresh.AutoListView;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.NetWorkStatusUtil;
import edu.buu.childhood.util.URLUtil;

/**
 * Created by lcc on 2016/9/27.
 */
public class TrackFragment extends Fragment implements CallBack, AutoListView.OnRefreshListener, AutoListView.OnLoadListener {
    //足迹事迹
    private List<UserEventList> datalist = new ArrayList<UserEventList>();
    private String footUrl;
    //注册广播
    private IntentFilter filter;
    private MyReceiver mReceiver;
    private TrackAdapter trackAdapter = null;
    private AutoListView trackListView;
    private Boolean isRefresh = true;
    private int pageNum = 1;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.track_fragment, container, false);
        mReceiver = new MyReceiver();
        filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        trackListView = (AutoListView) view.findViewById(R.id.listView);
        trackListView.setOnRefreshListener(this);
        trackListView.setOnLoadListener(this);
        if (datalist.size() > 0) {
            trackAdapter = new TrackAdapter(getActivity(), datalist);
            trackListView.setAdapter(trackAdapter);
        }
        getActivity().registerReceiver(mReceiver, filter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    //加载更多的数据
    @Override
    public void onLoad() {
        if (NetWorkStatusUtil.checkNetWorkStatus(getContext())) {
            isRefresh = false;
            pageNum++;
            GlobalVeriable globalVeriable = ((GlobalVeriable) getActivity().getApplication());
            Map args = new HashMap();
            args.put("userName", globalVeriable.getUserName());
            args.put("pageNum", pageNum);
            footUrl = URLUtil.getURL("getUserFootPrint", args);
            new NetAsyncTask(TrackFragment.this).execute(footUrl);
        } else {
            setNetwork();
        }
    }

    //刷新数据
    @Override
    public void onRefresh() {
        if (NetWorkStatusUtil.checkNetWorkStatus(getContext())) {
            pageNum = 1;
            isRefresh = true;
            GlobalVeriable globalVeriable = ((GlobalVeriable) getActivity().getApplication());
            Map args = new HashMap();
            args.put("userName", globalVeriable.getUserName());
            args.put("pageNum", 1);
            new NetAsyncTask(TrackFragment.this).execute(footUrl);
        } else {
            setNetwork();
        }
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            GlobalVeriable globalVeriable = ((GlobalVeriable) getActivity().getApplication());
            if (!checkNetWorkStatus(getActivity())) {
                setNetwork();
            } else {
                if (globalVeriable.getUserName() != null) {
                    Map args = new HashMap();
                    args.put("userName", globalVeriable.getUserName());
                    args.put("pageNum", 1);
                    footUrl = URLUtil.getURL("getUserFootPrint", args);
                    new NetAsyncTask(TrackFragment.this).execute(footUrl);
                }
            }
        }

    }

    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            if (result.getDatalist().size() > 0) {
                if (isRefresh) {
                    datalist.clear();
                    trackListView.onRefreshComplete();
                    handleResultList(result.getDatalist());
                } else {
                    trackListView.onLoadComplete();
                    handleResultList(result.getDatalist());
                }
            }
            if (trackAdapter == null) {
                trackAdapter = new TrackAdapter(getActivity(), datalist);
                trackListView.setAdapter(trackAdapter);
            }
            if (result.getTotalPage() == result.getCurrentPage()) {
                trackListView.setPageSize(result.getPageSize());
                trackListView.setResultSize(result.getDatalist().size() - 1);
            } else {
                trackListView.setPageSize(result.getPageSize());
                trackListView.setResultSize(result.getDatalist().size());
            }
            trackAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public CallBackPage doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(url);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            if (url.equals(footUrl)) {
                AchievementServiceImpl Service = new AchievementServiceImpl();
                return Service.getUserFootPrintHeadInf(new String(bytes));
            }
        }
        return null;
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.error);
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
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
    }

    public List<UserEventList> handleResultList(List<UserEventList> list) {
        int lastDay = 0;
        List<UserEventList> intLIst = new ArrayList<>();
        for (UserEventList userEventList : list) {
            if (Integer.parseInt(userEventList.getDay()) == lastDay) {
                datalist.add(userEventList);
            } else {
                UserEventList userEventListDay = new UserEventList();
                userEventListDay.setTemplateStr("Day ");
                userEventListDay.setEventParams(userEventList.getDay());
                userEventListDay.setDay(userEventList.getDay());
                datalist.add(userEventListDay);
                datalist.add(userEventList);
            }
        }
      /*  int lastI = intLIst.size() - 1;
       for (int i = intLIst.size() - 1; i>=0; i--) {
            if (intLIst.get(i).getTemplateStr().equals("Day ")) {
                for (int j = i; j <= lastI; j++) {
                    datalist.add(intLIst.get(j));
                }
                lastI = i - 1;
            }
        }*/
        return datalist;
    }
}