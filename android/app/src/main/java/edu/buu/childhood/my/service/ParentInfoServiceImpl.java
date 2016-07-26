package edu.buu.childhood.my.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.my.activity.ParentInfo;
import edu.buu.childhood.my.pojo.ParentInf;
import edu.buu.childhood.my.pojo.ParentInfItem;
import edu.buu.childhood.my.pojo.UserInf;
import edu.buu.childhood.my.pojo.myContentItem;

/**
 * Created by lcc on 2016/7/15.
 */
public class ParentInfoServiceImpl implements ParentInfoService{
    Gson json = new Gson();
    public CallBackPage<ParentInfItem> getParentInfoHeadInf(String result) {
        Message<ParentInf> message = json.fromJson(result, new TypeToken<Message<ParentInf>>() {
        }.getType());
        if (C.my.PARENT_INF_QUERY_SUCCESS.equals(message.getMessageCode())) {
            CallBackPage<ParentInfItem> callBackPage = new CallBackPage<ParentInfItem>();
            ParentInf page = (ParentInf) message.getMessageContent();
            List<ParentInfItem> list = new ArrayList<ParentInfItem>();
            ParentInfItem userInfo=new ParentInfItem();
            userInfo.setMatherName(page.getMatherName());
            userInfo.setFatherName(page.getFatherName());
            userInfo.setMatherTel(page.getMatherTel());
            userInfo.setFatherTel(page.getFatherTel());
            userInfo.setIsRight("2");
            list.add(userInfo);
            Log.d("parent",list+"");
            callBackPage.setDatalist(list);

        return callBackPage;
    }
    return null;
    }
    public String getmyContentContentInf() {
        return null;
    }
}