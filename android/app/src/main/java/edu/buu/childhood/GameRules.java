package edu.buu.childhood;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by lcc on 2016/6/13.
 */

public class GameRules extends Activity implements OnItemClickListener {
    private List<ItemBean> datalist;
    private ListView listview;
    private List<Integer> imageList = new ArrayList<Integer>();
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
    DBOprate dbOprate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_rules);
        //返回键跳转
        findViewById(R.id.game_image1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //listview 初始化
        imageList.add(R.drawable.list1);
        imageList.add(R.drawable.list2);
        listview = (ListView) findViewById(R.id.game_listView);
        showlistview();

        /**
         *  spinner 初始化及适配器
         */
        list.add("请选择");
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

                Toast.makeText(getApplicationContext(), "你点击了第" + (position + 1) + "个item", Toast.LENGTH_SHORT).show();
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


    /**
     * 为datalist写入数据，为listview配置适配器,加监听事件
     */
    private void showlistview() {
        datalist = new ArrayList<ItemBean>();
//        for (int i = 0; i < 5; i++) {
//            int a = (R.drawable.list1) + i;
//            int b = R.drawable.text_gamelistview;
//            datalist.add(new ItemBean(b, a, "第" + (i + 1) + "item的主题XXXX", "第" + (i + 1) + "item的日期", "第" + (i + 1) + "item的浏览次数"));
//        }
        Iterator iter = imageList.iterator();
        int i = 0;
        while (iter.hasNext()) {
            int a = (int) iter.next();
            int b = R.drawable.text_gamelistview;
            datalist.add(new ItemBean(b, a, "第" + (i + 1) + "item的主题XXXX", "第" + (i + 1) + "item的日期", "第" + (i + 1) + "item的浏览次数"));
            i++;
        }
        listview.setAdapter(new GameRules_Adapter(this, datalist));
        listview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        Toast.makeText(this, "你点击了第" + (arg2 + 1) + "个item", Toast.LENGTH_SHORT).show();
    }

}
