package edu.buu.childhood.common.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.common.UserPersonalPage;
import edu.buu.childhood.my.pojo.UserInfo;

/**
 * Created by Administrator on 2016/10/6.
 */
public class UserInfoISericeImpl implements UserInfoService {
    Gson json=new Gson();
    @Override
    public CallBackPage<UserInfo> getUserInfo(String result) {
        Message message = json.fromJson(result, new TypeToken<Message>() {
        }.getType());
        if (C.convene.GET_USER_INFO_SUCCESS.equals(message.getMessageCode())) {
            Message<UserInfo> message1 = json.fromJson(result, new TypeToken<Message<UserInfo>>() {
            }.getType());
            CallBackPage<UserInfo> callBackPage = new CallBackPage<>();
            List list = new ArrayList();
            UserInfo userInf = (UserInfo) message1.getMessageContent();
            list.add(userInf);
            callBackPage.setDatalist(list);
            callBackPage.setFalg(C.convene.GET_USER_INFO_SUCCESS);
            return callBackPage;
        }
        return null;
    }

    @Override
    public CallBackPage<UserPersonalPage> getUserPersionPage(String result) {

        Message messageCode = json.fromJson(result, new TypeToken<Message>() {
        }.getType());
        if (C.achvmt.GET_USER_PERSONAL_PAGE_SUCCESS.equals(messageCode.getMessageCode())){
            Message<UserPersonalPage> message = json.fromJson(result, new TypeToken<Message<UserPersonalPage>>() {
            }.getType());
            CallBackPage<UserPersonalPage> callBackPage=new CallBackPage<>();
            List<UserPersonalPage> list=new ArrayList<>();
            list.add(message.getMessageContent());
            callBackPage.setDatalist(list);
            callBackPage.setFalg(C.achvmt.GET_USER_PERSONAL_PAGE_SUCCESS);
            return callBackPage;
        }
        return null;
    }
}
