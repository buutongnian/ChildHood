package edu.buu.childhood.mainui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.database.UserDao;
import edu.buu.childhood.database.UserLoginDao;
import edu.buu.childhood.login.service.LoginServiceImpl;
import edu.buu.childhood.my.pojo.BackItem;
import edu.buu.childhood.my.pojo.User;
import edu.buu.childhood.my.pojo.UserLoginInfo;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.CreateDatabase;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.URLUtil;

/**
 * Created by lcc on 2016/6/12.
 */
public class MainActivity extends BaseAvtivity implements CallBack {

    private IWXAPI api;

    private UserLoginDao getLoginInf;
    private String name;
    private String password;
    private String url;
    private Double mylatitude;
    private Double mylongitude;
    private String isInGameUrl;
    private LocationClient mLocationClient;
    private Double mLatitude;
    private Double mLongitude;
    private CreateDatabase createDatabase = new CreateDatabase();

    private boolean isPerfectInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        CloseActivity.activityList.add(this);
        //定位
        SDKInitializer.initialize(getApplicationContext());
        mLocationClient = new LocationClient(this);
        MyLocationListener listener = new MyLocationListener();
        mLocationClient.registerLocationListener(listener);
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        mLocationClient.setLocOption(option);

        //实例化数据库
        getLoginInf = new UserLoginDao(this);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SharedPreferences preferences = getSharedPreferences("count", MODE_WORLD_READABLE);
        int count = preferences.getInt("count", 0);


        //记录是否屏蔽一键呼喊
        File shield = new File("/data/data/<package name>/shared_prefs/shield.xml");
        //判断程序与第几次运行，如果是第一次运行则跳转到引导页面
        if (count == 0) {
            createDatabase.copyDatabaseFile(this);
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), GuidePage.class);
            startActivity(intent);
            this.finish();
        }
        //否则进入欢迎页，跳到主页面
        else {
            PackageManager pm = getPackageManager();
            try {
                PackageInfo pi = pm.getPackageInfo("edu.buu.childhood", 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    GlobalVeriable globalVeriable = (GlobalVeriable) getApplication();
                    UserLoginInfo info = getLoginInf.getLoginUser();
                    if (info != null) {
                        name = info.getUserName();
                        password = info.getUserPassword();
                        mylatitude = Double.parseDouble(info.getMyLatitude());
                        mylongitude = Double.parseDouble(info.getMyLongitude());
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    getData();
                }
            }, 0);
        }
        SharedPreferences.Editor editor = preferences.edit();
        //存入数据
        editor.putInt("count", ++count);
        //提交修改
        editor.commit();
    }

    private void regToWx() {
        api = WXAPIFactory.createWXAPI(this, C.WX_APP_ID, true);
        api.registerApp(C.WX_APP_ID);
    }

    @Override
    protected void onStart() {
        if (!mLocationClient.isStarted()) {
            mLocationClient.start();
        }
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //unregisterReceiver(mReceiver);
    }

    @Override
    protected void onStop() {
        mLocationClient.stop();
        super.onStop();
    }

    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            GlobalVeriable globalVeriable = (GlobalVeriable) getApplication();
            BackItem bean = (BackItem) result.getDatalist().get(0);
            if (!bean.getInGame()) {
                if (bean.getIsright().equals(C.LoginStatus.LOGIN_SUCCESS)) {
                    globalVeriable.setLogin(true);
                    globalVeriable.setUserName(name);
                    globalVeriable.setPassword(password);
                    updateUserInfo(bean.getUser());
                    globalVeriable.setUserLevel(bean.getUser().getUserLevel());
                    if (bean.getUser().getUserId().equals(bean.getUser().getUserPwd())) {
                        ((GlobalVeriable) getApplication()).setPerfectInfo(false);
                    } else {
                        ((GlobalVeriable) getApplication()).setPerfectInfo(true);
                    }
                    Intent intent = new Intent(MainActivity.this, Main_interface.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                    Map map = new HashMap();
                    map.put("userName", name);
                    isInGameUrl = URLUtil.getURL("isInGame", map);
                    new NetAsyncTask(this).execute(isInGameUrl);
                    Intent intentSevice = new Intent();
                    intentSevice.setAction("CoreService");
                    intentSevice.setPackage(getPackageName());

                    startService(intentSevice);
                }
            } else {
                if (bean.getIsright().equals(C.def.USER_IN_GAME)) {
                    //在游戏中，初始化gameId，是否是创建者
                    ((GlobalVeriable) getApplication()).setGameId(bean.getGameId());
                    ((GlobalVeriable) getApplication()).setGameRunning(true);
                }
            }
        }
    }

    public CallBackPage doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(url);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            LoginServiceImpl Service = new LoginServiceImpl();
            if (url.equals(isInGameUrl)) {
                return Service.getLoginHeadInf(new String(bytes));
            } else {
                return Service.getLoginHeadInf(new String(bytes));
            }
        }
        return null;
    }

    private void getData() {
        if (!checkNetWorkStatus(MainActivity.this)) {
            Intent intent = new Intent(MainActivity.this, Main_interface.class);
            startActivity(intent);
            MainActivity.this.finish();
        } else {
            if (name != null) {
                Map args = new HashMap();
                args.put("userName", name);
                args.put("userPwd", password);
                if (mLatitude != null || mLongitude != null) {
                    args.put("lastLatitude", mLatitude);
                    args.put("lastLongitude", mLongitude);
                } else {
                    args.put("lastLatitude", mylatitude);
                    args.put("lastLongitude", mylongitude);
                }
                url = URLUtil.getURL("login", args);
                new NetAsyncTask(MainActivity.this).execute(url);
            } else {
                Intent intent = new Intent(MainActivity.this, Main_interface.class);
                startActivity(intent);
                MainActivity.this.finish();
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

    //初始化定位监听
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();
        }
    }


    public void updateUserInfo(User user) {
        UserDao userDao = new UserDao(this);
        userDao.saveUser(user);
    }
}
