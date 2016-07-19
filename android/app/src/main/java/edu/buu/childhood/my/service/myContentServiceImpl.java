package edu.buu.childhood.my.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.my.pojo.UserInf;
import edu.buu.childhood.my.pojo.myContentItem;

/**
 * Created by lcc on 2016/7/15.
 */
public class myContentServiceImpl implements MyContentService{
    Gson json = new Gson();
    public CallBackPage<myContentItem> getmyContentHeadInf(String result) {
        Message<UserInf> message = json.fromJson(result, new TypeToken<Message<UserInf>>() {
        }.getType());
        if (C.my.USER_INF_QUERY_SUCCESS.equals(message.getMessageCode())) {
            CallBackPage<myContentItem> callBackPage = new CallBackPage<myContentItem>();
            UserInf page = (UserInf) message.getMessageContent();
            List<myContentItem> list = new ArrayList<myContentItem>();
            myContentItem userInfo=new myContentItem();
            userInfo.setUserDetailAddr(page.getDetailAddr());
            userInfo.setUserNiname(page.getUserNickname());
            //userInfo.setUserIcon(page.getUserHeadImage());
            userInfo.setSelect("u");
            list.add(userInfo);
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