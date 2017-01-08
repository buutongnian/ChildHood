package edu.buu.childhood.game.activity;


import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import edu.buu.childhood.R;
import edu.buu.childhood.baidumap.activity.MainBaidu;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.activity.BaseAvtivity;


/**
 * Created by lcc on 2016/6/17.
 */
public class gameWebview extends BaseAvtivity {
    private WebView webView;
    private ImageView back;
    private ImageView refresh;
    private TextView titleview;
    private String url;
    private String gameTitle;
    private Dialog dialog;
    //一键呼唤所需要的游戏名称
    private TextView diaGameName;
    //一键呼唤所需要游戏编码
    private TextView diaGameCode;
    private String userName;
    private String oneKeyUrl;

    //开始游戏
    private ImageView startGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_webview);
        if (((GlobalVeriable) getApplication()).getLogin())
            userName = ((GlobalVeriable) getApplication()).getUserName();
        Intent intent = getIntent();
        url = intent.getStringExtra("gameContent");
        gameTitle = intent.getStringExtra("gameTitle");
        titleview = (TextView) findViewById(R.id.game_webview_title);
        titleview.setText(gameTitle);
        if (!checkNetWorkStatus(this)) {
            setNetwork();
        } else {
            webView = (WebView) findViewById(R.id.some_webView_webView);
            refresh = (ImageView) findViewById(R.id.some_webView_refresh);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setBlockNetworkImage(false);
            webView.loadUrl(url);
            webView.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return super.shouldOverrideUrlLoading(view, url);
                }
            });
            refresh.setOnClickListener(new MyLisenter());
            //开始游戏
            startGame = (ImageView) findViewById(R.id.startGame);
            startGame.setOnClickListener(new MyLisenter());
        }
    }

    class MyLisenter implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.some_webView_refresh:
                    webView.reload();
                    break;
                case R.id.startGame:
                    startActivity(new Intent(gameWebview.this, MainBaidu.class));
                    break;
                default:
                    break;
            }
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.logo);
        builder.setTitle("网络提示信息");
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

    @Override
    protected void onPause() {
        webView.onPause();
        super.onPause();
    }
}
