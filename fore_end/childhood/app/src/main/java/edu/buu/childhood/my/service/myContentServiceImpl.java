package edu.buu.childhood.my.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.my.pojo.User;

/**
 * Created by lcc on 2016/7/15.
 */
public class myContentServiceImpl implements MyContentService{
    Gson json = new Gson();
    public CallBackPage<User> getmyContentHeadInf(String result) {
        Message<User> messageCode = json.fromJson(result, new TypeToken<Message<User>>() {
        }.getType());
        if (C.my.USER_INF_QUERY_SUCCESS.equals(messageCode.getMessageCode())) {
            Message<User> message = json.fromJson(result, new TypeToken<Message<User>>() {
            }.getType());
            CallBackPage<User> callBackPage = new CallBackPage<User>();
            User page =message.getMessageContent();
            //List<UserInf> list = new ArrayList<UserInf>();
             List<User> list = new ArrayList<User>();
            /*myContentItem userInfo=new myContentItem();
            userInfo.setUserNiname(page.getUserNickname());
            //userInfo.setUserIcon(page.getUserHeadImage());
            userInfo.setImageUrl(page.getUserHeadImage());
            userInfo.setSelect("u");*/
            list.add(page);
            Log.d("list++",list+"");
            callBackPage.setDatalist(list);

        return callBackPage;
    }
    return null;
    }
    public String getmyContentContentInf() {
        return null;
    }
}