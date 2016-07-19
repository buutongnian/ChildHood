package edu.buu.childhood.my.service;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.my.pojo.ChildInf;
import edu.buu.childhood.my.pojo.myContentItem;

/**
 * Created by lcc on 2016/7/15.
 */
public class myContentServiceImplC implements MyContentServiceC{
    Gson json = new Gson();
    public CallBackPage<myContentItem> getmyContentHeadInfC(String result) {
        Message<ArrayList<ChildInf>>message = json.fromJson(result, new TypeToken<Message<ArrayList<ChildInf>>>() {
        }.getType());
        if (C.my.CHILD_INF_QUERY_SUCCESS.equals(message.getMessageCode())) {
            CallBackPage<myContentItem> callBackPage = new CallBackPage<myContentItem>();
            ArrayList<ChildInf> page = (ArrayList<ChildInf>) message.getMessageContent();
            List<myContentItem> list = new ArrayList<myContentItem>();
            Iterator iter = page.iterator();
            while (iter.hasNext()) {
            myContentItem userInfo=new myContentItem();
                ChildInf child = (ChildInf) iter.next();
                userInfo.setUserGender(child.getChildSex()+"");
            userInfo.setUserBirthday(child.getChildBirthday());
            userInfo.setSelect("c");
            list.add(userInfo);
           }
            callBackPage.setDatalist(list);

        return callBackPage;
    }
    return null;
    }
    public String getmyContentContentInfC() {
        return null;
    }
}