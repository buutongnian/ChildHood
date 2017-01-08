package edu.buu.childhood.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joe on 2016/11/4.
 * 关闭Activity的类，用于程序退出和退出登录时
 */
public class CloseActivity {

    public static List<Activity> activityList = new ArrayList<Activity>();

    public static void exitClient(Context ctx) {
        // 关闭所有Activity
        for (int i = 0; i < activityList.size(); i++) {
            if (null != activityList.get(i)) {
                activityList.get(i).finish();
            }
        }
        ActivityManager activityMgr = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        activityMgr.restartPackage(ctx.getPackageName());
        System.exit(0);
    }

    public static void exitOthers(Context ctx) {
        // 关闭所有Activity
        for (int i = 0; i < activityList.size(); i++) {
            if (null != activityList.get(i) && ctx != activityList.get(i) && ctx.getApplicationContext() != activityList.get(i)) {
                activityList.get(i).finish();
            }
        }
        activityList.clear();
    }
}
