package edu.buu.childhood.login.activity;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.my.pojo.BackItem;
import edu.buu.childhood.register.service.VerifyRegisterServiceImpl;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.NewCallBack;
import edu.buu.childhood.util.NewNetAsyncTask;
import edu.buu.childhood.util.URLUtil;
import edu.buu.childhood.util.ValidateUtil;

/**
 * Created by lcc on 2016/9/25.
 */
public class ResetPassword extends BaseAvtivity implements CallBack, NewCallBack {
    private EditText phone;
    private EditText vcode;
    private Button sendSMS;
    private Button btnNext;
    //验证手机号是否存在
    private String urlPhoneIsReg;
    //进入修改界面
    private String urlCheckVcode;
    //发验证码
    private String urlPin;
    private String userName;

    private TimeCount time;

    private Gson json = new Gson();

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            sendSMS.setText("获取验证码");
            sendSMS.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            sendSMS.setClickable(false);//防止重复点击
            sendSMS.setText(millisUntilFinished / 1000 + "秒");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        time = new TimeCount(60000, 1000);
        if (((GlobalVeriable) getApplication()).getLogin())
            userName = ((GlobalVeriable) getApplication()).getUserName();

        setContentView(R.layout.verify_message);
        phone = (EditText) findViewById(R.id.verifyMessPhone);
        vcode = (EditText) findViewById(R.id.verifyMessPIN);
        sendSMS = (Button) findViewById(R.id.verifyMessSendPin);
        sendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkNetWorkStatus(ResetPassword.this)) {
                    setNetwork();
                } else {
                    if (ValidateUtil.tel(userName)) {
                        Map args = new HashMap();
                        args.put("userName", userName);
                        urlPhoneIsReg = URLUtil.getURL("isRegistered", args);
                        new NewNetAsyncTask(ResetPassword.this).execute(urlPhoneIsReg);
                    }
                }
            }
        });
        btnNext = (Button) findViewById(R.id.reset_password_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkNetWorkStatus(ResetPassword.this)) {
                    setNetwork();
                } else {
                    if (ValidateUtil.tel(userName) && ValidateUtil.vcode(vcode.getText().toString())) {
                        Map args = new HashMap();
                        args.put("telNum", userName);
                        args.put("vcode", vcode.getText());
                        urlCheckVcode = URLUtil.getURL("checkVcode", args);
                        new NewNetAsyncTask(ResetPassword.this).execute(urlCheckVcode);
                    } else {
                        Toast.makeText(ResetPassword.this, "验证码格式有误", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    /**
     * 新回调函数，传入API请求返回json数据进行处理
     */
    @Override
    public void getResult(String jsonStr) {
        if (jsonStr != null) {
            Message reciveMsg = json.fromJson(jsonStr, new TypeToken<Message<?>>() {
            }.getType());
            if (reciveMsg.getMessageCode() != null && reciveMsg.getMessageCode() instanceof String) {
                switch (reciveMsg.getMessageCode()) {
                    //验证码校验成功
                    case C.vcode.VERIFIED_SUCCESS:
                        Intent intent = new Intent(ResetPassword.this, AlterPassword.class);
                        intent.putExtra("userName", phone.getText().toString());
                        startActivity(new Intent(ResetPassword.this, AlterPassword.class));
                        finish();
                        break;
                    //验证码校验不成功
                    case C.vcode.VERIFIED_FAILURE:
                        Toast.makeText(ResetPassword.this, "验证码错误", Toast.LENGTH_SHORT).show();
                        break;
                    //用户存在
                    case C.reg.USER_EXISTS:
                        Toast.makeText(ResetPassword.this, "用户不存在", Toast.LENGTH_SHORT).show();
                        break;
                    //用户不存在
                    case C.reg.USER_NOT_EXISTS:
                        Toast.makeText(ResetPassword.this, "手机号未注册", Toast.LENGTH_SHORT).show();
                        break;
                    //短信发送成功
                    case C.vcode.SMS_SEND_SUCCESS:
                        Toast.makeText(ResetPassword.this, "短信已发送", Toast.LENGTH_SHORT).show();
                        break;
                    //短信发送失败
                    case C.vcode.SMS_SEND_ERROR:
                        Toast.makeText(ResetPassword.this, "不可以在一分钟内多次发送验证码", Toast.LENGTH_SHORT).show();
                        break;
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
                BackItem bean = new BackItem();
                if ("exist".equals(bean.getIsright())) {
                    Toast.makeText(ResetPassword.this, "手机号已注册", Toast.LENGTH_SHORT).show();
                    if (ValidateUtil.tel(userName)) {
                        Map args = new HashMap();
                        args.put("userName", userName);
                        urlPin = URLUtil.getURL("sendSms", args);
                        new NetAsyncTask(ResetPassword.this).execute(urlPin);
                        time.start();
                    }
                }
                if ("no_exist".equals(bean.getIsright())) {
                    Toast.makeText(ResetPassword.this, "手机号不存在，请注册", Toast.LENGTH_SHORT).show();
                }
                if ("sendSuccess".equals(bean.getIsright())) {
                    Toast.makeText(ResetPassword.this, "短信发送成功", Toast.LENGTH_SHORT).show();
                }
                if ("sendError".equals(bean.getIsright())) {
                    Toast.makeText(ResetPassword.this, "不可以在一分钟内多次发送验证码", Toast.LENGTH_SHORT).show();
                }
                if ("vcodeSuccess".equals(bean.getIsright())) {
                    startActivity(new Intent(ResetPassword.this, AlterPassword.class));
                    finish();
                }
                if ("vcodeError".equals(bean.getIsright())) {
                    Toast.makeText(ResetPassword.this, "验证码错误", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * 新回调函数，传入url数组，一般情况只传入一个，即url[0]
     * 为兼容旧回调函数故做此处理
     * 2016/11/05
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
            if (url.equals(urlPhoneIsReg)) {
                VerifyRegisterServiceImpl Service = new VerifyRegisterServiceImpl();
                return Service.getmyContentHeadInf(new String(bytes));
            } else if (url.equals(urlPin)) {
                VerifyRegisterServiceImpl Service = new VerifyRegisterServiceImpl();
                return Service.getmyContentHeadInf(new String(bytes));
            } else if (url.equals(urlCheckVcode)) {
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
