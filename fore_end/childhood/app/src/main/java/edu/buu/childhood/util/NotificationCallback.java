package edu.buu.childhood.util;

import android.app.Notification;
import android.graphics.Bitmap;

/**
 * Created by joe on 2016/11/8.
 */

public interface NotificationCallback {
    public void showNotification(Notification noti, Bitmap bitmap);
}
