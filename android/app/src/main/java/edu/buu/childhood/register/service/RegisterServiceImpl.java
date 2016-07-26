package edu.buu.childhood.register.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.my.pojo.BackItem;

/**
 * Created by lcc on 2016/7/15.
 */
public class RegisterServiceImpl implements RegisterService{
    Gson json = new Gson();
    public CallBackPage<BackItem> getmyContentHeadInf(String result) {
        Message<String> message = json.fromJson(result, new TypeToken<Message<String>>() {
        }.getType());
        Log.d("register++","true");
        if (C.reg.REG_SUCCESS.equals(message.getMessageCode())) {

            CallBackPage<BackItem> callBackPage = new CallBackPage<BackItem>();
            List<BackItem> list = new ArrayList<BackItem>();
            BackItem info=new BackItem();
            info.setIsright("true");
            list.add(info);
            callBackPage.setDatalist(list);
           return callBackPage;
         }
        if (C.reg.USER_EXISTS.equals(message.getMessageCode())) {
            CallBackPage<BackItem> callBackPage = new CallBackPage<BackItem>();
            List<BackItem> list = new ArrayList<BackItem>();
            BackItem info=new BackItem();
            info.setIsright("exist");
            list.add(info);
            callBackPage.setDatalist(list);
            return callBackPage;
        }
        if (C.reg.REG_ERROR.equals(message.getMessageCode())) {
            CallBackPage<BackItem> callBackPage = new CallBackPage<BackItem>();
            List<BackItem> list = new ArrayList<BackItem>();
            BackItem info=new BackItem();
            info.setIsright("error");
            list.add(info);
            callBackPage.setDatalist(list);
            return callBackPage;
        }
        if (C.reg.REG_UNSUCCESS.equals(message.getMessageCode())) {
            CallBackPage<BackItem> callBackPage = new CallBackPage<BackItem>();
            List<BackItem> list = new ArrayList<BackItem>();
            BackItem info=new BackItem();
            info.setIsright("unsuccess");
            list.add(info);
            callBackPage.setDatalist(list);
            return callBackPage;
        }
     return null;
    }
    public String getmyContentContentInf() {
        return null;
    }
}