package edu.buu.childhood.util;

import android.os.AsyncTask;

import edu.buu.childhood.common.Message;

/**
 * Created by joe on 2016/11/3.
 */

public class NewNetAsyncTask extends AsyncTask<String, Integer, String> {
    private NewCallBack cb;

    public NewNetAsyncTask(NewCallBack cb) {
        this.cb = cb;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        return cb.doInBackground(params[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        cb.getResult(result);
    }
}
