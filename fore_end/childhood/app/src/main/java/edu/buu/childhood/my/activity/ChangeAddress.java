package edu.buu.childhood.my.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.my.pojo.BackItem;
import edu.buu.childhood.my.service.LiveCommunityServiceImpl;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.URLUtil;
import edu.buu.childhood.util.ValidateUtil;

/**
 * Created by lcc on 2016/7/8.
 */
public class ChangeAddress extends BaseAvtivity implements CallBack, View.OnClickListener {
    private TextView save;
    private EditText m_editText;
    private String backname;
    private String url;
    private String a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.my_signature);

        //接收数据
        Intent intent = getIntent();
        backname = intent.getStringExtra("address");
        findViewById(R.id.changeAddress_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });//返回键
        m_editText = (EditText) findViewById(R.id.changeAddress_editText);
        m_editText.selectAll();
        m_editText.setText(backname);
        save = (TextView) findViewById(R.id.changeAddress_save);
        save.setOnClickListener(this);
    }

    public void onClick(View v) {
        a = m_editText.getText().toString();
        if (!checkNetWorkStatus(this)) {
            setNetwork();
        } else {
            if (ValidateUtil.isEmpty(a)) {
                Toast.makeText(this, "地址不可为空", Toast.LENGTH_LONG).show();
            } else {
//url
                Map args = new HashMap();
                args.put("userName", "joe");
                args.put("liveCommunity", a);
                url = URLUtil.getURL("changeUserInf", args);
                new NetAsyncTask(this).execute(url);
                //回到上一页面
                //发送数据
                Intent intent = new Intent();
                intent.putExtra("back", m_editText.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }

        }
    }

    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            BackItem bean = (BackItem) result.getDatalist().get(0);
            if (bean.getIsright() == null) {
                Toast.makeText(this, "提交不成功", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public CallBackPage doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(url);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            LiveCommunityServiceImpl Service = new LiveCommunityServiceImpl();
            return Service.getLiveCommunityChangeHeadInf(new String(bytes));
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
