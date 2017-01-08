package edu.buu.childhood.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.login.service.LoginServiceImpl;
import edu.buu.childhood.my.pojo.BackItem;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.URLUtil;
import edu.buu.childhood.util.ValidateUtil;

/**
 * Created by lcc on 2016/9/25.
 */
public class AlterPassword extends BaseAvtivity implements CallBack {
    private String url;
    private EditText newPW;
    private EditText surePW;
    private ImageView alterPassword;
    private String urlAlterPassword;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        setContentView(R.layout.alter_password);
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        newPW = (EditText) findViewById(R.id.newPassword);
        surePW = (EditText) findViewById(R.id.makeSure);
        alterPassword = (ImageView) findViewById(R.id.alter);
        alterPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidateUtil.password(newPW.getText().toString())) {
                    if (newPW.getText().toString().equals(surePW.getText().toString())) {
                        Map args = new HashMap();
                        args.put("userName", userName);
                        args.put("password ", newPW.getText());
                        urlAlterPassword = URLUtil.getURL("resetPassword", args);
                        new NetAsyncTask(AlterPassword.this).execute(urlAlterPassword);
                    } else {
                        Toast.makeText(AlterPassword.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AlterPassword.this, "请输入8-16位仅包含字母或数字的密码", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void getResult(CallBackPage result) {
        if (result != null) {
            if (result.getDatalist().size() > 0) {
                BackItem bean = new BackItem();
                if ("alterSuccess".equals(bean.getIsright())) {
                    Toast.makeText(AlterPassword.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                }
                if ("alterError".equals(bean.getIsright())) {
                    Toast.makeText(AlterPassword.this, "密码修改失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public CallBackPage doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(url);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            LoginServiceImpl Service = new LoginServiceImpl();
            return Service.getLoginHeadInf(new String(bytes));
        }
        return null;
    }
}
