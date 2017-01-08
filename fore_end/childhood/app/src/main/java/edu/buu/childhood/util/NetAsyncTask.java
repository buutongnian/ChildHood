package edu.buu.childhood.util;

import android.os.AsyncTask;

import edu.buu.childhood.common.CallBackPage;

/**
 * Created by Administrator on 2016/7/10.
 */
public class NetAsyncTask extends AsyncTask<String, Integer, CallBackPage> {
    private CallBack cb;


    public NetAsyncTask(CallBack cb) {
        this.cb = cb;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected CallBackPage doInBackground(String... params) {
        return cb.doInBackground(params[0]);
    }

    @Override
    protected void onPostExecute(CallBackPage result) {
        cb.getResult(result);
    }
}
