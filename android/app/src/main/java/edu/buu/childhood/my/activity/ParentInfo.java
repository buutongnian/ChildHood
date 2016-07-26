package edu.buu.childhood.my.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;
import edu.buu.childhood.R;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.my.pojo.ParentInfItem;
import edu.buu.childhood.my.service.ChangeParentInfoServiceImpl;
import edu.buu.childhood.my.service.ParentInfoServiceImpl;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.URLUtil;

/**
 * Created by lcc on 2016/7/22.
 */
public class ParentInfo extends Activity implements CallBack, View.OnClickListener {
    private EditText motherName;
    private EditText fatherName;
    private EditText motherPhone;
    private EditText fatherPhone;
    private TextView xiugai;
    private String urlA;
    private String urlC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_info);
        motherName = (EditText) findViewById(R.id.parent_info_motherName);
        fatherName = (EditText) findViewById(R.id.parent_info_fatherName);
        motherPhone = (EditText) findViewById(R.id.parent_info_motherPhone);
        fatherPhone = (EditText) findViewById(R.id.parent_info_fatherPhone);
        xiugai = (TextView) findViewById(R.id.parent_info_save);
        xiugai.setOnClickListener(this);
        Map args = new HashMap();
        args.put("userName", "joe");
        urlA = URLUtil.getURL("parentInf", args);
        new NetAsyncTask(this).execute(urlA);
    }

    public void onClick(View v) {
        Map args = new HashMap();
        args.put("userName", "joe");
        args.put("fatherName", fatherName.getText());
        args.put("motherName", motherName.getText());
        args.put("fatherTel", fatherPhone.getText());
        args.put("motherTel", motherPhone.getText());
        urlC = URLUtil.getURL("changeParentInf", args);
        new NetAsyncTask(this).execute(urlC);

    }

    @Override
    public void getResult(CallBackPage result) {
        ParentInfItem bean = (ParentInfItem) result.getDatalist().get(0);
        if (bean.getIsRight() == "2") {
            motherName.setText(bean.getMatherName());
            fatherName.setText(bean.getFatherName());
            motherPhone.setText(bean.getMatherTel());
            fatherPhone.setText(bean.getFatherTel());
        } else {
            Toast.makeText(this, "更新成功", Toast.LENGTH_LONG).show();
        }


    }

    public CallBackPage doInBackground(String url) {
        if (url == urlA) {
            HttpUtil httpUtil = new HttpUtil(urlA);
            ParentInfoServiceImpl Service = new ParentInfoServiceImpl();
            return Service.getParentInfoHeadInf(new String(httpUtil.getHttpData()));
        } else {
            HttpUtil httpUtil = new HttpUtil(urlC);
            ChangeParentInfoServiceImpl Service = new ChangeParentInfoServiceImpl();
            return Service.getmyContentHeadInf(new String(httpUtil.getHttpData()));
        }
    }
}
