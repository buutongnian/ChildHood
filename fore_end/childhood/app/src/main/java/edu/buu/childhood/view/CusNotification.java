package edu.buu.childhood.view;

import android.app.Notification;
import android.content.Context;
import android.widget.ImageView;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;

import edu.buu.childhood.R;

/**
 * Created by joe on 2016/11/7.
 */

public class CusNotification extends Notification {
    private RemoteViews remoteView;
    private ImageView largeImage;
    private String title = "消息标题";
    private String message = "消息内容";

    public CusNotification(Context ctx) {
        super.flags = FLAG_AUTO_CANCEL;
        remoteView = new RemoteViews(ctx.getPackageName(),
                R.layout.custom_notification);
    }

    public CusNotification setLargeImage(ImageView imageView) {
        this.largeImage = imageView;
        return this;
    }

    public CusNotification setNotificationTitle(String title) {
        this.title = title;
        return this;
    }

    public CusNotification setNotificationMessage(String message) {
        this.message = message;
        return this;
    }

    public CusNotification build() {
        remoteView.setTextViewText(R.id.notification_title, title);
        remoteView.setTextViewText(R.id.notification_message, message);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
        String time = dateFormat.format(new java.util.Date(System.currentTimeMillis()));
        remoteView.setTextViewText(R.id.notification_time, time);
        super.icon = R.drawable.logo_128px;
        super.tickerText = title + "\n" + message;
        super.contentView = remoteView;
        return this;
    }
}
