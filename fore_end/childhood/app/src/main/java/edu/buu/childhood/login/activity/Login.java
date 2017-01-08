package edu.buu.childhood.login.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;

import java.util.HashMap;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.chat.bean.User;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.database.UserDao;
import edu.buu.childhood.database.UserLoginDao;
import edu.buu.childhood.login.service.LoginServiceImpl;
import edu.buu.childhood.mainui.activity.Main_interface;
import edu.buu.childhood.my.pojo.BackItem;
import edu.buu.childhood.my.pojo.UserInfo;
import edu.buu.childhood.my.pojo.UserLoginInfo;
import edu.buu.childhood.register.activity.VerifyRegister;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.URLUtil;
import edu.buu.childhood.util.ValidateUtil;
import edu.buu.childhood.view.LoadDialog;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;


/**
 * Created by lcc on 2016/6/20.
 */


@RuntimePermissions
public class Login extends BaseAvtivity implements CallBack, View.OnClickListener {
    private EditText username;
    private EditText password;
    private String url;
    private Button userLogin;
    private UserLoginDao loginDarabase;
    private ProgressDialog dialog;
    private LocationClient mLocationClient;
    private double mLatitude;
    private double mLongitude;
    private String isInGameUrl;
    private MyReceiver mReceiver;
    private IntentFilter filter;
    private String getUserInfoUrl;
    private TextView resetPassword;
    private TextView messageLogin;

    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.login);
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //注册广播
        mReceiver = new MyReceiver();
        filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
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
        //获得xml数据
        username = (EditText) findViewById(R.id.userNameText);
        password = (EditText) findViewById(R.id.passwdText);
        userLogin = (Button) findViewById(R.id.loginButton);
        userLogin.setOnClickListener(this);
        loginDarabase = new UserLoginDao(this);
        resetPassword = (TextView) findViewById(R.id.login_reset_password);
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, ResetPassword.class));
            }
        });
        messageLogin = (TextView) findViewById(R.id.messageLogin);
        messageLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("lastLatitude", mLatitude);
                intent.putExtra("lastLongitude", mLongitude);
                intent.setClass(Login.this, VerifyMessageLogin.class);
                startActivity(intent);
            }
        });

    }

    public void onClick(View v) {
        //LoginPermissionsDispatcher.requestWithCheck(this);
        if (!checkNetWorkStatus(this) && !isOpenGps()) {
            if (!checkNetWorkStatus(this)) {
                setNetwork();
                return;
            }
            if (!isOpenGps()) {
                initGPS();
                return;
            }
        } else {
            if (!ValidateUtil.tel(username.getText().toString())) {
                Toast.makeText(this, "用户名格式有误", Toast.LENGTH_LONG).show();
                return;
            }
            if (!ValidateUtil.password(password.getText().toString())) {
                Toast.makeText(this, "密码格式有误", Toast.LENGTH_LONG).show();
                return;
            }
            if (mLongitude == 4.9E-324 || mLatitude == 4.9E-324) {
                Toast.makeText(this, "请设置位置权限，方便查看周边伙伴", Toast.LENGTH_SHORT).show();
            } else {
                Map args = new HashMap();
                args.put("userName", username.getText());
                args.put("userPwd", password.getText());
                args.put("lastLatitude", mLatitude);
                args.put("lastLongitude", mLongitude);
                url = URLUtil.getURL("login", args);
                new NetAsyncTask(this).execute(url);
                loadingDialog = showDialog(this);
            }
        }
    }

    public Dialog showDialog(Context context) {
        Dialog dialog;
        dialog = new LoadDialog(context, R.style.Dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    public void registerClick(View view) {
        Intent intent = new Intent();
        intent.putExtra("lastLatitude", mLatitude);
        intent.putExtra("lastLongitude", mLongitude);
        intent.setClass(Login.this, VerifyRegister.class);
        startActivity(intent);
    }

    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            if (!"userInfo".equals(result.getFalg())) {
                BackItem bean = (BackItem) result.getDatalist().get(0);
                if (!bean.getInGame()) {
                    if (bean.getIsright().equals(C.LoginStatus.LOGIN_SUCCESS)) {
                        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                        UserLoginInfo data = new UserLoginInfo();
                        data.setUserName(username.getText().toString());
                        data.setUserPassword(password.getText().toString());
                        data.setMyLatitude(String.valueOf(mLatitude));
                        data.setMyLongitude(String.valueOf(mLongitude));
                        data.setIsLogin(1);
                        if (loginDarabase.queryIsNull()) {
                            loginDarabase.deleteAll();
                        }
                        loginDarabase.SaveUserInfo(data);
                        ((GlobalVeriable) getApplication()).setUserName(username.getText().toString());
                        ((GlobalVeriable) getApplication()).setPassword(password.getText().toString());
                        ((GlobalVeriable) getApplication()).setLogin(true);
                        ((GlobalVeriable) getApplication()).setUserLevel(bean.getUser().getUserLevel());
                        if (bean.getUser().getUserId().equals(bean.getUser().getUserPwd())) {
                            ((GlobalVeriable) getApplication()).setPerfectInfo(false);
                        } else {
                            ((GlobalVeriable) getApplication()).setPerfectInfo(true);
                        }
                        Map userMap = new HashMap();
                        userMap.put("userName", ((GlobalVeriable) getApplication()).getUserName());
                        userMap.put("targetUser", username.getText().toString());
                        getUserInfoUrl = URLUtil.getURL("getUserInfo", userMap);
                        new NetAsyncTask(this).execute(getUserInfoUrl);
                        Map map = new HashMap();
                        map.put("userName", username.getText().toString());
                        isInGameUrl = URLUtil.getURL("isInGame", map);
                        new NetAsyncTask(this).execute(isInGameUrl);
                        Intent intent = new Intent();
                        intent.setAction("CoreService");
                        intent.setPackage(getPackageName());

                        stopService(intent);

                        startService(intent);
                        startActivity(new Intent(Login.this, Main_interface.class));
                        finish();
                    } else if (bean.getIsright().equals(C.reg.USER_NOT_EXISTS)) {
                        Toast.makeText(this, "用户不存在", Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                    } else if (bean.getIsright().equals(C.LoginStatus.LOGIN_UNSUCCESS)) {
                        Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                    } else {
                        Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                    }
                } else {
                    if (bean.getIsright().equals(C.def.USER_IN_GAME)) {
                        //在游戏中，初始化gameId，是否是创建者
                        ((GlobalVeriable) getApplication()).setGameId(bean.getGameId());
                        ((GlobalVeriable) getApplication()).setGameRunning(true);
//                        Intent intent = new Intent();
//                        intent.setAction("CoreService");
//                        intent.setPackage(getPackageName());
//                        startService(intent);
                    }
                }
            } else {
                UserInfo userInf = (UserInfo) result.getDatalist().get(0);
                User user = new User();
                user.setUserName(userInf.getUserName());
                user.setUsernick(userInf.getUserNickname());
                user.setUserImage(userInf.getUserHeadImage());
                UserDao userDao = new UserDao(this);
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
            LoginServiceImpl Service = new LoginServiceImpl();
            if (url.equals(isInGameUrl)) {
                return Service.getLoginHeadInf(new String(bytes));
            } else if (url.equals(getUserInfoUrl)) {
                return Service.getUserInfo(new String(bytes));
            } else {
                return Service.getLoginHeadInf(new String(bytes));
            }
        }
        return null;
    }

    /*网络连接检测*/
    private boolean checkNetWorkStatus(Context context) {
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
                if (android.os.Build.VERSION.SDK_INT > 10) {
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

    //初始化定位监听
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mLocationClient.isStarted()) {
            mLocationClient.start();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //LoginPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);//将回调交给代理类处理
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
    protected void onStop() {
        super.onStop();
        mLocationClient.stop();
    }

    private Boolean isOpenGps() {
        //0 isPermission();
        LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        // 判断GPS模块是否开启，如果没有则开启
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        } else {
            return false;
        }
    }

    /* 监听GPS
    */
    private void initGPS() {
        LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        // 判断GPS模块是否开启，如果没有则开启
        if (!locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        }
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!checkNetWorkStatus(Login.this)) {
                setNetwork();
            }
            if (!isOpenGps()) {
                initGPS();
            }
        }
    }

    @OnShowRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
//申请前告知用户为什么需要该权限
    void showRationaleForCamera(PermissionRequest request) {
        showRationaleDialog("使用此功能需要开启定位，寻找附近小伙伴", request);
    }

    /**
     * 告知用户具体需要权限的原因
     *
     * @param messageResId
     * @param request
     */
    void showRationaleDialog(String messageResId, final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();//请求权限
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(messageResId)
                .show();
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_COARSE_LOCATION)
//被拒绝
    void onCameraDenied() {
        Toast.makeText(this, "请到设置中开启定位功能", Toast.LENGTH_LONG).show();
    }

    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    public void request() {

    }
}
