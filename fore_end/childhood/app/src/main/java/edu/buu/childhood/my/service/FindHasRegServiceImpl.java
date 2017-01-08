package edu.buu.childhood.my.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.my.pojo.FindPartnerInfo;
import edu.buu.childhood.my.pojo.FindReg;

/**
 * Created by lcc on 2016/7/15.
 */
public class FindHasRegServiceImpl {
    Gson json = new Gson();
    public CallBackPage<FindPartnerInfo> getFindHasRegHeadInf(String result) {
        Message message = json.fromJson(result, new TypeToken<Message>() {
        }.getType());
        if (C.find.GET_FIND_INFO_SUCCESS.equals(message.getMessageCode())) {
            Message<FindReg> message1 = json.fromJson(result, new TypeToken<Message<FindReg>>() {
            }.getType());
            CallBackPage<FindPartnerInfo> callBackPage = new CallBackPage<FindPartnerInfo>();
            FindReg page = (FindReg) message1.getMessageContent();
            List<FindPartnerInfo> list = new ArrayList<FindPartnerInfo>();
            FindPartnerInfo userInfo=new FindPartnerInfo();
            userInfo.setAge(page.getAge());
            userInfo.setMessage(page.getMessage());
            userInfo.setYoungLatitude(page.getYoungLatitude());
            userInfo.setYoungLongitude(page.getYoungLongitude());
            userInfo.setSex(page.getSex());
            userInfo.setYoungNickname(page.getYoungNickname());
            userInfo.setYoungRoad(page.getYoungRoad());
            userInfo.setSelect(C.find.GET_FIND_INFO_SUCCESS);
            list.add(userInfo);
            callBackPage.setDatalist(list);

        return callBackPage;
    }else if(C.find.USER_UNREGED_FIND.equals(message.getMessageCode())){
            CallBackPage<FindPartnerInfo> callBackPage = new CallBackPage<FindPartnerInfo>();
            List<FindPartnerInfo> list = new ArrayList<FindPartnerInfo>();
            FindPartnerInfo info=new FindPartnerInfo();
            info.setSelect(C.find.USER_UNREGED_FIND);
            list.add(info);
            Log.i("sss",info.getSelect());
            callBackPage.setDatalist(list);
            return callBackPage;
        }
    return null;
    }
    public String getFindHasRegContentInf() {
        return null;
    }
}