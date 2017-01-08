package edu.buu.childhood.register.activity;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.database.UserLoginDao;
import edu.buu.childhood.game.adapter.GameRules_Spinner_Adapter;
import edu.buu.childhood.login.activity.Login;
import edu.buu.childhood.mainui.activity.Main_interface;
import edu.buu.childhood.my.pojo.BackItem;
import edu.buu.childhood.register.service.VerifyRegisterServiceImpl;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.CreateDatabase;
import edu.buu.childhood.util.DBOprate;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.URLUtil;
import edu.buu.childhood.util.ValidateUtil;

/**
 * Created by lcc on 2016/6/21.
 */
public class PerfectInfo extends BaseAvtivity implements CallBack, View.OnClickListener {

    //第一个spinner
    private List<String> list1 = new ArrayList<String>();
    private Spinner mySpinner1;
    //第二个spinner
    private List<String> list2 = new ArrayList<String>();
    private Spinner mySpinner2;
    //第三个spinner
    private List<String> list3 = new ArrayList<String>();
    private Spinner mySpinner3;
    private Map<String, Integer> provinceMap = new HashMap<String, Integer>();
    private Map<String, Integer> cityMap = new HashMap<String, Integer>();
    private Map<String, Integer> DistrictMap = new HashMap<String, Integer>();
    private DBOprate dbOprate;
    private CreateDatabase createDatabase = new CreateDatabase();
    private Button button;
    private String url;
    private EditText userPwd;
    private EditText userNickName;
    private EditText makesurePassword;
    private EditText community;
    private int provinceCode;
    private int cityCode;
    private int DistrictCode;
    private EditText email;
    private String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.perfect_info);
        Intent intent = getIntent();
        if (intent != null) {
            phone = intent.getStringExtra("userName");
        }
        userPwd = (EditText) findViewById(R.id.register_password);
        makesurePassword = (EditText) findViewById(R.id.registerMakeSure);
        userNickName = (EditText) findViewById(R.id.register_userNickName);
        email = (EditText) findViewById(R.id.register_email);
        community = (EditText) findViewById(R.id.register_community);

        //返回键跳转
        /**
         *  spinner 初始化及适配器
         */

        createDatabase.copyDatabaseFile(this);
        dbOprate = new DBOprate(this);
        provinceMap = dbOprate.getProvince();

        //第一个spinner数据初始化
        list1.add("省份");
        for (Map.Entry<String, Integer> entry : provinceMap.entrySet()) {
            list1.add(entry.getKey());

        }
        //第二个spinner数据初始化
        list2.add("城市");
        //第三个spinner数据初始化
        list3.add("区县");

        mySpinner1 = (Spinner) findViewById(R.id.register_spinner1);
        mySpinner2 = (Spinner) findViewById(R.id.register_spinner2);
        mySpinner3 = (Spinner) findViewById(R.id.register_spinner3);
        /**
         *第一个adapter
         */
        GameRules_Spinner_Adapter listAdapter = new GameRules_Spinner_Adapter(this,
                R.layout.spinner_checked_text, list1, mySpinner1);
        listAdapter.setDropDownViewResource(R.layout.game_spinner_dropdownlist);
        mySpinner1.setAdapter(listAdapter);

        /**
         *第二个adapter
         */
        GameRules_Spinner_Adapter list2Adapter = new GameRules_Spinner_Adapter(this,
                R.layout.spinner_checked_text, list2, mySpinner2);
        list2Adapter.setDropDownViewResource(R.layout.game_spinner_dropdownlist);
        mySpinner2.setAdapter(list2Adapter);
        /**
         *第三个adapter
         */
        GameRules_Spinner_Adapter list3Adapter = new GameRules_Spinner_Adapter(this,
                R.layout.spinner_checked_text, list3, mySpinner3);
        list3Adapter.setDropDownViewResource(R.layout.game_spinner_dropdownlist);
        mySpinner3.setAdapter(list3Adapter);
        //第一个spinner单击事件

        mySpinner1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                String provinceValues = (String) mySpinner1.getItemAtPosition(position);
                if (!"省份".equals(provinceValues)) {
                    provinceCode = provinceMap.get(provinceValues);
                    // Log.d("provinceCode", provinceCode + "");
                    cityMap = dbOprate.getCity(provinceCode);//调用数据库筛选类
                    list2.clear();//清空list1第一次选择的数据
                    list2.add("城市");
                    for (Map.Entry<String, Integer> entry : cityMap.entrySet()) {
                        list2.add(entry.getKey());
                    }
                    GameRules_Spinner_Adapter list2Adapter = new GameRules_Spinner_Adapter(getApplicationContext(),
                            R.layout.spinner_checked_text, list2, mySpinner2);
                    list2Adapter.setDropDownViewResource(R.layout.game_spinner_dropdownlist);
                    mySpinner2.setAdapter(list2Adapter);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                arg0.setVisibility(View.VISIBLE);
            }
        });

        mySpinner2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                String cityValues = (String) mySpinner2.getItemAtPosition(position);
                if (!"城市".equals(cityValues)) {
                    cityCode = cityMap.get(cityValues);
                    DistrictMap = dbOprate.getDistrict(cityCode);//调用数据库筛选类
                    list3.clear();//清空list1第一次选择的数据
                    list3.add("区县");
                    for (Map.Entry<String, Integer> entry : DistrictMap.entrySet()) {
                        list3.add(entry.getKey());
                    }
                    GameRules_Spinner_Adapter list3Adapter = new GameRules_Spinner_Adapter(getApplicationContext(),
                            R.layout.spinner_checked_text, list3, mySpinner3);
                    list3Adapter.setDropDownViewResource(R.layout.game_spinner_dropdownlist);
                    mySpinner3.setAdapter(list3Adapter);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                arg0.setVisibility(View.VISIBLE);
            }
        });
        mySpinner3.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                String DistrictValues = (String) mySpinner3.getItemAtPosition(position);
                if (!"区县".equals(DistrictValues)) {
                    DistrictCode = DistrictMap.get(DistrictValues);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                arg0.setVisibility(View.VISIBLE);
            }

        });
        button = (Button) findViewById(R.id.registerFinish);
        button.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (!checkNetWorkStatus(this)) {
            setNetwork();
        } else {
            if (ValidateUtil.isEmpty(userNickName.getText().toString())) {
                Toast.makeText(this, "请输入昵称", Toast.LENGTH_SHORT).show();
                return;
            }
            if (ValidateUtil.password(userPwd.getText().toString())) {
                if (!(ValidateUtil.isEmpty(makesurePassword.getText().toString()))) {
                    if (userPwd.getText().toString().equals(makesurePassword.getText().toString())) {
                        Map args = new HashMap();
                        if (((GlobalVeriable) getApplication()).getLogin()) {
                            args.put("userName", ((GlobalVeriable) getApplication()).getUserName());
                        } else {
                            args.put("userName", phone);
                        }
                        args.put("userPwd", userPwd.getText().toString());
                        if (!ValidateUtil.isEmpty(userNickName.getText().toString()))
                            args.put("userNickname", userNickName.getText().toString());
                        if (provinceCode != 0)
                            args.put("belongingProvince", provinceCode);
                        if (cityCode != 0)
                            args.put("belongingCity", cityCode);
                        if (DistrictCode != 0)
                            args.put("belongingDistrict", DistrictCode);
                        if (!ValidateUtil.isEmpty(community.getText().toString()))
                            args.put("community ", community.getText().toString());
                        if (!ValidateUtil.isEmpty(email.getText().toString()))
                            args.put("email", email.getText().toString());
                        url = URLUtil.getURL("perfectInfo", args);
                        Log.i("url", url);
                        new NetAsyncTask(this).execute(url);
                    } else {
                        Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请输入确认密码", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "请输入8-16位仅包含字母或数字的密码", Toast.LENGTH_LONG).show();
            }


        }
    }

    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            BackItem bean = (BackItem) result.getDatalist().get(0);
            if (bean.getIsright().equals(C.reg.PERFECT_INFO_SUCCESS)) {
                UserLoginDao userLoginDao = new UserLoginDao(PerfectInfo.this);
                userLoginDao.UpdateUserPerfectInfo(phone, 1);
                userLoginDao.getLoginUser();
                if (((GlobalVeriable) getApplication()).getLogin()) {
                    startActivity(new Intent(PerfectInfo.this, Main_interface.class));
                } else {
                    startActivity(new Intent(PerfectInfo.this, Login.class));
                }
                ((GlobalVeriable) getApplication()).setPerfectInfo(true);
                Toast.makeText(PerfectInfo.this, "信息完善成功", Toast.LENGTH_SHORT).show();
                finish();
            }
            if (bean.getIsright().equals(C.reg.PERFECT_INFO_UNSUCCESS)) {
                Toast.makeText(PerfectInfo.this, "系统错误，请稍后再试", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public CallBackPage doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(url);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            VerifyRegisterServiceImpl Service = new VerifyRegisterServiceImpl();
            return Service.getmyContentHeadInf(new String(bytes));
        }
        return null;
    }

    public void registerBack(View view) {
        finish();
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
