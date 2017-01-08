package edu.buu.childhood.login.activity;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.baidumap.pojo.JoinedStatus;
import edu.buu.childhood.chat.bean.User;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.database.UserDao;
import edu.buu.childhood.database.UserLoginDao;
import edu.buu.childhood.login.service.LoginServiceImpl;
import edu.buu.childhood.mainui.activity.Main_interface;
import edu.buu.childhood.my.pojo.BackItem;
import edu.buu.childhood.my.pojo.UserLoginInfo;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.NewCallBack;
import edu.buu.childhood.util.NewNetAsyncTask;
import edu.buu.childhood.util.URLUtil;
import edu.buu.childhood.util.ValidateUtil;
import edu.buu.childhood.view.LoadDialog;

/**
 * Created by lcc on 2016/9/25.
 */
public class VerifyMessageLogin extends BaseAvtivity implements CallBack, NewCallBack {
    private EditText phone;
    private EditText vcode;
    private Button sendVcode;
    private Button SMSloginBtn;
    //发验证码
    private String urlPin;
    //登录
    private String urlLogin;
    //地理信息
    private Double Latitude;
    private Double Longitude;
    private String isRegisterUrl;
    private String isInGameUrl;
    private UserLoginDao loginDarabase;

    private Dialog loadingDialog;

    private TimeCount time;

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            sendVcode.setText("获取验证码");
            sendVcode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            sendVcode.setClickable(false);//防止重复点击
            sendVcode.setText(millisUntilFinished / 1000 + "秒");
        }
    }

    private Gson json = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.verify_login_message);
        time = new TimeCount(60000, 1000);
        loginDarabase = new UserLoginDao(this);
        //接收来自登录的地理位置信息
        Intent intent = getIntent();
        Latitude = intent.getDoubleExtra("lastLatitude", 0);
        Longitude = intent.getDoubleExtra("lastLongitude", 0);
        phone = (EditText) findViewById(R.id.SMSlogin_phone_num);
        vcode = (EditText) findViewById(R.id.SMSlogin_vcode);
        sendVcode = (Button) findViewById(R.id.SMSlogin_send_sms);
        sendVcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkNetWorkStatus(VerifyMessageLogin.this)) {
                    setNetwork();
                } else {
                    if (ValidateUtil.tel(phone.getText().toString())) {
                        Map map = new HashMap();
                        map.put("userName", phone.getText().toString());
                        isRegisterUrl = URLUtil.getURL("isRegistered", map);
                        new NewNetAsyncTask(VerifyMessageLogin.this).execute(isRegisterUrl);
                    } else {
                        Toast.makeText(getApplicationContext(), "手机号格式有误", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        SMSloginBtn = (Button) findViewById(R.id.SMSlogin_btn);
        SMSloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkNetWorkStatus(VerifyMessageLogin.this)) {
                    setNetwork();
                } else {
                    if (ValidateUtil.tel(phone.getText().toString()) && ValidateUtil.vcode(vcode.getText().toString())) {
                        if (Longitude == 4.9E-324 || Latitude == 4.9E-324) {
                            Toast.makeText(VerifyMessageLogin.this, "登录失败，请稍后再试", Toast.LENGTH_SHORT).show();
                        } else {
                            Map args = new HashMap();
                            args.put("userName", phone.getText().toString());
                            args.put("lastLatitude", Latitude);
                            args.put("lastLongitude", Longitude);
                            args.put("vcode", vcode.getText().toString());
                            urlLogin = URLUtil.getURL("SMSLogin", args);
                            new NewNetAsyncTask(VerifyMessageLogin.this).execute(urlLogin);
                            loadingDialog = showDialog(VerifyMessageLogin.this);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "验证码格式错误", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public Dialog showDialog(Context context) {
        Dialog dialog;
        dialog = new LoadDialog(context, R.style.Dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    @Override
    public void getResult(String jsonStr) {
        if (jsonStr != null) {
            Message reciveMsg = json.fromJson(jsonStr, new TypeToken<Message>() {
            }.getType());
            if (reciveMsg.getMessageCode() != null && reciveMsg.getMessageCode() instanceof String) {
                switch (reciveMsg.getMessageCode()) {
                    case C.LoginStatus.LOGIN_SUCCESS:
                        reciveMsg = json.fromJson(jsonStr, new TypeToken<Message<edu.buu.childhood.my.pojo.User>>() {
                        }.getType());
                        edu.buu.childhood.my.pojo.User userInfo = ((Message<edu.buu.childhood.my.pojo.User>) reciveMsg).getMessageContent();
                        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();

                        UserLoginInfo data = new UserLoginInfo();
                        data.setUserName(userInfo.getUserName());
                        data.setUserPassword(userInfo.getUserPwd());
                        data.setMyLatitude(String.valueOf(Latitude));
                        data.setMyLongitude(String.valueOf(Longitude));
                        data.setIsLogin(1);
                        if (loginDarabase.queryIsNull()) {
                            loginDarabase.deleteAll();
                        }
                        loginDarabase.SaveUserInfo(data);
                        ((GlobalVeriable) getApplication()).setUserName(userInfo.getUserName());
                        ((GlobalVeriable) getApplication()).setPassword(userInfo.getUserPwd());
                        ((GlobalVeriable) getApplication()).setLogin(true);
                        ((GlobalVeriable) getApplication()).setUserLevel(userInfo.getUserLevel());

                        if (userInfo.getUserId().equals(userInfo.getUserPwd())) {
                            ((GlobalVeriable) getApplication()).setPerfectInfo(false);
                        } else {
                            ((GlobalVeriable) getApplication()).setPerfectInfo(true);
                        }

                        User user = new User();
                        user.setUserName(userInfo.getUserName());
                        user.setUsernick(userInfo.getUserNickname());
                        user.setUserImage(userInfo.getUserHeadImage());
                        UserDao userDao = new UserDao(this);
                        if (userDao.isUserInfo(userInfo.getUserName())) {
                            userDao.updateUserInfo(user);
                        } else {
                            userDao.saveContact(user);
                        }

                        Map<String, String> map = new HashMap();
                        map.put("userName", userInfo.getUserName());
                        isInGameUrl = URLUtil.getURL("isInGame", map);
                        new NetAsyncTask(this).execute(isInGameUrl);

                        Intent intent = new Intent();
                        intent.setAction("CoreService");
                        intent.setPackage(getPackageName());

                        stopService(intent);

                        startService(intent);
                        startActivity(new Intent(VerifyMessageLogin.this, Main_interface.class));
                        finish();
                        break;
                    case C.LoginStatus.LOGIN_UNSUCCESS:
                        reciveMsg = json.fromJson(jsonStr, new TypeToken<String>() {
                        }.getType());
                        String messageContent = ((Message<String>) reciveMsg).getMessageContent();
                        if (C.LoginStatus.USER_NOT_EXIST.equals(messageContent)) {
                            Toast.makeText(VerifyMessageLogin.this, "用户不存在", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (C.vcode.VERIFIED_FAILURE.equals(messageContent)) {
                            Toast.makeText(VerifyMessageLogin.this, "验证码错误", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        break;
                    case C.vcode.SMS_SEND_SUCCESS:
                        Toast.makeText(VerifyMessageLogin.this, "短信已发送", Toast.LENGTH_SHORT).show();
                        break;
                    case C.vcode.SMS_SEND_ERROR:
                        Toast.makeText(VerifyMessageLogin.this, "不可以在一分钟内多次发送验证码", Toast.LENGTH_SHORT).show();
                        break;
                    case C.def.USER_IN_GAME:
                        reciveMsg = json.fromJson(jsonStr, new TypeToken<Message<JoinedStatus>>() {
                        }.getType());
                        JoinedStatus joinedStatus = ((Message<JoinedStatus>) reciveMsg).getMessageContent();
                        ((GlobalVeriable) getApplication()).setGameId(joinedStatus.getGameId());
                        ((GlobalVeriable) getApplication()).setGameRunning(true);
                        break;
                    case C.reg.USER_NOT_EXISTS:
                        Toast.makeText(VerifyMessageLogin.this, "手机号未注册", Toast.LENGTH_SHORT).show();
                        break;
                    case C.reg.USER_EXISTS:
                        Map<String, String> args = new HashMap();
                        args.put("telNum", String.valueOf(phone.getText()));
                        String sendSMSUrl = URLUtil.getURL("sendSms", args);
                        new NewNetAsyncTask(VerifyMessageLogin.this).execute(sendSMSUrl);
                        time.start();
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            if (result.getDatalist().size() > 0) {
                BackItem bean = (BackItem) result.getDatalist().get(0);
                if (C.vcode.SMS_SEND_ERROR.equals(bean.getIsright())) {
                    Toast.makeText(VerifyMessageLogin.this, "您不可在一分钟内发送多次验证码", Toast.LENGTH_SHORT).show();
                }
                if (!bean.getInGame()) {
                    if (C.LoginStatus.LOGIN_SUCCESS.equals(bean.getIsright())) {
                        if (bean.getIsright().equals(C.LoginStatus.LOGIN_SUCCESS)) {
                            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                            UserLoginInfo data = new UserLoginInfo();
                            if (loginDarabase.queryIsNull()) {
                                loginDarabase.deleteAll();
                            }
                            loginDarabase.SaveUserInfo(data);
                            ((GlobalVeriable) getApplication()).setLogin(true);
                            loginSuccess(bean);
                            Map map = new HashMap();
                            map.put("userName", bean.getUser().getUserName());
                            isInGameUrl = URLUtil.getURL("isInGame", map);
                            new NetAsyncTask(this).execute(isInGameUrl);
                            Intent intent = new Intent();
                            intent.setAction("CoreService");
                            intent.setPackage(getPackageName());
                            startService(intent);
                            startActivity(new Intent(VerifyMessageLogin.this, Main_interface.class));
                            finish();
                        }
                    }
                } else {
                    if (bean.getIsright().equals(C.def.USER_IN_GAME)) {
                        //在游戏中，初始化gameId，是否是创建者
                        ((GlobalVeriable) getApplication()).setGameId(bean.getGameId());
                        ((GlobalVeriable) getApplication()).setGameRunning(true);
                        Intent intent = new Intent();
                        intent.setAction("CoreService");
                        intent.setPackage(getPackageName());
                        startService(intent);
                    }
                }
                if ("user_not_exist".equals(bean.getIsright())) {
                    Toast.makeText(VerifyMessageLogin.this, "您还没有注册哦", Toast.LENGTH_SHORT).show();
                }
                if (C.vcode.VERIFIED_FAILURE.equals(bean.getIsright())) {
                    Toast.makeText(VerifyMessageLogin.this, "验证码错误", Toast.LENGTH_SHORT).show();
                }
                if (C.reg.USER_EXISTS.equals(bean.getIsright())) {
                    Map args = new HashMap();
                    args.put("telNum", phone.getText());
                    urlPin = URLUtil.getURL("sendSms", args);
                    new NetAsyncTask(VerifyMessageLogin.this).execute(urlPin);
                }
                if (C.reg.USER_NOT_EXISTS.equals(bean.getIsright())) {
                    Toast.makeText(VerifyMessageLogin.this, "用户不存在", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * 新回调函数，传入url数组，一般情况只传入一个，即url[0]
     * 为兼容旧回调函数故做此处理
     * 2016/11/03
     *
     * @param url 所传入url数组
     * @return 返回请求API接口所得到的json数据
     */
    @Override
    public String doInBackground(String... url) {
        HttpUtil httpUtil = new HttpUtil(url[0]);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            return new String(bytes);
        }
        return null;
    }

    @Override
    public CallBackPage doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(url);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            if (url.equals(urlLogin)) {
                LoginServiceImpl Service = new LoginServiceImpl();
                return Service.getLoginHeadInf(new String(bytes));
            } else if (url.equals(urlPin)) {
                LoginServiceImpl Service = new LoginServiceImpl();
                return Service.getLoginHeadInf(new String(bytes));
            } else {
                LoginServiceImpl Service = new LoginServiceImpl();
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

    public void loginSuccess(BackItem backItem) {
        User user = new User();
        user.setUserName(backItem.getUser().getUserName());
        user.setUsernick(backItem.getUser().getUserNickname());
        user.setUserImage(backItem.getUser().getUserHeadImage());
        UserDao userDao = new UserDao(this);
        if (userDao.isUserInfo(backItem.getUser().getUserName())) {
            userDao.updateUserInfo(user);
        } else {
            userDao.saveContact(user);
        }
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
}
