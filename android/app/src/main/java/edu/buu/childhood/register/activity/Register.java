package edu.buu.childhood.register.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.game.adapter.GameRules_Spinner_Adapter;
import edu.buu.childhood.my.pojo.BackItem;
import edu.buu.childhood.register.service.RegisterServiceImpl;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.CreateDatabase;
import edu.buu.childhood.util.DBOprate;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.URLUtil;

/**
 * Created by lcc on 2016/6/21.
 */
public class Register extends Activity implements CallBack ,View.OnClickListener {
    //spinner1 变量
    //第一个spinner
    private List<String> list = new ArrayList<String>();
    private Spinner mySpinner;
    //第二个spinner
    private List<String> list1 = new ArrayList<String>();
    private Spinner mySpinner1;
    //第三个spinner
    private List<String> list2 = new ArrayList<String>();
    private Spinner mySpinner2;
    //第四个spinner
    private List<String> list3 = new ArrayList<String>();
    private Spinner mySpinner3;
    private Map<String, Integer> areaMap = new HashMap<String, Integer>();
    private Map<String, Integer> provinceMap = new HashMap<String, Integer>();
    private Map<String, Integer> cityMap = new HashMap<String, Integer>();
    private Map<String, Integer> DistrictMap = new HashMap<String, Integer>();
    private DBOprate dbOprate;
    private CreateDatabase createDatabase = new CreateDatabase();
    private Button button;
    private String url;
    private EditText useName;
    private EditText userPwd;
    private double regLatitude;
    private double regLongtitude;
    private EditText liveCommunity;
    private EditText userNickName;
    private int areaCode;
    private int provinceCode;
    private int cityCode;
    private int DistrictCode;
    private EditText email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        useName=(EditText)findViewById(R.id.register_userName);
        userPwd= (EditText) findViewById(R.id.resigter_userPwd);
        liveCommunity= (EditText) findViewById(R.id.resigter_liveCommunity);
        userNickName= (EditText) findViewById(R.id.resigter_userNickName);
        email= (EditText) findViewById(R.id.resigter_email);


        //返回键跳转
        /**
         *  spinner 初始化及适配器
         */
        list.add("请选择");
        createDatabase.copyDatabaseFile(this);
        dbOprate = new DBOprate(this);
        areaMap = dbOprate.getArea();
        for (Map.Entry<String, Integer> entry : areaMap.entrySet()) {
            list.add(entry.getKey());

        }
        //第二个spinner数据初始化
        list1.add("请选择");
        //第三个spinner数据初始化
        list2.add("请选择");
        //第四个spinner数据初始化
        list3.add("请选择");

        mySpinner = (Spinner) findViewById(R.id.register_spinner1);
        mySpinner1 = (Spinner) findViewById(R.id.register_spinner2);
        mySpinner2 = (Spinner) findViewById(R.id.register_spinner3);
        mySpinner3 = (Spinner) findViewById(R.id.register_spinner4);
        /**
         *第一个adapter
         */
        GameRules_Spinner_Adapter listAdapter = new GameRules_Spinner_Adapter(this,
                R.layout.spinner_checked_text, list, mySpinner);
        listAdapter.setDropDownViewResource(R.layout.game_spinner_dropdownlist);
        mySpinner.setAdapter(listAdapter);

        /**
         *第二个adapter
         */
        GameRules_Spinner_Adapter list1Adapter = new GameRules_Spinner_Adapter(this,
                R.layout.spinner_checked_text, list1, mySpinner1);
        list1Adapter.setDropDownViewResource(R.layout.game_spinner_dropdownlist);
        mySpinner1.setAdapter(list1Adapter);
        /**
         *第三个adapter
         */
        GameRules_Spinner_Adapter list2Adapter = new GameRules_Spinner_Adapter(this,
                R.layout.spinner_checked_text, list2, mySpinner2);
        list2Adapter.setDropDownViewResource(R.layout.game_spinner_dropdownlist);
        mySpinner2.setAdapter(list2Adapter);
        /**
         *第四个adapter
         */
        GameRules_Spinner_Adapter list3Adapter = new GameRules_Spinner_Adapter(this,
                R.layout.spinner_checked_text, list3, mySpinner3);
        list3Adapter.setDropDownViewResource(R.layout.game_spinner_dropdownlist);
        mySpinner3.setAdapter(list3Adapter);
        //第一个spinner单击事件
        mySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                String areaValues = (String) mySpinner.getItemAtPosition(position);
                if (!"请选择".equals(areaValues)) {
                    areaCode = areaMap.get(areaValues);
                    Log.d("areaCode",areaCode+"");
                    provinceMap = dbOprate.getProvince(areaCode);//调用数据库筛选类
                    list1.clear();//清空list1第一次选择的数据
                    list1.add("请选择");
                    for (Map.Entry<String, Integer> entry : provinceMap.entrySet()) {
                        list1.add(entry.getKey());
                    }
                    GameRules_Spinner_Adapter list1Adapter = new GameRules_Spinner_Adapter(getApplicationContext(),
                            R.layout.spinner_checked_text, list1, mySpinner1);
                    list1Adapter.setDropDownViewResource(R.layout.game_spinner_dropdownlist);
                    mySpinner1.setAdapter(list1Adapter);
                }

                arg0.setVisibility(View.VISIBLE);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                arg0.setVisibility(View.VISIBLE);
            }
        });
        mySpinner1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                String provinceValues = (String) mySpinner1.getItemAtPosition(position);
                if (!"请选择".equals(provinceValues)) {
                    provinceCode = provinceMap.get(provinceValues);
                    Log.d("provinceCode",provinceCode+"");
                    cityMap = dbOprate.getCity(provinceCode);//调用数据库筛选类
                    list2.clear();//清空list1第一次选择的数据
                    list2.add("请选择");
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
                if (!"请选择".equals(cityValues)) {
                    cityCode = cityMap.get(cityValues);
                    Log.d("cityCode",cityCode+"");
                    DistrictMap = dbOprate.getDistrict(cityCode);//调用数据库筛选类
                    list3.clear();//清空list1第一次选择的数据
                    list3.add("请选择");
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
                if (!"请选择".equals(DistrictValues)) {
                    DistrictCode =  DistrictMap.get(DistrictValues);
            }
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                arg0.setVisibility(View.VISIBLE);
            }

        });
        button = (Button) findViewById(R.id.register_button);
        button.setOnClickListener(this);
    }

    public void onClick(View v)  {
        Map args = new HashMap();
        args.put("userName", useName.getText());
        args.put("userPwd", userPwd.getText());
        args.put("regLatitude", 39.998397);
        args.put("regLongitude", 116.357981);
        args.put("liveCommunity", liveCommunity.getText());
        args.put("userNickName", userNickName.getText());
        args.put("belongingProvince", provinceCode);
        args.put("belongingCity", cityCode);
        args.put("belongingDistrict", DistrictCode);
        args.put("email", email.getText());
        url = URLUtil.getURL("register", args);
        new NetAsyncTask(this).execute(url);

    }


    @Override
    public void getResult(CallBackPage result) {
        BackItem bean = (BackItem) result.getDatalist().get(0);

            Log.d("register+",bean.getIsright());

    }

    public CallBackPage doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(url);
        RegisterServiceImpl Service = new RegisterServiceImpl();
        return Service.getmyContentHeadInf(new String(httpUtil.getHttpData()));
    }

    public void registerBack(View view) {
        finish();
    }

}
