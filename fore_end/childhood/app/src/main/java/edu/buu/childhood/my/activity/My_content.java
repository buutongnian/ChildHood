package edu.buu.childhood.my.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.my.pojo.User;
import edu.buu.childhood.my.service.myContentServiceImpl;
import edu.buu.childhood.util.AsyncImageLoader;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.URLUtil;

/**
 * Created by lcc on 2016/6/7.
 */
public class My_content extends BaseAvtivity implements CallBack {
    private RelativeLayout relativeLayout;
    private RelativeLayout relativeLayout_name;
    private RelativeLayout phoneNumberChange;
    private RelativeLayout addressChange;
    private TextView name;
    private TextView address;
    private TextView tvPhoneNumber;

    private String urlC;
    private String urlU;
    private String urlData;
    private String initDateTime;
    /* int request_code = 1;
     int request_code2 = 2;
     int request_code3 = 3;*/
    private IntentFilter filter;
    private MyReceiver mReceiver;
    private ImageView my_content_image;
    private AsyncImageLoader asyncImageLoader;
    private Bitmap bitmap;
    private String phoneNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.my_content);
        asyncImageLoader = new AsyncImageLoader(this);
        mReceiver = new MyReceiver();
        filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        name = (TextView) findViewById(R.id.my_content_inName);
        address = (TextView) findViewById(R.id.my_content_address);
        tvPhoneNumber = (TextView) findViewById(R.id.my_content_phonenumber);
        my_content_image = (ImageView) findViewById(R.id.my_content_image);
        relativeLayout = (RelativeLayout) findViewById(R.id.my_content_r2);
        relativeLayout_name = (RelativeLayout) findViewById(R.id.my_content_name);
        phoneNumberChange = (RelativeLayout) findViewById(R.id.my_content_phonenumber_relative);
        addressChange = (RelativeLayout) findViewById(R.id.my_content_r4);
        addressChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送数据
                Intent intent = new Intent();
                intent.putExtra("address", address.getText());
                intent.setClass(My_content.this, ChangeAddress.class);
                //My_content.this.startActivityForResult(intent, request_code2);
                My_content.this.startActivity(intent);
            }
        });

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(My_content.this, HeadPicture.class);
                if (bitmap == null) {
                    my_content_image.setDrawingCacheEnabled(true);
                    Bitmap bitmap = my_content_image.getDrawingCache();
                }
                intent.putExtra("imageBitmap", bitmap);
                startActivity(intent);
                my_content_image.setDrawingCacheEnabled(false);
            }
        });
        phoneNumberChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   //发送数据
                Intent intent = new Intent();
                intent.putExtra("phoneNumber", phoneNumber);
                intent.setClass(My_content.this, PhoneNumberChange.class);
                My_content.this.startActivity(intent);*/
            }
        });
        relativeLayout_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送数据
                Intent intent = new Intent();
                //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra("name", name.getText());
                intent.setClass(My_content.this, Edit_name.class);
                // My_content.this.startActivityForResult(intent, request_code);
                My_content.this.startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        registerReceiver(mReceiver, filter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }


    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            if (result.getDatalist().size() > 0) {
                User user = (User) result.getDatalist().get(0);
                if (!TextUtils.isEmpty(user.getUserHeadImage())) {
                    my_content_image.setTag(user.getUserHeadImage());
                    bitmap = asyncImageLoader.loadImage(my_content_image, user.getUserHeadImage());
                    if (bitmap != null) {
                        my_content_image.setImageBitmap(bitmap);
                    }
                }
                name.setText(user.getUserNickname());
                tvPhoneNumber.setText(user.getUserTelNum());
                address.setText(user.getDetailAddr());
            }
        }
    }

    @Override
    public CallBackPage doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(url);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            myContentServiceImpl Service = new myContentServiceImpl();
            return Service.getmyContentHeadInf(new String(bytes));
        }
        return null;
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            GlobalVeriable globalVeriable = ((GlobalVeriable) getApplication());
            if (!checkNetWorkStatus(My_content.this)) {
                setNetwork();
            } else {
                if (globalVeriable.getUserName() != null) {
                    Map args = new HashMap();
                    args.put("userName", globalVeriable.getUserName());
                    urlU = URLUtil.getURL("myInf", args);
                    Log.i("userurl", urlU);
                    new NetAsyncTask(My_content.this).execute(urlU);
                }
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
