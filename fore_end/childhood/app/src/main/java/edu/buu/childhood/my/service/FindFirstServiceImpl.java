package edu.buu.childhood.my.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.my.pojo.FindPartnerInfo;

/**
 * Created by lcc on 2016/7/15.
 */
public class FindFirstServiceImpl {
    Gson json = new Gson();
    public CallBackPage<FindPartnerInfo> getFindFirstHeadInf(String result) {
        Message<String> message = json.fromJson(result, new TypeToken<Message<String>>() {
        }.getType());
        if (C.find.FIND_REG_SUCCESS.equals(message.getMessageCode())) {
            CallBackPage<FindPartnerInfo> callBackPage = new CallBackPage<FindPartnerInfo>();
            List<FindPartnerInfo> list = new ArrayList<FindPartnerInfo>();
            FindPartnerInfo info=new FindPartnerInfo();
            info.setSelect("reg");
            list.add(info);
            callBackPage.setDatalist(list);
           return callBackPage;
         }
     return null;
    }
    public String getFindFirstContentInf() {
        return null;
    }
}