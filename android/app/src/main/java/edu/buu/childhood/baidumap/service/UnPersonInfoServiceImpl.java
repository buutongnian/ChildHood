package edu.buu.childhood.baidumap.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.buu.childhood.baidumap.poj.AMUserList;
import edu.buu.childhood.baidumap.poj.ComputeCanInvite;
import edu.buu.childhood.baidumap.poj.InfoList;
import edu.buu.childhood.baidumap.poj.MarkItem;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.Message;

/**
 * Created by lcc on 2016/7/15.
 */
public class UnPersonInfoServiceImpl implements UnPersonInfoService {
    Gson json = new Gson();
    public CallBackPage<MarkItem> getUnPersonInfoHeadInf(String result) {
        Message<List<AMUserList>> message = json.fromJson(result, new TypeToken<Message<List<AMUserList>>>() {
        }.getType());
        if (C.convene.COMPUTE_CAN_INVITE_SUCCESS.equals(message.getMessageCode())) {
            CallBackPage<MarkItem> callBackPage = new CallBackPage<MarkItem>();
            List<AMUserList> page = (List<AMUserList>) message.getMessageContent();
            List<MarkItem> list = new ArrayList<MarkItem>();
            Iterator iter = page.iterator();
            while (iter.hasNext()) {
                MarkItem Info = new MarkItem();
                AMUserList item = (AMUserList) iter.next();
                Info.setLastLatitude(item.getLastLatitude());
                Info.setLastLongitude(item.getLastLogitude());
                Info.setDistance(item.getDistance());
                Info.setSelect("3");
                list.add(Info);
            }
            callBackPage.setDatalist(list);

        return callBackPage;
    }
    return null;
    }
    public String getmyUnPersonInfoContentInf() {
        return null;
    }
}