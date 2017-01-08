package edu.buu.childhood.game.activity;


import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

import edu.buu.childhood.R;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.game.adapter.GameRules_Adapter;
import edu.buu.childhood.game.adapter.GameRules_Spinner_Adapter;
import edu.buu.childhood.game.pojo.ItemBean;
import edu.buu.childhood.game.refresh.AutoListView;
import edu.buu.childhood.game.service.GameContentServiceImpl;
import edu.buu.childhood.game.service.GameServiceImpl;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.DBOprate;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.URLUtil;
import edu.buu.childhood.view.LoadDialog;

/**
 * Created by lcc on 2016/6/13.
 */

public class GameRules extends BaseAvtivity implements CallBack, AutoListView.OnRefreshListener,
        AutoListView.OnLoadListener {
    //获取游戏数据
    private List<ItemBean> datalist = new ArrayList<ItemBean>();
    private AutoListView xl;
    private GameRules_Adapter gameRules_adapter;
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
    //省市县的数据
    private Map<String, Integer> areaMap = new HashMap<String, Integer>();
    private Map<String, Integer> ageMap = new HashMap<String, Integer>();
    private Map<String, Integer> countMap = new HashMap<String, Integer>();
    private DBOprate dbOprate;
    private int pageNum = 1;
    private TextView titleText;
    //是否是上拉刷新
    private boolean isReshView = true;
    private GameServiceImpl gameService;
    //注册广播变量
    private IntentFilter filter;
    private MyReceiver mReceiver;
    //返回
    private ImageView imageView;
    //url的map
    private Map args;
    //请求游戏内容
    private String gameUrl;

    private String clickGameTitle;

    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_rules);
        //实例化url的map
        args = new HashMap();
        //注册广播
        mReceiver = new MyReceiver();
        filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        imageView = (ImageView) findViewById(R.id.game_rules_image);
        titleText = (TextView) findViewById(R.id.game_rules_title_text);
        xl = (AutoListView) findViewById(R.id.autolistview);
        gameRules_adapter = new GameRules_Adapter(this, datalist);
        xl.setAdapter(gameRules_adapter);
        xl.setOnRefreshListener(this);
        xl.setOnLoadListener(this);
        //listview 监听事件
        xl.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemBean item = (ItemBean) xl.getItemAtPosition(position);
                if (item != null) {
                    int gCode = item.getGameCode();
                    Map args = new HashMap();
                    args.put("userName", ((GlobalVeriable) getApplication()).getUserName());
                    args.put("gameCode", gCode);
                    if (((GlobalVeriable) getApplication()).getUserName() != null) {
                        args.put("userName", ((GlobalVeriable) getApplication()).getUserName());
                    } else {
                        args.put("userName", C.def.ANONYMOUS);
                    }
                    clickGameTitle = item.getGameTitle();
                    gameUrl = URLUtil.getURL("gameContent", args);
                    loadingDialog = showDialog(GameRules.this);
                    new NetAsyncTask(GameRules.this).execute(gameUrl);
                }
            }
        });


        //返回键跳转
       /* findViewById(R.id.game_image1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
        /**
         *  spinner 初始化及适配器
         */
//        list.add("区域");
        dbOprate = new DBOprate(this);
        areaMap = dbOprate.getArea();
        for (int i = 0; i < areaMap.size(); i++) {
            for (Map.Entry<String, Integer> entry : areaMap.entrySet()) {
                if (entry.getValue() == i) {
                    list.add(entry.getKey());
                }
            }
        }
        //第二个spinner数据初始化
//        list1.add("年龄段");
        ageMap = dbOprate.getAge();
        for (int i = 0; i < ageMap.size(); i++) {
            for (Map.Entry<String, Integer> entry : ageMap.entrySet()) {
                if (entry.getValue() == i) {
                    list1.add(entry.getKey());
                }
            }
        }
        //第三个spinner数据初始化
//        list2.add("人数段");
        countMap = dbOprate.getCount();
        for (int i = 0; i < countMap.size(); i++) {
            for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
                if (entry.getValue() == i) {
                    list2.add(entry.getKey());
                }
            }
        }

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
                if (!"区域".equals(areaValues)) {
                    int areaCode = areaMap.get(areaValues);
                    args.clear();
                    args.put("userName", C.def.ANONYMOUS);
                    args.put("gameArea", areaCode);
                    args.put("pageNum", 1);
                    datalist.clear();
                    new NetAsyncTask(GameRules.this).execute(URLUtil.getURL("gameHead", args));
                }
                mySpinner.setBackgroundResource(R.drawable.spinner_left);
                arg0.setVisibility(View.VISIBLE);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                mySpinner.setBackgroundResource(R.drawable.spinner_left);
                arg0.setVisibility(View.VISIBLE);
            }
        });

        //第二个spinner单击事件
        mySpinner1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                String age = (String) mySpinner1.getItemAtPosition(position);
                if (!"年龄段".equals(age)) {
                    int ageCode = ageMap.get(age);
                    args.clear();
                    args.put("userName", C.def.ANONYMOUS);
                    args.put("ageCode", ageCode);
                    args.put("pageNum", 1);
                    datalist.clear();
                    new NetAsyncTask(GameRules.this).execute(URLUtil.getURL("gameHead", args));
                }
                mySpinner1.setBackgroundResource(R.drawable.spinner_left);
                arg0.setVisibility(View.VISIBLE);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                mySpinner1.setBackgroundResource(R.drawable.spinner_left);
                arg0.setVisibility(View.VISIBLE);
            }
        });
        //第三个spinner单击事件
        mySpinner2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                String count = (String) mySpinner2.getItemAtPosition(position);
                if (!"人数段".equals(count)) {
                    int countCode = countMap.get(count);
                    args.clear();
                    args.put("userName", C.def.ANONYMOUS);
                    args.put("pageNum", 1);
                    args.put("memNumCode", countCode);
                    datalist.clear();
                    new NetAsyncTask(GameRules.this).execute(URLUtil.getURL("gameHead", args));
                }
                mySpinner2.setBackgroundResource(R.drawable.spinner_left);
                arg0.setVisibility(View.VISIBLE);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                mySpinner2.setBackgroundResource(R.drawable.spinner_left);
                arg0.setVisibility(View.VISIBLE);
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
    protected void onResume() {
        registerReceiver(mReceiver, filter);
        //缓存数据
      /*  CallBackPage C = MemoryCache.getInstance().getCbp1();
        datalist.addAll(C.getDatalist());*/
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }


    @Override
    public void getResult(CallBackPage result) {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        if (result != null) {
            if (result.getDatalist().size() > 0) {
                if (!"gameContent".equals(result.getFalg())) {
                    if (isReshView) {
                        datalist.clear();
                        xl.onRefreshComplete();
                        List<ItemBean> itemBeanList = result.getDatalist();
                        List<ItemBean> tempList = new ArrayList<ItemBean>();
                        for (ItemBean itemBean : itemBeanList) {
                            for (Map.Entry<String, Integer> entry : ageMap.entrySet()) {
                                if (entry.getValue() == Integer.parseInt(itemBean.getAgeRank())) {
                                    itemBean.setAgeRank(entry.getKey());
                                    break;
                                }
                            }
                            for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
                                if (entry.getValue() == Integer.parseInt(itemBean.getMemNumSize())) {
                                    itemBean.setMemNumSize(entry.getKey());
                                    break;
                                }
                            }
                            tempList.add(itemBean);
                        }
                        datalist.addAll(tempList);
                    } else {
                        xl.onLoadComplete();
                        List<ItemBean> itemBeanList = result.getDatalist();
                        List<ItemBean> tempList = new ArrayList<ItemBean>();

                        for (ItemBean itemBean : itemBeanList) {
                            for (Map.Entry<String, Integer> entry : ageMap.entrySet()) {
                                if (entry.getValue() == Integer.parseInt(itemBean.getAgeRank())) {
                                    itemBean.setAgeRank(entry.getKey());
                                    break;
                                }
                            }
                            for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
                                if (entry.getValue() == Integer.parseInt(itemBean.getMemNumSize())) {
                                    itemBean.setMemNumSize(entry.getKey());
                                    break;
                                }
                            }
                            tempList.add(itemBean);
                        }
                        datalist.addAll(tempList);
                    }
                    gameRules_adapter.notifyDataSetChanged();
                    xl.setPageSize(result.getPageSize());
                    xl.setResultSize(result.getDatalist().size());
                } else {
                    ItemBean bean = (ItemBean) result.getDatalist().get(0);
                    if (bean.getGameContent() != null) {
                        Intent intent = new Intent();
                        intent.putExtra("gameContent", bean.getGameContent());
                        intent.putExtra("gameTitle", clickGameTitle);
                        intent.setClass(GameRules.this, gameWebview.class);
                        GameRules.this.startActivity(intent);
                    }
                }
            } else {
                datalist.clear();
                gameRules_adapter.notifyDataSetChanged();
                xl.setPageSize(result.getPageSize());
                xl.setResultSize(result.getDatalist().size());
            }
        } else {
            xl.onLoadComplete();
        }
    }

    @Override
    public CallBackPage doInBackground(String url) {
        Log.d("gameHeadUrl", url);
        HttpUtil httpUtil = new HttpUtil(url);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            if (!(url.equals(gameUrl))) {
                gameService = new GameServiceImpl();
                return gameService.getGameHeadInf(new String(bytes));
            } else {
                GameContentServiceImpl service = new GameContentServiceImpl();
                return service.getGameContentInf(new String(bytes));
            }
        }
        return null;
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

    @Override
    public void onLoad() {
        isReshView = false;
        pageNum++;
        args.clear();
        args.put("pageNum", pageNum);
        if (((GlobalVeriable) getApplication()).getUserName() != null) {
            args.put("userName", ((GlobalVeriable) getApplication()).getUserName());
        } else {
            args.put("userName", C.def.ANONYMOUS);
        }
        String loadUrl = URLUtil.getURL("gameHead", args);
        new NetAsyncTask(GameRules.this).execute(loadUrl);
    }

    @Override
    public void onRefresh() {
        isReshView = true;
        pageNum = 1;
        args.clear();
        args.put("pageNum", pageNum);
        if (((GlobalVeriable) getApplication()).getUserName() != null) {
            args.put("userName", ((GlobalVeriable) getApplication()).getUserName());
        } else {
            args.put("userName", C.def.ANONYMOUS);
        }
        String refreshUrl = URLUtil.getURL("gameHead", args);
        new NetAsyncTask(GameRules.this).execute(refreshUrl);
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!checkNetWorkStatus(GameRules.this)) {
                imageView.setVisibility(View.VISIBLE);
                xl.setRefreshEnable();
            } else {
                imageView.setVisibility(View.GONE);
                args.clear();
                args.put("pageNum", 1);
                if (((GlobalVeriable) getApplication()).getUserName() != null) {
                    args.put("userName", ((GlobalVeriable) getApplication()).getUserName());
                } else {
                    args.put("userName", C.def.ANONYMOUS);
                }
                new NetAsyncTask(GameRules.this).execute(URLUtil.getURL("gameHead", args));
            }
        }
    }

    /**
     * 网络未连接时，调用设置方法(暂时不用)
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
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
    }
}
