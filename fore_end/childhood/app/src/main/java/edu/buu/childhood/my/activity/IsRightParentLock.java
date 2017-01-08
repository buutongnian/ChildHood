package edu.buu.childhood.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import edu.buu.childhood.R;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.my.mylock.MyLockView;
import edu.buu.childhood.util.CloseActivity;

/**
 * Created by lcc on 2016/7/23.
 */
public class IsRightParentLock extends BaseAvtivity implements MyLockView.lockListener {
    private MyLockView lockView;
    private String passwordString = "";
    private String mima;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.parent_lock);
        lockView = (MyLockView) findViewById(R.id.myLockView);
        lockView.setLockListener(this);
        File file = new File(getFilesDir(), ((GlobalVeriable) getApplication()).getUserName());
        try {
            FileInputStream fin = new FileInputStream(file);
            //把字节流转化为字符流
            BufferedReader buffer = new BufferedReader(new InputStreamReader(fin));
            //读取文件中的用户名和密码
            mima = buffer.readLine();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @Override
    public void getStringPassword(String password) {
        passwordString = password;
    }

    @Override
    public boolean isPassword() {
        if (passwordString.equals(mima)) {
            return true;
        }
        Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
        return false;
    }

    public void traParentLayout() {
        startActivity(new Intent(IsRightParentLock.this, ChangePassword.class));
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
