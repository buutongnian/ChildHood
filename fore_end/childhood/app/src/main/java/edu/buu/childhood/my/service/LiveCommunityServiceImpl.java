package edu.buu.childhood.my.service;

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
public class LiveCommunityServiceImpl implements LiveCommunityService {
    Gson json = new Gson();
    public CallBackPage<BackItem> getLiveCommunityChangeHeadInf(String result) {
        Message<String> message = json.fromJson(result, new TypeToken<Message<String>>() {
        }.getType());
        if (C.my.USER_INF_UPDATE_SUCCESS.equals(message.getMessageCode())) {
            CallBackPage<BackItem> callBackPage = new CallBackPage<BackItem>();
            List<BackItem> list = new ArrayList<BackItem>();
            BackItem info=new BackItem();
            info.setIsright("true");
            list.add(info);
            callBackPage.setDatalist(list);
           return callBackPage;
         }
     return null;
    }
    public String getLiveCommunityChangeContentInf() {
        return null;
    }
}