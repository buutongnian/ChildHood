package edu.buu.childhood.my.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.common.Page;
import edu.buu.childhood.my.pojo.FindPartnerInfo;
import edu.buu.childhood.my.pojo.FindReg;
import edu.buu.childhood.my.pojo.FindResult;

/**
 * Created by lcc on 2016/7/15.
 */
public class FindPartnerServiceImpl {
    Gson json = new Gson();

    public CallBackPage<FindResult> getFindPartnerHeadInf(String result) {
        Message message = json.fromJson(result, new TypeToken<Message>() {
        }.getType());
        if (C.find.GET_FIND_INFO_SUCCESS.equals(message.getMessageCode())) {
            Message<Page<FindResult>> message1 = json.fromJson(result, new TypeToken<Message<Page<FindResult>>>() {
            }.getType());
            CallBackPage<FindResult> callBackPage = new CallBackPage<FindResult>();
            Page<FindResult> page = (Page<FindResult>) message1.getMessageContent();
            List<FindResult> list = new ArrayList<FindResult>();
            Iterator iter = page.getDataList().iterator();
            while (iter.hasNext()) {
                FindResult userInfo = new FindResult();
                FindResult bean = (FindResult) iter.next();
                userInfo.setAge(bean.getAge());
                userInfo.setMessage(bean.getMessage());
                userInfo.setSex(bean.getSex());
                userInfo.setUserName(bean.getMatchUser());
                userInfo.setYoungNickname(bean.getYoungNickname());
                userInfo.setUserHeadimage(bean.getUserHeadimage());
                list.add(userInfo);
            }
            callBackPage.setDatalist(list);

            return callBackPage;
        }

        return null;
    }

    public String getFindPartnerContentInf() {
        return null;
    }
}