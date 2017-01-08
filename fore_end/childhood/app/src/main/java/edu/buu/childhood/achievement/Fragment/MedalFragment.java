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
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.achievement.service.AchievementServiceImpl;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.achievement.pojo.UserMedalList;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.URLUtil;

/**
 * Created by lcc on 2016/9/27.
 */
public class MedalFragment extends Fragment implements CallBack {
    //注册广播
    private IntentFilter filter;
    private MyReceiver mReceiver;
    //勋章
    private List<UserMedalList> medalDatalist = new ArrayList<UserMedalList>();
    private String medalUrl;
    private ImageView ivMedal1;
    private ImageView ivMedal2;
    private ImageView ivMedal3;
    private ImageView ivMedal4;
    private ImageView ivMedal5;
    private ImageView ivMedal6;
    private ImageView ivMedal7;
    private ImageView ivMedal8;
    private ImageView ivMedal9;
    private ImageView ivMedal10;

    private ImageView[] imageViews = new ImageView[10];
    private int[] imageResources = {R.drawable.owned1, R.drawable.owned2, R.drawable.owned3, R.drawable.owned4, R.drawable.owned5, R.drawable.owned6, R.drawable.owned7, R.drawable.owned8, R.drawable.owned9, R.drawable.owned10};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.medal_fragment, container, false);
        mReceiver = new MyReceiver();
        filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        getActivity().registerReceiver(mReceiver, filter);
        initView(view);
        return view;
    }

    public void initView(View view) {
        ivMedal1 = (ImageView) view.findViewById(R.id.ivMedal1);
        ivMedal2 = (ImageView) view.findViewById(R.id.ivMedal2);
        ivMedal3 = (ImageView) view.findViewById(R.id.ivMedal3);
        ivMedal4 = (ImageView) view.findViewById(R.id.ivMedal4);
        ivMedal5 = (ImageView) view.findViewById(R.id.ivMedal5);
        ivMedal6 = (ImageView) view.findViewById(R.id.ivMedal6);
        ivMedal7 = (ImageView) view.findViewById(R.id.ivMedal7);
        ivMedal8 = (ImageView) view.findViewById(R.id.ivMedal8);
        ivMedal9 = (ImageView) view.findViewById(R.id.ivMedal9);
        ivMedal10 = (ImageView) view.findViewById(R.id.ivMedal10);
        imageViews[0] = ivMedal1;
        imageViews[1] = ivMedal2;
        imageViews[2] = ivMedal3;
        imageViews[3] = ivMedal4;
        imageViews[4] = ivMedal5;
        imageViews[5] = ivMedal6;
        imageViews[6] = ivMedal7;
        imageViews[7] = ivMedal8;
        imageViews[8] = ivMedal9;
        imageViews[9] = ivMedal10;
    }

    @Override
    public void onResume() {

        super.onResume();
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
                    medalUrl = URLUtil.getURL("getUserMedalList", args);
                    Log.i("medalUrl", medalUrl);
                    new NetAsyncTask(MedalFragment.this).execute(medalUrl);
                }
            }
        }
    }

    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            if (result.getDatalist().size() > 0) {
                for (int i = 0; i < result.getDatalist().size(); i++) {
                    UserMedalList userMedalList = (UserMedalList) result.getDatalist().get(i);
                    int id = userMedalList.getMedalId() - 1;
                    if (imageViews[id] != null) {
                        imageViews[id].setImageResource(imageResources[id]);
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
            if (url.equals(medalUrl)) {
                AchievementServiceImpl Service = new AchievementServiceImpl();
                return Service.getUserMedalListHeadInf(new String(bytes));
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
        Toast.makeText(getActivity(), "wifi is closed!", Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.error);
        builder.setTitle("网络提示信息");
        builder.setMessage("网络不可用，如果继续，请先设置网络！");
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
