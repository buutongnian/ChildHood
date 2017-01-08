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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import edu.buu.childhood.Interface.BtnListener;
import edu.buu.childhood.R;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.activity.UserInfoActivity;
import edu.buu.childhood.rank.adapter.Ranking_personlist_listview_Adapter;
import edu.buu.childhood.rank.pojo.UserRank;
import edu.buu.childhood.rank.service.RankService;
import edu.buu.childhood.rank.service.RankServiceImpl;
import edu.buu.childhood.rank.service.UpdateParise;
import edu.buu.childhood.util.AsyncImageLoader;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.URLUtil;
import edu.buu.childhood.view.LoadDialog;

/**
 * Created by lcc on 2016/6/25.
 */
public class Ranking_personlist extends Fragment implements CallBack, BtnListener {
    private ListView listView;
    private List<UserRank> rankDatalist;
    private String url;
    private ImageView firstImage;
    private ImageView secondImage;
    private ImageView thirdImage;
    private AsyncImageLoader imageLoader;
    public static Map isCouldLikeUser = new HashMap();
    private Dialog loadDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ranking_personlist, container, false);
        imageLoader = new AsyncImageLoader(getActivity());
        listView = (ListView) view.findViewById(R.id.ranking_personlist_listview);
        firstImage = (ImageView) view.findViewById(R.id.ranking_personlist_imageView2);
        secondImage = (ImageView) view.findViewById(R.id.ranking_personlist_imageView1);
        thirdImage = (ImageView) view.findViewById(R.id.ranking_personlist_imageView3);
        rankDatalist = new ArrayList<UserRank>();
        if (!checkNetWorkStatus(getActivity())) {
            setNetwork();
        } else {
            //delay();
            loadDialog = showDialog(getContext());
            Map args = new HashMap();
            args.put("userName", ((GlobalVeriable) getActivity().getApplication()).getUserName());
            url = URLUtil.getURL("userRank", args);
            new NetAsyncTask(this).execute(url);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserRank userRank = rankDatalist.get(position);
                if (userRank != null) {
                    Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                    intent.putExtra("userName", userRank.getUserName());
                    intent.putExtra("userNickName", userRank.getUsernNickname());
                    intent.putExtra("imageUrl", userRank.getUserHeadImage());
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            List<UserRank> list = result.getRankList().getRankList();
            rankDatalist.add(result.getRankList().getSelfRank());
            rankDatalist.addAll(list);
            Ranking_personlist_listview_Adapter userRank_adapter = new Ranking_personlist_listview_Adapter(getActivity(), rankDatalist, this);
            listView.setAdapter(userRank_adapter);
            if (list.size() >= 1) {
                String firstImageUrl = ((UserRank) result.getRankList().getRankList().get(0)).getUserHeadImage();
                if (!TextUtils.isEmpty(firstImageUrl)) {
                    firstImage.setTag(firstImageUrl);
                    Bitmap bitmap = imageLoader.loadImage(firstImage, firstImageUrl);
                    if (bitmap != null) {
                        firstImage.setImageBitmap(bitmap);
                    }
                }
            }
            if (list.size() >= 2) {
                String secondImageUrl = ((UserRank) result.getRankList().getRankList().get(1)).getUserHeadImage();
                if (!TextUtils.isEmpty(secondImageUrl)) {
                    secondImage.setTag(secondImage);
                    Bitmap bitmap = imageLoader.loadImage(secondImage, secondImageUrl);
                    if (bitmap != null) {
                        secondImage.setImageBitmap(bitmap);
                    }
                }
            }
            if (list.size() >= 3) {
                String thirdImageUrl = ((UserRank) result.getRankList().getRankList().get(2)).getUserHeadImage();
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
            RankService rankService = new RankServiceImpl();
            CallBackPage callBackPage = new CallBackPage();
            callBackPage.setRankList(rankService.getUserRank(new String(bytes)));
            return callBackPage;
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

    @Override
    public void onClick(View v, int positation) {
        UserRank personlistItembean = rankDatalist.get(positation);
        if (isCouldLikeUser.get(personlistItembean.getUserName()) == null) {
            int heat = personlistItembean.getLikeCount();
            Map map = new HashMap();
            map.put("userName", ((GlobalVeriable) getActivity().getApplication()).getUserName());
            map.put("access", C.def.ACCESS_RANKINGLIST);
            map.put("likeUser", personlistItembean.getUserName());
            String url = URLUtil.getURL("likeUser", map);
            new UpdateParise(heat, listView, positation, url, personlistItembean.getUserName());
        } else {
            Toast.makeText(getActivity(), "你今天已经对该用户点过赞了", Toast.LENGTH_SHORT).show();
        }
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
                if (rankDatalist.size() < 0) {
                    loadDialog = showDialog(getActivity());
                    loadDialog.show();
                }
            }
        };
        timer.schedule(timerTask, 1000);
    }
}
