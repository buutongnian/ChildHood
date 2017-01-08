package edu.buu.childhood.baidumap.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import edu.buu.childhood.baidumap.pojo.MarkItem;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.Message;

/**
 * Created by lcc on 2016/7/15.
 */
public class JoinGameServiceImpl {

    public CallBackPage<MarkItem> getJoinGameHeadInf(String result) {
        Gson json = new Gson();
        Log.i("result1",result.toString());
        Message<String> message = json.fromJson(result, new TypeToken<Message<String>>() {
        }.getType());
        Log.i("messagecode1",message.getMessageCode());
        if (C.onekey.JOIN_GAME_SUCCESS.equals(message.getMessageCode())) {
            CallBackPage<MarkItem> callBackPage = new CallBackPage<MarkItem>();
            List<MarkItem> list = new ArrayList<MarkItem>();
            MarkItem info=new MarkItem();
            info.setSelect("4");
            Log.i("code",message.getMessageCode());
            list.add(info);
            callBackPage.setDatalist(list);
           return callBackPage;
         }
     return null;
    }
    public String getJoinExitContentInf() {
        return null;
    }
}