package edu.buu.childhood.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.common.activity.UserInfoActivity;
import edu.buu.childhood.my.Adapter.getPartnerAdapter;
import edu.buu.childhood.my.pojo.FindResult;
import edu.buu.childhood.my.service.FindPartnerServiceImpl;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.URLUtil;

/**
 * Created by lcc on 2016/9/10.
 */
public class HasFoundPartner extends BaseAvtivity implements CallBack {
    private ListView listview;
    List<FindResult> getList = null;
    private getPartnerAdapter adapter;
    private String getUrl;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.get_partner);
        //获得用户名
        if (((GlobalVeriable) getApplication()).getLogin())
            userName = ((GlobalVeriable) getApplication()).getUserName();
        listview = (ListView) findViewById(R.id.get_partner_listview);
        getList = new ArrayList<FindResult>();
        adapter = new getPartnerAdapter(HasFoundPartner.this, getList);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FindResult findResult = getList.get(position);
                if (findResult != null) {
                    Intent intent = new Intent(HasFoundPartner.this, UserInfoActivity.class);
                    intent.putExtra("userName", findResult.getUserName());
                    intent.putExtra("userNickName", findResult.getYoungNickname());
                    intent.putExtra("imageUrl", findResult.getUserHeadimage());
                    startActivity(intent);
                }

            }
        });

//请求数据
        Map args = new HashMap();
        args.put("userName", userName);
        getUrl = URLUtil.getURL("getPartnerList", args);
        new NetAsyncTask(HasFoundPartner.this).execute(getUrl);


       /* //为listview添加监听事件
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FindResult item = (FindResult) listview.getItemAtPosition(position);
                // TextView trans= (TextView) findViewById(R.id.getPatentUserName);
                //  String name=trans.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("name", item.getUserName());
                intent.setClass(HasFoundPartner.this, PartnerInfo2.class);
                HasFoundPartner.this.startActivity(intent);
            }
        });*/


    }

    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            if (result.getDatalist().size() > 0) {
                getList.addAll(result.getDatalist());
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public CallBackPage doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(getUrl);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            FindPartnerServiceImpl service = new FindPartnerServiceImpl();
            return service.getFindPartnerHeadInf(new String(bytes));
        }
        return null;
    }
}
