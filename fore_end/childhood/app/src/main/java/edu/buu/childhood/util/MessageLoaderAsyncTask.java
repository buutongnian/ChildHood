package edu.buu.childhood.util;

import android.os.AsyncTask;

/**
 * Created by Administrator on 2016/9/10.
 */
public class MessageLoaderAsyncTask extends AsyncTask<String, Integer, MessageEnty> {
    private MessageCallBack imageCallBack;
    private String message;

    public MessageLoaderAsyncTask(MessageCallBack imageCallBack, String message) {
        this.imageCallBack = imageCallBack;
        this.message = message;
    }

    @Override
    protected MessageEnty doInBackground(String... params) {
        if(params[0]!=null){
            return imageCallBack.doInBackground(params[0]);

        }else{
        return null;
        }

    }

    @Override
    protected void onPostExecute(MessageEnty ImageEnty) {
        if (ImageEnty != null) {
            imageCallBack.getResult(ImageEnty, message);
        }
    }
}
