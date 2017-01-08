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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.achievement.activity.AchievementActivity;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.database.UserDao;
import edu.buu.childhood.forum.someWebview;
import edu.buu.childhood.login.activity.Login;
import edu.buu.childhood.my.pojo.User;
import edu.buu.childhood.my.service.MyInfoServiceImpl;
import edu.buu.childhood.util.AsyncImageLoader;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.URLUtil;

public class My extends BaseAvtivity implements CallBack, View.OnClickListener {

    private LinearLayout userInfoBlockLogedin;
    private LinearLayout userInfoBlockNotLogedin;

    private RelativeLayout relativeLayout;
    private RelativeLayout chengjiu;
    private RelativeLayout parentLock;
    private RelativeLayout myForum;
    private String parentPassword;
    private MyReceiver mReceiver;
    private IntentFilter filter;
    private String userHeadInfoUrl;
    private TextView my_user_nickname;
    private TextView my_username;
    private ImageView my_info_image;
    private AsyncImageLoader asyncImageLoader;
    private RelativeLayout set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.my);

        mReceiver = new MyReceiver();
        filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");

        userInfoBlockLogedin = (LinearLayout) findViewById(R.id.my_user_info);
        userInfoBlockNotLogedin = (LinearLayout) findViewById(R.id.my_login_register);

        if (((GlobalVeriable) getApplication()).getLogin()) {
            userInfoBlockLogedin.setVisibility(View.VISIBLE);
            userInfoBlockNotLogedin.setVisibility(View.GONE);
        } else {
            userInfoBlockLogedin.setVisibility(View.GONE);
            userInfoBlockNotLogedin.setVisibility(View.VISIBLE);
        }

        myForum = (RelativeLayout) findViewById(R.id.my_forum);
        myForum.setOnClickListener(this);
        asyncImageLoader = new AsyncImageLoader(this);
        parentLock = (RelativeLayout) findViewById(R.id.my_parent_center);
        parentLock.setOnClickListener(this);
        my_user_nickname = (TextView) findViewById(R.id.my_user_nickname);
        my_username = (TextView) findViewById(R.id.my_username);
        my_info_image = (ImageView) findViewById(R.id.my_info_image);
        relativeLayout = (RelativeLayout) findViewById(R.id.my_block_user_info);
        relativeLayout.setOnClickListener(this);
        chengjiu = (RelativeLayout) findViewById(R.id.my_myr4);
        chengjiu.setOnClickListener(this);
        set = (RelativeLayout) findViewById(R.id.mySet);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(My.this, Set.class));
            }
        });
        getUserInfo();
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
    public void onClick(View v) {
        GlobalVeriable globalVeriable = (GlobalVeriable) getApplication();
        if (!checkNetWorkStatus(this)) {
            setNetwork();
        } else {
            if (globalVeriable.getLogin()) {
                switch (v.getId()) {
                    case R.id.my_block_user_info:
                        startActivity(new Intent(My.this, My_content.class));
                        break;
                    case R.id.my_myr4:
                        startActivity(new Intent(My.this, AchievementActivity.class));
                        break;
                    case R.id.my_parent_center:
                        File file = new File(getFilesDir(), ((GlobalVeriable) getApplication()).getUserName());
                        //如果文件存在，则读取
                        if (file.exists()) {
                            try {
                                FileInputStream fin = new FileInputStream(file);
                                //把字节流转化为字符流
                                BufferedReader buffer = new BufferedReader(new InputStreamReader(fin));
                                //读取文件中的用户名和密码
                                parentPassword = buffer.readLine();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent();
                            intent.putExtra("pw", parentPassword);
                            intent.setClass(My.this, ParentLock.class);
                            My.this.startActivity(intent);
                        } else {
                            setParentPassword();
                        }
                        break;
                    case R.id.my_forum:
                        startActivity(new Intent(My.this, someWebview.class));
                        break;
                    default:
                        break;
                }
            } else {
                if (v.getId() == R.id.my_block_user_info) {
                    startActivity(new Intent(My.this, Login.class));
                } else {
                    Toast.makeText(getApplication(), "您还未登录", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            if ("MyHeadInfo".equals(result.getFalg())) {
                User userInf = (User) result.getDatalist().get(0);
                my_user_nickname.setText(userInf.getUserNickname());
                my_username.setText(userInf.getUserName());
                String imageUrl = userInf.getUserHeadImage();
                if (!TextUtils.isEmpty(imageUrl)) {
                    my_info_image.setTag(imageUrl);
                    Bitmap bitmap = asyncImageLoader.loadImage(my_info_image, imageUrl);
                    if (bitmap != null) {
                        my_info_image.setImageBitmap(bitmap);
                    }
                }
                UserDao userDao = new UserDao(this);
                edu.buu.childhood.chat.bean.User user = new edu.buu.childhood.chat.bean.User();
                user.setUserName(userInf.getUserName());
                user.setNick(userInf.getUserNickname());
                user.setUserImage(userInf.getUserHeadImage());
                user.setTel(userInf.getUserTelNum());
                if (userDao.isUserInfo(userInf.getUserName())) {
                    userDao.updateUserInfo(user);
                } else {
                    userDao.saveContact(user);
                }
            }
        }
    }

    public CallBackPage doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(url);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            MyInfoServiceImpl service = new MyInfoServiceImpl();
            return service.getMyHeadInfo(new String(bytes));
        }
        return null;
    }

    //广播
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            GlobalVeriable globalVeriable = ((GlobalVeriable) getApplication());
            if (!checkNetWorkStatus(My.this)) {
                setNetwork();
            } else {
                if (globalVeriable.getLogin()) {
                    Map map = new HashMap();
                    map.put("userName", globalVeriable.getUserName());
                    userHeadInfoUrl = URLUtil.getURL("myInf", map);
                    new NetAsyncTask(My.this).execute(userHeadInfoUrl);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
    }

    private void setParentPassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.logo);
        builder.setTitle("提示");
        builder.setMessage("请设置家长监控密码");
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(My.this, ChangePassword.class));
                dialog.dismiss();
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

    public void getUserInfo() {
        GlobalVeriable globalVeriable = (GlobalVeriable) getApplication();
        if (globalVeriable.getLogin()) {
            UserDao userDao = new UserDao(this);
            edu.buu.childhood.chat.bean.User user = userDao.getUserInfo(globalVeriable.getUserName());
            if (user != null) {
                my_user_nickname.setText(user.getNick());
                my_username.setText(user.getUserName());
                String imageUrl = user.getUserImage();
                if (!TextUtils.isEmpty(imageUrl)) {
                    my_info_image.setTag(imageUrl);
                    Bitmap bitmap = asyncImageLoader.loadImage(my_info_image, imageUrl);
                    if (bitmap != null) {
                        my_info_image.setImageBitmap(bitmap);
                    }
                }
            }
        }
    }
}
