package edu.buu.childhood.my.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import edu.buu.childhood.R;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.my.data.DateTimePickDialogUtil;
import edu.buu.childhood.my.pojo.myContentItem;
import edu.buu.childhood.my.service.myContentServiceImpl;
import edu.buu.childhood.my.service.myContentServiceImplC;
import edu.buu.childhood.util.CallBack;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.NetAsyncTask;
import edu.buu.childhood.util.URLUtil;

/**
 * Created by lcc on 2016/6/7.
 */
public class My_content extends Activity implements CallBack {
    private RelativeLayout relativeLayout;
    private RelativeLayout relativeLayout_name;
    private RelativeLayout relativeLayout_gender;
    private RelativeLayout birthdayChange;
    private TextView gender;
    private TextView name;
    private TextView address;
    private TextView birthday;
    private RelativeLayout exit;
    private String urlC;
    private String urlU;
    private String initDateTime;
    int request_code=1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_content);

        gender = (TextView) findViewById(R.id.my_content_gendertext);
        name = (TextView) findViewById(R.id.my_content_inName);
        address = (TextView) findViewById(R.id.my_content_address);
        birthday = (TextView) findViewById(R.id.my_content_birthday);
//退出
        exit = (RelativeLayout) findViewById(R.id.my_content_exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentVersion = android.os.Build.VERSION.SDK_INT;
                if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
                    Intent startMain = new Intent(Intent.ACTION_MAIN);
                    startMain.addCategory(Intent.CATEGORY_HOME);
                    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(startMain);
                    System.exit(0);
                } else {// android2.1
                    ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                    am.restartPackage(getPackageName());
                }
            }
        });

        relativeLayout = (RelativeLayout) findViewById(R.id.my_content_r2);
        relativeLayout_name = (RelativeLayout) findViewById(R.id.my_content_name);
        birthdayChange = (RelativeLayout) findViewById(R.id.my_content_birthdayChange);
        relativeLayout_gender = (RelativeLayout) findViewById(R.id.my_content_gender);
        birthdayChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDateTime = (String) birthday.getText();
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        My_content.this, initDateTime);
                dateTimePicKDialog.dateTimePicKDialog(birthday);
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(My_content.this, HeadPicture.class));
            }
        });
        relativeLayout_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(My_content.this, GenderChange.class));
            }
        });
        relativeLayout_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送数据
                Intent intent=new Intent();
                //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra("name",name.getText());
                intent.setClass(My_content.this, Edit_name.class);
                My_content.this.startActivityForResult(intent,request_code);
            }
        });
        findViewById(R.id.my_content_image6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startActivity(new Intent(My_content.this, My.class));
                finish();
            }
        });

        Map args = new HashMap();
        args.put("userName", "joe");
        urlU = URLUtil.getURL("myInf", args);
        new NetAsyncTask(this).execute(urlU);

        Map argsC = new HashMap();
        argsC.put("userName", "lcc");
        urlC = URLUtil.getURL("childInf", argsC);
        new NetAsyncTask(this).execute(urlC);

    }


    @Override
            public void getResult(CallBackPage result) {
                myContentItem bean = (myContentItem) result.getDatalist().get(0);
                if (bean.getSelect() == "c") {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
                    String bir = df.format(bean.getUserBirthday());
                    birthday.setText(bir);

                    gender.setText(bean.getUserGender());
                } else {
                    name.setText(bean.getUserNiname());
                    address.setText(bean.getUserDetailAddr());
                }
            }

            @Override
            public CallBackPage doInBackground(String url) {
                if (url == urlC) {
                    HttpUtil httpUtilC = new HttpUtil(urlC);
                    myContentServiceImplC Service = new myContentServiceImplC();
                    return Service.getmyContentHeadInfC(new String(httpUtilC.getHttpData()));
                } else {
                    HttpUtil httpUtil = new HttpUtil(urlU);
                    myContentServiceImpl Service = new myContentServiceImpl();
            return Service.getmyContentHeadInf(new String(httpUtil.getHttpData()));
        }
    }

    public void onActivityResult(int requestCode, int resultCode,Intent data){
        if(requestCode==request_code){
            if (resultCode==RESULT_OK){
                name.setText(data.getStringExtra("back"));
            }
        }

   }

}
