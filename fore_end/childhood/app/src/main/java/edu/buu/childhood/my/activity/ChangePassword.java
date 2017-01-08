package edu.buu.childhood.my.activity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import edu.buu.childhood.R;
import edu.buu.childhood.common.GlobalVeriable;
import edu.buu.childhood.common.activity.BaseAvtivity;
import edu.buu.childhood.my.mylock.ChangeMyLockView;
import edu.buu.childhood.util.CloseActivity;

/**
 * Created by lcc on 2016/7/26.
 */
public class ChangePassword extends BaseAvtivity implements ChangeMyLockView.lockListener {
    private ChangeMyLockView lockView;
    private String passwordString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CloseActivity.activityList.add(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.change_password);
        lockView = (ChangeMyLockView) findViewById(R.id.lockView);
        lockView.setLockListener(this);
    }

    @Override
    public void getStringPassword(String password) {
        passwordString = password;
    }

    public void save() {
        File file = new File(getFilesDir(), ((GlobalVeriable) getApplication()).getUserName());
        deleteFile(file);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //写入用户名和密码，以name##passwd的格式写入
            fos.write(passwordString.getBytes());
            //关闭输出流
            fos.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Toast.makeText(this, "密码设置成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void deleteFile(File file) {
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    this.deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
        }
    }
}
