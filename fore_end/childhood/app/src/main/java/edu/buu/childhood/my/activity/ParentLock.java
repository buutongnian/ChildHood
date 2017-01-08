package edu.buu.childhood.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import edu.buu.childhood.R;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.database.UserLoginDao;
import edu.buu.childhood.mainui.activity.Main_interface;
import edu.buu.childhood.my.mylock.MyLockView;
import edu.buu.childhood.util.CloseActivity;
import edu.buu.childhood.util.SmackManager;

/**
 * Created by lcc on 2016/7/23.
 */
public class ParentLock extends BaseAvtivity implements MyLockView.lockListener {
    private MyLockView lockView;
    private TextView forgetPassword;
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
        forgetPassword = (TextView) findViewById(R.id.parent_forget_parent_password);
        forgetPassword.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  GlobalVeriable globalVeriable = (GlobalVeriable) getApplication();
                                                  if (globalVeriable.getLogin()) {
                                                      File file = new File(getFilesDir(), globalVeriable.getUserName());
                                                      if (file.exists()) {
                                                          file.delete();
                                                      }
                                                      SmackManager.getInstance().logout();
                                                      Intent intent = new Intent();
                                                      intent.setAction("CoreService");
                                                      intent.setPackage(getPackageName());
                                                      stopService(intent);

                                                      UserLoginDao userLoginDao = new UserLoginDao(ParentLock.this);
                                                      userLoginDao.UpdateUserLoginInfo(globalVeriable.getUserName(), 0);

                                                      globalVeriable.setLogin(false);
                                                      globalVeriable.setUserName(null);
                                                      globalVeriable.setPassword(null);
                                                      Intent loginExit = new Intent(ParentLock.this, Main_interface.class);
                                                      startActivity(loginExit);
                                                      CloseActivity.exitOthers(ParentLock.this);
                                                      finish();
                                                  }
                                              }
                                          }
        );
        Intent intent = getIntent();
        mima = intent.getStringExtra("pw");
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
        startActivity(new Intent(ParentLock.this, ParentLayout.class));
        finish();
//        onDestroy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
