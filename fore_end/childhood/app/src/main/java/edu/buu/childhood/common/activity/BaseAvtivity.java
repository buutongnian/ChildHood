package edu.buu.childhood.common.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import edu.buu.childhood.R;
import edu.buu.childhood.view.LoadDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Administrator on 2016/10/7.
 */
public class BaseAvtivity extends AppCompatActivity {
    private Boolean isExit = false;


    @Override
    public int checkSelfPermission(String permission) {
        Log.i("location", "dddd");

        return super.checkSelfPermission(permission);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void onBack(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
       /* ActivityManager manager = (ActivityManager)this.getSystemService(ACTIVITY_SERVICE); //获取应用程序管理器
        manager.killBackgroundProcesses(getPackageName()); //强制结束当前应用程序*/
        finish();
        super.onBackPressed();
    }

    public void isPermission() {

        if (Build.VERSION.SDK_INT >= 23) {
            int checkPermission = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                // return;
            }
        }

    }

    public Dialog showDialog(Context context) {
        Dialog dialog;
        dialog = new LoadDialog(context, R.style.Dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }


}
