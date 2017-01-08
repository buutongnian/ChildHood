package edu.buu.childhood.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.my.service.MyInfoService;
import edu.buu.childhood.my.service.MyInfoServiceImpl;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.URLUtil;

/**
 * Created by lcc on 2016/7/1.
 */
public class PhoneNumberChange extends BaseAvtivity implements CallBack {
    private EditText etPhoneNumber;
    private TextView btEditePhoneSave;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.phonenumberchange);
        etPhoneNumber = (EditText) findViewById(R.id.edite_phonenumber_editText);
        btEditePhoneSave = (TextView) findViewById(R.id.edite_name_save);
        //接收从上一级传过来的数据
        Intent intent = getIntent();
        if (intent != null) {
            phoneNumber = intent.getStringExtra("phoneNumber");
        }
        btEditePhoneSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etPhoneNumber.getText().equals("")) {
                    Map args = new HashMap();
                    args.put("userName", ((GlobalVeriable) getApplication()).getUserName());
                    args.put("userTelNum", etPhoneNumber.getText().toString());
                    String url = URLUtil.getURL("changeUserInf", args);
                    Log.d("man", url);
                    new NetAsyncTask(PhoneNumberChange.this).execute(url);
                }
            }
        });

    }

    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            if (result.equals(C.my.USER_INF_UPDATE_SUCCESS)) {
                finish();
            }
        }
    }

    public CallBackPage doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(url);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            MyInfoService myInfoService = new MyInfoServiceImpl();
            return myInfoService.updatePhoneNumBer(new String(bytes));
        }
        return null;
    }
}