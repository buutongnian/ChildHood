package edu.buu.childhood.util;

import edu.buu.childhood.common.CallBackPage;

/**
 * Created by Administrator on 2016/7/10.
 */
public interface CallBack {
    public void getResult(CallBackPage result);
    public CallBackPage doInBackground(String url);
}
