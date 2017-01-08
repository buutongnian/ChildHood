package edu.buu.childhood.baidumap.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.HashMap;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.baidumap.pojo.GameInfBean;
import edu.buu.childhood.baidumap.service.CreateDissolveGameServiceImpl;
import edu.buu.childhood.baidumap.service.CreateEndGameServiceImpl;
import edu.buu.childhood.baidumap.service.CreateStartGameServiceImpl;
import edu.buu.childhood.baidumap.service.JoinExitServiceImpl;
import edu.buu.childhood.baidumap.service.OnekeyServiceImpl;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.my.pojo.BackItem;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.URLUtil;

/**
 * Created by lcc on 2016/7/3.
 */
public class chatTeamCreate extends BaseAvtivity implements CallBack {
    private LinearLayout teamerName;
    private Button dissolveGameButton;
    private Button endGameButton;
    private Button startGameButton;
    private Button exitGameButton;
    private TextView chat_team_create_gameName;
    private TextView chat_team_create_gameFounder;
    private TextView chat_team_create_gamePlace;
    private TextView chat_team_create_gameSynopsis;
    private TextView chat_team_create_gameMembers;
    private String url1;
    private String url2;
    private String url3;
    private String getGameInfoUrl;
    private String exitUrl;
    private String gameScoreUrl;
    private int gameScore;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.chat_team_create);
        GlobalVeriable globalVeriable = ((GlobalVeriable) getApplication());

        chat_team_create_gameName = (TextView) findViewById(R.id.chat_team_create_gameName);
        chat_team_create_gameFounder = (TextView) findViewById(R.id.chat_team_create_gameFounder);
        chat_team_create_gamePlace = (TextView) findViewById(R.id.chat_team_create_gamePlace);
        chat_team_create_gameSynopsis = (TextView) findViewById(R.id.chat_team_create_gameSynopsis);
        chat_team_create_gameMembers = (TextView) findViewById(R.id.chat_team_create_gameMembers);
        if (checkNetWorkStatus(chatTeamCreate.this)) {
            if (((GlobalVeriable) getApplication()).getGameRunning() && ((GlobalVeriable) getApplication()).getGameId() != 0) {
                Map map = new HashMap();
                map.put("gameId", globalVeriable.getGameId());
                getGameInfoUrl = URLUtil.getURL("getGameInfo", map);
                new NetAsyncTask(this).execute(getGameInfoUrl);
            }
        } else {
            setNetwork();
        }
        teamerName = (LinearLayout) findViewById(R.id.chatTeamCreat_teamername);
        teamerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(chatTeamCreate.this, TeamerNameActivity.class));
            }
        });
        dissolveGameButton = (Button) findViewById(R.id.chatTeamCreateDissolveGame);
        dissolveGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkNetWorkStatus(chatTeamCreate.this)) {
                    setNetwork();
                } else {
                    Map args = new HashMap();
                    args.put("gameId", ((GlobalVeriable) getApplication()).getGameId());
                    url1 = URLUtil.getURL("founderCancelGame", args);
                    new NetAsyncTask(chatTeamCreate.this).execute(url1);
                    ((GlobalVeriable) getApplication()).setGameRunning(false);
                }
            }
        });


        endGameButton = (Button) findViewById(R.id.chatTeamCreateEndGame);
        endGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkNetWorkStatus(chatTeamCreate.this)) {
                    setNetwork();
                } else {
                    Map args = new HashMap();
                    args.put("gameId", ((GlobalVeriable) getApplication()).getGameId());
                    url2 = URLUtil.getURL("finishGame", args);
                    new NetAsyncTask(chatTeamCreate.this).execute(url2);

                }
            }
        });

        startGameButton = (Button) findViewById(R.id.chatTeamCreateStartGame);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkNetWorkStatus(chatTeamCreate.this)) {
                    setNetwork();
                } else {
                    Map args = new HashMap();
                    args.put("gameId", ((GlobalVeriable) getApplication()).getGameId());
                    url3 = URLUtil.getURL("startGame", args);
                    new NetAsyncTask(chatTeamCreate.this).execute(url3);
                }
            }
        });
        exitGameButton = (Button) findViewById(R.id.chatTeamCreateExitGame);
        exitGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkNetWorkStatus(chatTeamCreate.this)) {
                    setNetwork();
                } else {
                    Map args = new HashMap();
                    args.put("userName", ((GlobalVeriable) getApplication()).getUserName());
                    args.put("gameId", ((GlobalVeriable) getApplication()).getGameId());
                    exitUrl = URLUtil.getURL("userExitGame", args);
                    new NetAsyncTask(chatTeamCreate.this).execute(exitUrl);

                }
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            if ("gameInfo".equals(result.getFalg())) {
                if (result.getDatalist().size() > 0) {
                    GameInfBean gameInfBean = (GameInfBean) result.getDatalist().get(0);
                    chat_team_create_gameName.setText(gameInfBean.getGameName());
                    chat_team_create_gameFounder.setText(gameInfBean.getGameFounderNickName());
                    chat_team_create_gamePlace.setText(gameInfBean.getGamePlace());
                    chat_team_create_gameSynopsis.setText(gameInfBean.getGameSynopsis());
                    chat_team_create_gameMembers.setText(gameInfBean.getGameFounderNickName() + "（发起人）");
                    if (!gameInfBean.getGameFounderName().equals(((GlobalVeriable) getApplication()).getUserName())) {
                        dissolveGameButton.setVisibility(View.GONE);
                        endGameButton.setVisibility(View.GONE);
                        startGameButton.setVisibility(View.GONE);
                    } else {
                        if (!C.status.STARTED.equals(gameInfBean.getGameStatus())) {
                            endGameButton.setVisibility(View.GONE);
                        } else {
                            dissolveGameButton.setVisibility(View.GONE);
                            startGameButton.setVisibility(View.GONE);
                        }
                        exitGameButton.setVisibility(View.GONE);
                    }
                }
            } else if ("startGame".equals(result.getFalg())) {
                finish();
                //游戏开始请求成功
                Toast.makeText(this, "游戏已经开始", Toast.LENGTH_SHORT).show();
            } else if ("scoreSuccess".equals(result.getFalg())) {
                ((GlobalVeriable) getApplication()).setGameRunning(false);
                startActivity(new Intent(this, MainBaidu.class));
                finish();
            } else {
                BackItem bean = (BackItem) result.getDatalist().get(0);
                if (bean.getIsright() == null) {
                    Toast.makeText(this, "提交不成功", Toast.LENGTH_SHORT).show();
                } else {
                    if (C.onekey.SCORE_SUCCESS.equals(bean.getGameStatus())) {
                        //Todo 游戏结束后评价
                        evaluateDialog();
                    } else {
                        ((GlobalVeriable) getApplication()).setGameRunning(false);
                        Intent intent = new Intent(this, MainBaidu.class);
                        intent.putExtra("flag", "flag");
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }
    }

    public CallBackPage doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(url);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            if (url.equals(url1)) {
                CreateDissolveGameServiceImpl Service = new CreateDissolveGameServiceImpl();
                return Service.getCreateDissolveGameHeadInf(new String(bytes));
            } else if (url.equals(url2)) {
                CreateEndGameServiceImpl Service = new CreateEndGameServiceImpl();
                return Service.getCreateEndGameHeadInf(new String(bytes));
            } else if (url.equals(getGameInfoUrl)) {
                OnekeyServiceImpl onekeyService = new OnekeyServiceImpl();
                return onekeyService.getGameInfo(new String(bytes));
            } else if (url.equals(exitUrl)) {
                JoinExitServiceImpl Service = new JoinExitServiceImpl();
                return Service.getJoinExitHeadInf(new String(bytes));
            } else if (url.equals(gameScoreUrl)) {
                OnekeyServiceImpl onekeyService = new OnekeyServiceImpl();
                return onekeyService.gameScore(new String(bytes));
                //Todo 游戏评分
            } else {
                CreateStartGameServiceImpl Service = new CreateStartGameServiceImpl();
                return Service.getCreateStartGameHeadInf(new String(bytes));
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
                if (Build.VERSION.SDK_INT > 10) {
                    intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
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

    public void evaluateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(chatTeamCreate.this);
        builder.setIcon(R.drawable.logo);
        builder.setTitle("请选择您对游戏的星级");
        final String[] evaluate = {"一颗星", "两颗星", "三颗星", "四颗星", "五颗星"};
        //    设置一个单项选择下拉框
        /**
         * 第一个参数指定我们要显示的一组下拉单选框的数据集合
         * 第二个参数代表索引，指定默认哪一个单选框被勾选上，1表示默认'一颗星' 会被勾选上
         * 第三个参数给每一个单选项绑定一个监听器
         */
        builder.setSingleChoiceItems(evaluate, 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gameScore = which + 1;
                Toast.makeText(chatTeamCreate.this, "您的评价是：" + evaluate[which], Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Map map = new HashMap();
                map.put("gameId", ((GlobalVeriable) getApplication()).getGameId());
                map.put("userName", ((GlobalVeriable) getApplication()).getUserName());
                map.put("gameScore", gameScore);
                gameScoreUrl = URLUtil.getURL("scoreGame", map);
                new NetAsyncTask(chatTeamCreate.this).execute(gameScoreUrl);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
