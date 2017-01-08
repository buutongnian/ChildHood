package edu.buu.childhood.my.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.my.pojo.BackItem;
import edu.buu.childhood.my.pojo.ParentInfItem;

/**
 * Created by lcc on 2016/7/15.
 */
public class ChangeParentInfoServiceImpl implements ChangeParentInfoService{
    Gson json = new Gson();
    public CallBackPage<ParentInfItem> getmyContentHeadInf(String result) {
        Message<String> message = json.fromJson(result, new TypeToken<Message<String>>() {
        }.getType());
        if (C.my.PARENT_INF_UPDATE_SUCCESS.equals(message.getMessageCode())) {
            CallBackPage<ParentInfItem> callBackPage = new CallBackPage<ParentInfItem>();
            List<ParentInfItem> list = new ArrayList<ParentInfItem>();
            ParentInfItem info=new ParentInfItem();
            info.setIsRight("1");
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