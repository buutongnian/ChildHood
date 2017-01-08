package edu.buu.childhood.register.activity;

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

import java.util.HashMap;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.my.pojo.BackItem;
import edu.buu.childhood.register.service.VerifyRegisterServiceImpl;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.URLUtil;
import edu.buu.childhood.util.ValidateUtil;

/**
 * Created by lcc on 2016/9/25.
 */
public class VerifyRegister extends BaseAvtivity implements CallBack {
    private EditText phone;
    private EditText pin;
    private Button sendPin;
    private Button register;
    private String urlPin;//发送验证码
    private String urlNext;//用户注册
    private String urlPhoneIsReg;
    private String urlCheckVcode;
    private Double Latitude;
    private Double Longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.verify_register);
        //接收来自登录的地理位置信息
        Intent intent = getIntent();
        Latitude = intent.getDoubleExtra("lastLatitude", 0);
        Longitude = intent.getDoubleExtra("lastLongitude", 0);
        phone = (EditText) findViewById(R.id.verifyRegPhone);
        pin = (EditText) findViewById(R.id.verifyRegPIN);
        sendPin = (Button) findViewById(R.id.verifyRegSendPin);
        register = (Button) findViewById(R.id.verifyRegFinishPIN);
        sendPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkNetWorkStatus(VerifyRegister.this)) {
                    setNetwork();
                } else {
                    if (ValidateUtil.tel(phone.getText().toString())) {
                        Map args = new HashMap();
                        args.put("userName", phone.getText());
                        urlPhoneIsReg = URLUtil.getURL("isRegistered", args);
                        Log.i("check", urlPhoneIsReg);
                        new NetAsyncTask(VerifyRegister.this).execute(urlPhoneIsReg);
                    } else {
                        Toast.makeText(getApplicationContext(), "手机号不正确", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkNetWorkStatus(VerifyRegister.this)) {
                    setNetwork();
                } else {
                    if (ValidateUtil.tel(phone.getText().toString()) && ValidateUtil.vcode(pin.getText().toString())) {
                        Map args = new HashMap();
                        args.put("userName", phone.getText());
                        args.put("vcode", pin.getText().toString());
                        args.put("regLatitude", Latitude);
                        args.put("regLongitude", Longitude);
                        urlNext = URLUtil.getURL("register", args);
                        Log.i("register", urlNext);
                        new NetAsyncTask(VerifyRegister.this).execute(urlNext);
                    }
                }
            }
        });
    }

    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            if (result.getDatalist().size() > 0) {
                BackItem bean = (BackItem) result.getDatalist().get(0);
                if (C.reg.USER_EXISTS.equals(bean.getIsright())) {
                    Toast.makeText(VerifyRegister.this, "手机号已注册", Toast.LENGTH_SHORT).show();
                }
                if (C.reg.USER_NOT_EXISTS.equals(bean.getIsright())) {
                    if (ValidateUtil.tel(phone.getText().toString())) {
                        Map args = new HashMap();
                        args.put("telNum", phone.getText().toString());
                        urlPin = URLUtil.getURL("sendSms", args);
                        new NetAsyncTask(VerifyRegister.this).execute(urlPin);
                    }
                }
                if (C.vcode.SMS_SEND_SUCCESS.equals(bean.getIsright())) {
                    TimeCount time = new TimeCount(60000, 1000);
                    time.start();
                    Toast.makeText(VerifyRegister.this, "短信已发送", Toast.LENGTH_SHORT).show();
                }
                if (C.vcode.SMS_SEND_ERROR.equals(bean.getIsright())) {
                    Toast.makeText(VerifyRegister.this, "不可以在一分钟内多次发送验证码", Toast.LENGTH_SHORT).show();
                }
                //注册成功
                if (C.reg.REG_SUCCESS.equals(bean.getIsright())) {
                    //UserLoginInfo userLoginInfo = new UserLoginInfo();
                    Intent intent = new Intent();
                    intent.putExtra("userName", phone.getText().toString());
                    intent.setClass(VerifyRegister.this, PerfectInfo.class);
                    startActivity(intent);
                    finish();
                }
                if (C.vcode.VERIFIED_FAILURE.equals(bean.getIsright())) {
                    Toast.makeText(VerifyRegister.this, "验证码错误", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public CallBackPage doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(url);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            if (url.equals(urlPhoneIsReg)) {
                VerifyRegisterServiceImpl Service = new VerifyRegisterServiceImpl();
                return Service.getmyContentHeadInf(new String(bytes));

            } else if (url.equals(urlPin)) {
                VerifyRegisterServiceImpl Service = new VerifyRegisterServiceImpl();
                return Service.getmyContentHeadInf(new String(bytes));
            } else if (url.equals(urlCheckVcode)) {
                VerifyRegisterServiceImpl Service = new VerifyRegisterServiceImpl();
                return Service.getmyContentHeadInf(new String(bytes));
            } else if (url.equals(urlNext)) {
                VerifyRegisterServiceImpl Service = new VerifyRegisterServiceImpl();
                return Service.getmyContentHeadInf(new String(bytes));
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

            }
        });
        builder.create();
        builder.show();
    }


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            sendPin.setText("重新验证");
            sendPin.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            sendPin.setClickable(false);
            sendPin.setText(millisUntilFinished / 1000 + "秒");
        }
    }
}
