package edu.buu.childhood.my.activity;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.database.UserLoginDao;
import edu.buu.childhood.login.activity.Login;
import edu.buu.childhood.mainui.activity.Main_interface;
import edu.buu.childhood.my.DownLoadManager;
import edu.buu.childhood.my.pojo.UserLoginInfo;
import edu.buu.childhood.my.pojo.VersionInfo;
import edu.buu.childhood.my.service.VersionServiceImpl;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.SmackManager;
import edu.buu.childhood.util.URLUtil;

/**
 * Created by lcc on 2016/10/7.
 */
public class Set extends BaseAvtivity implements CallBack {
    private String version;
    private char enable;
    private String url;
    private RelativeLayout update;
    private String localVersion;
    private String packageUrl;
    private RelativeLayout aboutUs;
    private RelativeLayout exit;
    private RelativeLayout feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.set);
        update = (RelativeLayout) findViewById(R.id.setUpdate);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    localVersion = getVersionName();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!checkNetWorkStatus(Set.this)) {
                    setNetwork();
                } else {

                    Map args = new HashMap();
                    args.put("system", "Android");
                    url = URLUtil.getURL("getLeastVersion", args);
                    new NetAsyncTask(Set.this).execute(url);
                }
            }
        });
        exit = (RelativeLayout) findViewById(R.id.set_exit);
        RelativeLayout relativeLayoutLogin = (RelativeLayout) findViewById(R.id.setLogin);
        if (!((GlobalVeriable) getApplication()).getLogin()) {
            //登录跳转
            assert relativeLayoutLogin != null;
            relativeLayoutLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Set.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            });
            exit.setVisibility(View.GONE);
        } else {
            assert relativeLayoutLogin != null;
            relativeLayoutLogin.setVisibility(View.GONE);
        }
        //退出
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVeriable globalVeriable = (GlobalVeriable) getApplication();
                if (globalVeriable.getLogin()) {
                    SmackManager.getInstance().logout();
                    Intent intent = new Intent();
                    intent.setAction("CoreService");
                    intent.setPackage(getPackageName());
                    stopService(intent);

                    UserLoginDao userLoginDao = new UserLoginDao(Set.this);
                    userLoginDao.UpdateUserLoginInfo(globalVeriable.getUserName(), 0);

                    globalVeriable.setLogin(false);
                    globalVeriable.setUserName(null);
                    globalVeriable.setPassword(null);
                    Intent loginExit = new Intent(Set.this, Main_interface.class);
                    startActivity(loginExit);
                    CloseActivity.exitOthers(Set.this);
                    finish();
                }
            }
        });
        aboutUs = (RelativeLayout) findViewById(R.id.setAboutUs);
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Set.this, AboutUs.class));
            }
        });
        feedback = (RelativeLayout) findViewById(R.id.set_feedback);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Set.this, Feedback.class));
            }
        });
    }

    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {

            VersionInfo bean = (VersionInfo) result.getDatalist().get(0);
            version = bean.getVersion();
            packageUrl = bean.getPackageUrl();
            enable = bean.getEnable();
            if (version.equals(localVersion)) {
                //对话框通知用户升级程序
                showUpdataDialog();
            } else {
                Toast.makeText(getApplicationContext(), "不需要更新",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


    public CallBackPage doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(url);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            {
                VersionServiceImpl Service = new VersionServiceImpl();
                return Service.getVersionHeadInf(new String(bytes));
            }
        }
        return null;
    }

    private String getVersionName() throws Exception {
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageManager packageManager = getPackageManager();
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),
                0);
        return packInfo.versionName;
    }

    /*
    *
    * 弹出对话框通知用户更新程序
    *
    * 弹出对话框的步骤：
    *  1.创建alertDialog的builder.
    *  2.要给builder设置属性, 对话框的内容,样式,按钮
    *  3.通过builder 创建一个对话框
    *  4.对话框show()出来
    */
    protected void showUpdataDialog() {
        AlertDialog.Builder builer = new AlertDialog.Builder(this);
        builer.setTitle("版本升级");
        builer.setMessage(version);
        //当点确定按钮时从服务器上下载 新的apk 然后安装   װ
        builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.i("123", "下载apk,更新");
                downLoadApk();
            }
        });
        builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                //do sth
            }
        });
        AlertDialog dialog = builer.create();
        dialog.show();
    }

    /*
     * 从服务器中下载APK
     */
    protected void downLoadApk() {
        final ProgressDialog pd;    //进度条对话框
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = DownLoadManager.getFileFromServer(packageUrl, pd);
                    sleep(3000);
                    installApk(file);
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "获取服务器更新信息失败", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //安装apk
    protected void installApk(File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
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
