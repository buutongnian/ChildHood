package edu.buu.childhood.my.activity;

/**
 * Created by lcc on 2016/6/14.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;
import edu.buu.childhood.R;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.my.pojo.BackItem;
import edu.buu.childhood.my.service.Edit_nameServiceImpl;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.URLUtil;

public class Edit_name extends Activity implements CallBack, OnClickListener {
    private TextView save;
    private EditText m_editText;
    private String backname;
    private String url;
    private String a;

    public Edit_name() {
        // TODO Auto-generated constructor stub
    }

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edite_name);
        //接收数据
        Intent intent = getIntent();
        backname = intent.getStringExtra("name");
        findViewById(R.id.edite_name_image).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });//返回键
        m_editText = (EditText) findViewById(R.id.edite_name_editText);
        m_editText.selectAll();
        m_editText.setText(backname);
        save = (TextView) findViewById(R.id.edite_name_save);
        save.setOnClickListener(this);
    }

    public void onClick(View v) {
        a = m_editText.getText().toString();
//url
        Map args = new HashMap();
        args.put("userName", "joe");
        args.put("userNickname", a);
        url = URLUtil.getURL("changeUserInf", args);
        new NetAsyncTask(this).execute(url);

//回到上一页面
        //发送数据
        Intent intent=new Intent();
        intent.putExtra("back",m_editText.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
  /*  @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    public void getResult(CallBackPage result) {
        BackItem bean = (BackItem) result.getDatalist().get(0);
        if (bean.getIsright() == null) {
            Toast.makeText(this, "提交不成功", Toast.LENGTH_SHORT).show();
        }
    }

    public CallBackPage doInBackground(String url) {
        HttpUtil httpUtil = new HttpUtil(url);
        Edit_nameServiceImpl Service = new Edit_nameServiceImpl();
        return Service.getmyContentHeadInf(new String(httpUtil.getHttpData()));
    }

}
