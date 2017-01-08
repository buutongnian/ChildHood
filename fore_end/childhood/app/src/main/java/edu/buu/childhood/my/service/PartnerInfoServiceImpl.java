package edu.buu.childhood.my.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.my.pojo.UserInfo;

/**
 * Created by lcc on 2016/7/15.
 */
public class PartnerInfoServiceImpl {
    Gson json = new Gson();
    public CallBackPage<UserInfo> getPartnerInfoHeadInf(String result) {
        Message message = json.fromJson(result, new TypeToken<Message>() {
        }.getType());
        if (C.convene.GET_USER_INFO_SUCCESS.equals(message.getMessageCode())) {
            Message<UserInfo> message1 = json.fromJson(result, new TypeToken<Message<UserInfo>>() {
            }.getType());
            CallBackPage<UserInfo> callBackPage = new CallBackPage<UserInfo>();
            UserInfo page = (UserInfo) message1.getMessageContent();
            List<UserInfo> list = new ArrayList<UserInfo>();
            list.add(page);
            callBackPage.setDatalist(list);

        return callBackPage;
    }
    return null;
    }
    public String getPartnerInfoContentInf() {
        return null;
    }
}