package edu.buu.childhood.util;

/**
 * Created by Administrator on 2016/9/10.
 */
public interface MessageCallBack {
    public MessageEnty doInBackground(String url);
    public void getResult(MessageEnty ImageEnty, String message);
}
