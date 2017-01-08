package edu.buu.childhood.my.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NewCallBack;
import edu.buu.childhood.util.NewNetAsyncTask;
import edu.buu.childhood.util.URLUtil;
import edu.buu.childhood.util.ValidateUtil;

/**
 * Created by joe on 2016/11/8.
 */

public class Feedback extends Activity implements NewCallBack {

    private EditText feedbackContent;
    private Button submit;

    private Gson json = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        feedbackContent = (EditText) findViewById(R.id.feedback_content);
        submit = (Button) findViewById(R.id.feedback_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ValidateUtil.isEmpty(feedbackContent.getText().toString())) {
                    String userName = ((GlobalVeriable) getApplication()).getUserName();
                    Map<String, String> args = new HashMap<String, String>();
                    if (userName != null) {
                        args.put("userName", userName);
                    } else {
                        args.put("userName", C.def.ANONYMOUS);
                    }
                    args.put("feedbackContent", feedbackContent.getText().toString());
                    args.put("feedbackModule", "fromAndroid");
                    String feedbackUrl = URLUtil.getURL("feedback", args);
                    Log.d("feedbackUrl", feedbackUrl);
                    new NewNetAsyncTask(Feedback.this).execute(feedbackUrl);
                } else {
                    Toast.makeText(Feedback.this, "请填写反馈内容", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 新回调函数，传入API请求返回json数据进行处理
     */
    @Override
    public void getResult(String jsonStr) {
        if (jsonStr != null) {
            Message reciveMsg = json.fromJson(jsonStr, new TypeToken<Message<?>>() {
            }.getType());
            if (reciveMsg.getMessageCode() != null && reciveMsg.getMessageCode() instanceof String) {
                switch (reciveMsg.getMessageCode()) {
                    case C.feedback.FEEDBACK_SUCCESS:
                        feedbackContent.setText("");
                        Toast.makeText(this, "反馈成功，感谢您的意见", Toast.LENGTH_LONG).show();
                        break;
                    case C.feedback.FEEDBACK_UNSUCCESS:
                        Toast.makeText(this, "服务器不给力，没有接收到您的反馈", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * 新回调函数，传入url数组，一般情况只传入一个，即url[0]
     * 为兼容旧回调函数故做此处理
     * 2016/11/08
     *
     * @param url 所传入url数组
     * @return 返回请求API接口所得到的json数据
     */
    @Override
    public String doInBackground(String... url) {
        HttpUtil httpUtil = new HttpUtil(url[0]);
        byte[] bytes = httpUtil.getHttpData();
        if (bytes != null) {
            return new String(bytes);
        }
        return null;
    }
}
