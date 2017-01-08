package edu.buu.childhood.util;


import edu.buu.childhood.common.Message;

/**
 * Created by joe on 2016/11/3.
 */

public interface NewCallBack {
    public void getResult(String jsonStr);

    public String doInBackground(String... url);
}
