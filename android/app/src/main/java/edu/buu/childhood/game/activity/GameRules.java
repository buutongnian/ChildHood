package edu.buu.childhood.game.activity;


import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.game.refresh.ConnectionDetector;
import edu.buu.childhood.game.refresh.OnRefreshListener;
import edu.buu.childhood.game.refresh.RefreshListView;
import edu.buu.childhood.game.adapter.GameRules_Adapter;
import edu.buu.childhood.game.adapter.GameRules_Spinner_Adapter;
import edu.buu.childhood.game.pojo.ItemBean;
import edu.buu.childhood.R;
import edu.buu.childhood.game.service.GameServiceImpl;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.CreateDatabase;
import edu.buu.childhood.util.DBOprate;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.URLUtil;

/**
 * Created by lcc on 2016/6/13.
 */

public class GameRules extends Activity implements OnItemClickListener, CallBack, OnRefreshListener {
    private List<ItemBean> datalist = new ArrayList<ItemBean>();
    private Boolean first = true;
    private RefreshListView listview;
    //private GameRules_Adapter gameRules_adapter=;
    private String url = "http://123.57.52.135:8080/ChildHood/gameHead.do?webOrApp=app&pageNum=1";
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
    private Map<String, Integer> areaMap = new HashMap<String, Integer>();
    private Map<String, Integer> provinceMap = new HashMap<String, Integer>();
    private DBOprate dbOprate;
    private CreateDatabase createDatabase = new CreateDatabase();
    private int pageNum = 1;
    private boolean pageCompare;
    private boolean firstPageCompare;
    private ImageView imageWifi;
    private TextView titleText;
    //Typeface tf = Typeface.createFromAsset(getAssets(), "font/方正卡通简体.ttf");
    public GameRules() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_rules);
        titleText= (TextView) findViewById(R.id.game_rules_title_text);
       // titleText.setTypeface(tf);
        //titleText.setEnabled(false);
        //测试网络
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        Boolean isInternetPresent = cd.isConnectingToInternet();
        if (!isInternetPresent) {
            imageWifi = (ImageView) findViewById(R.id.game_rules_imagewifi);
            imageWifi.setVisibility(View.VISIBLE);
        }

        listview = (RefreshListView) findViewById(R.id.refreshlistview);
        listview.setOnRefreshListener(this);


        //返回键跳转
        findViewById(R.id.game_image1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //listview 初始化
        showlistview();

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

        mySpinner = (Spinner) findViewById(R.id.game_spinner1);
        mySpinner1 = (Spinner) findViewById(R.id.game_spinner2);
        mySpinner2 = (Spinner) findViewById(R.id.game_spinner3);
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

        //第一个spinner单击事件
        mySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                String areaValues = (String) mySpinner.getItemAtPosition(position);
                if (!"请选择".equals(areaValues)) {
                    int areaCode = areaMap.get(areaValues);
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
                    int provinceCode = provinceMap.get(provinceValues);
                    Map<String, Integer> provinceMap = dbOprate.getCity(provinceCode);//调用数据库筛选类
                    list2.clear();//清空list1第一次选择的数据
                    list2.add("请选择");
                    for (Map.Entry<String, Integer> entry : provinceMap.entrySet()) {
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
    }

    @Override
    public void onDownPullRefresh() {
        Log.i("shu","刷新");
        if(datalist.size()>0){
            datalist.removeAll(datalist);

        }
        // if(!firstPageCompare){
        pageNum = 1;
        Map args = new HashMap();
        args.put("pageNum", pageNum);
        url = URLUtil.getURL("gameHead", args);
        new NetAsyncTask(this).execute(url);

       listview.hideHeaderView();
    /*}else{
            new NetAsyncTask(this).execute(url);
            //listview.hideHeaderView();
        }*/
    }

    @Override
    public void onLoadingMore() {
        if (!pageCompare) {
            pageNum++;
            Map args = new HashMap();
            args.put("pageNum", pageNum);
            url = URLUtil.getURL("gameHead", args);
            new NetAsyncTask(this).execute(url);
            listview.hideFooterView();
        } else {
            listview.hideFooterView();
        }
    }


    /**
     * 为datalist写入数据，为listview配置适配器,加监听事件
     */
    private void showlistview() {

        new NetAsyncTask(this).execute(url);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

    }

    @Override
    public void getResult(CallBackPage result) {

        datalist.addAll(result.getDatalist());
        GameRules_Adapter gameRules_adapter = new GameRules_Adapter(this, datalist);
        //datalist = new GameServiceImpl().getGameHeadInf(new String(result));
        if (result.getCurrentPage() == result.getTotalPage()) {
            pageCompare = true;
        } else {
            pageCompare = false;
        }
        //pageCompare = new GameServiceImpl().pageNumJust();
        if (result.getCurrentPage() == 1) {
            firstPageCompare = true;
        } else {
            firstPageCompare = false;
        }
        // firstPageCompare=new GameServiceImpl().firstPageJust();
        if (result != null && first) {
            listview.setAdapter(gameRules_adapter);
            listview.setOnItemClickListener(this);
            first = false;
            //gameRules_adapter.notifyDataSetChanged();
        } else if (result != null) {
            gameRules_adapter.notifyDataSetChanged();
        }
    }

    @Override
    public CallBackPage doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(url);
        GameServiceImpl gameService = new GameServiceImpl();
        return gameService.getGameHeadInf(new String(httpUtil.getHttpData()));
    }
}
