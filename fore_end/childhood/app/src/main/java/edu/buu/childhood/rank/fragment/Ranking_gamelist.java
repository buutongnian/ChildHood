package edu.buu.childhood.rank.fragment;


import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import edu.buu.childhood.R;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.rank.adapter.Ranking_gamelist_listview_Adapter;
import edu.buu.childhood.rank.pojo.ranking_gamelist_itembean;
import edu.buu.childhood.rank.service.GameRankServiceImpl;
import edu.buu.childhood.util.AsyncImageLoader;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.URLUtil;
import edu.buu.childhood.view.LoadDialog;

/**
 * Created by lcc on 2016/7/2.
 */
public class Ranking_gamelist extends Fragment implements CallBack {
    private ListView listView;
    private List<ranking_gamelist_itembean> rankDatalist;
    private String url;
    private ImageView firstImage;
    private ImageView secondImage;
    private ImageView thirdImage;
    private AsyncImageLoader imageLoader;
    private Dialog loadDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ranking_gamelist, container, false);
        listView = (ListView) view.findViewById(R.id.ranking_gamelist_listview);
        imageLoader = new AsyncImageLoader(getActivity());
        firstImage = (ImageView) view.findViewById(R.id.ranking_gamelist_imageView2);
        secondImage = (ImageView) view.findViewById(R.id.ranking_98dpgamelist_imageView1);
        thirdImage = (ImageView) view.findViewById(R.id.ranking_gamelist_imageView3);
        rankDatalist = new ArrayList<ranking_gamelist_itembean>();
        //delay();
        if (!checkNetWorkStatus(getActivity())) {
            setNetwork();
        } else {
            loadDialog = showDialog(getContext());
            Map args = new HashMap();
            args.put("userName", ((GlobalVeriable) getActivity().getApplication()).getUserName());
            args.put("pageNum", 1);
            url = URLUtil.getURL("gameRank", args);
            new NetAsyncTask(this).execute(url);
        }
        return view;
    }

    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            rankDatalist.addAll(result.getDatalist());
            Ranking_gamelist_listview_Adapter gameRank_adapter = new Ranking_gamelist_listview_Adapter(getActivity(), rankDatalist);
            listView.setAdapter(gameRank_adapter);
            if (result.getDatalist().size() >= 1) {
                String firstImageUrl = ((ranking_gamelist_itembean) result.getDatalist().get(0)).getGameIcon();
                if (!TextUtils.isEmpty(firstImageUrl)) {
                    firstImage.setTag(firstImageUrl);
                    Bitmap bitmap = imageLoader.loadImage(firstImage, firstImageUrl);
                    if (bitmap != null) {
                        firstImage.setImageBitmap(bitmap);
                    }
                }
            }
            if (result.getDatalist().size() >= 2) {
                String secondImageUrl = ((ranking_gamelist_itembean) result.getDatalist().get(1)).getGameIcon();
                if (!TextUtils.isEmpty(secondImageUrl)) {
                    secondImage.setTag(secondImageUrl);
                    Bitmap bitmap = imageLoader.loadImage(secondImage, secondImageUrl);
                    if (bitmap != null) {
                        secondImage.setImageBitmap(bitmap);
                    }
                }
            }

            if (result.getDatalist().size() >= 3) {
                String thirdImageUrl = ((ranking_gamelist_itembean) result.getDatalist().get(2)).getGameIcon();
                if (!TextUtils.isEmpty(thirdImageUrl)) {
                    thirdImage.setTag(thirdImageUrl);
                    Bitmap bitmap = imageLoader.loadImage(thirdImage, thirdImageUrl);
                    if (bitmap != null) {
                        thirdImage.setImageBitmap(bitmap);
                    }
                }
            }
        }
        if (loadDialog != null) {
            loadDialog.dismiss();
        }
    }

    @Override
    public CallBackPage doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(url);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            GameRankServiceImpl rankService = new GameRankServiceImpl();
            return rankService.getGameRankHeadInf(new String(bytes));
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
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
    }

    public Dialog showDialog(Context context) {
        Dialog dialog;
        dialog = new LoadDialog(context, R.style.Dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    public void delay() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                loadDialog = showDialog(getContext());
                loadDialog.show();
            }
        };
        timer.schedule(timerTask, 1000);
    }
}
