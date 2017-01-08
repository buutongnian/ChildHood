package edu.buu.childhood.baidumap.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.baidumap.pojo.GameInfBean;
import edu.buu.childhood.baidumap.service.JoinExitServiceImpl;
import edu.buu.childhood.baidumap.service.OnekeyServiceImpl;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.my.pojo.BackItem;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.URLUtil;

/**
 * Created by lcc on 2016/7/3.
 */
public class chatTeamJoin extends Activity implements CallBack {
    private LinearLayout teamerName;
    private Button exitGame;
    private String url;
    private TextView chat_team_join_gameName;
    private TextView chat_team_join_gameFounder;
    private TextView chat_team_join_gamePlace;
    private TextView chat_team_join_gameSynopsis;
    private String getGameInfoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.chat_team_join);
        chat_team_join_gameName = (TextView) findViewById(R.id.chat_team_join_gameName);
        chat_team_join_gameFounder = (TextView) findViewById(R.id.chat_team_join_gameFounder);
        chat_team_join_gamePlace = (TextView) findViewById(R.id.chat_team_join_gamePlace);
        chat_team_join_gameSynopsis = (TextView) findViewById(R.id.chat_team_join_gameSynopsis);
        Map map = new HashMap();
        GlobalVeriable globalVeriable = ((GlobalVeriable) getApplication());
        map.put("gameId", globalVeriable.getGameId());
        getGameInfoUrl = URLUtil.getURL("getGameInfo", map);
        new NetAsyncTask(this).execute(getGameInfoUrl);
        teamerName = (LinearLayout) findViewById(R.id.chartTeamJion_teamername);
        teamerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(chatTeamJoin.this, TeamerNameActivity.class));
            }
        });
        exitGame = (Button) findViewById(R.id.chartTeamJion_button);
        exitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkNetWorkStatus(chatTeamJoin.this)) {
                    setNetwork();
                } else {
                    Map args = new HashMap();
                    args.put("userName", "joe");
                    args.put("gameId", 12);
                    url = URLUtil.getURL("userExitGame", args);
                    new NetAsyncTask(chatTeamJoin.this).execute(url);
                }
            }
        });
    }

    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            if ("gameInfo".equals(result.getFalg())) {
                if (result.getDatalist().size() > 0) {
                    GameInfBean gameInfBean = (GameInfBean) result.getDatalist().get(0);
                    chat_team_join_gameName.setText(gameInfBean.getGameName());
                    chat_team_join_gameFounder.setText(gameInfBean.getGameFounderNickName());
                    chat_team_join_gamePlace.setText(gameInfBean.getGamePlace());
                    chat_team_join_gameSynopsis.setText(gameInfBean.getGameSynopsis());
                }
            } else {
                BackItem bean = (BackItem) result.getDatalist().get(0);
                if (bean.getIsright() == null) {
                    Toast.makeText(this, "提交不成功", Toast.LENGTH_SHORT).show();
                } else {
                    ((GlobalVeriable) getApplication()).setGameRunning(false);
                }
            }
        }
    }

    public CallBackPage doInBackground(String url) {

        HttpUtil httpUtil = new HttpUtil(url);
        if (url.equals(getGameInfoUrl)) {
            OnekeyServiceImpl onekeyService = new OnekeyServiceImpl();
            return onekeyService.getGameInfo(new String(httpUtil.getHttpData()));
        } else {
            JoinExitServiceImpl Service = new JoinExitServiceImpl();
            return Service.getJoinExitHeadInf(new String(httpUtil.getHttpData()));
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
                dialog.dismiss();
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
