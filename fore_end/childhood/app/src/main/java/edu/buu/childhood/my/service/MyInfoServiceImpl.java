package edu.buu.childhood.my.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import edu.buu.childhood.my.pojo.User;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.Message;


/**
 * Created by Administrator on 2016/9/13.
 */
public class MyInfoServiceImpl implements MyInfoService {
    @Override
    public CallBackPage<User> getMyHeadInfo(String result) {
        Gson json=new Gson();
        Message message=json.fromJson(result,new TypeToken<Message>(){}.getType());
        if(C.my.USER_INF_QUERY_SUCCESS.equals(message.getMessageCode())){
            Message<User> messageUserInfo=json.fromJson(result,new TypeToken<Message<User>>(){}.getType());
            CallBackPage<User> callBackPage=new CallBackPage<User>();
            callBackPage.setFalg("MyHeadInfo");
            List list=new ArrayList();
            list.add(messageUserInfo.getMessageContent());
            callBackPage.setDatalist(list);
            return callBackPage;
        }
        return null;
    }

    @Override
    public CallBackPage<String> updatePhoneNumBer(String result) {
        Gson json=new Gson();
        Message message=json.fromJson(result,new TypeToken<Message>(){}.getType());
        if(C.my.USER_INF_UPDATE_SUCCESS.equals(message.getMessageCode())){
            CallBackPage callBackPage=new CallBackPage();
            callBackPage.setFalg(C.my.USER_INF_UPDATE_SUCCESS);
            return callBackPage;
        }
        return null;
    }
}
