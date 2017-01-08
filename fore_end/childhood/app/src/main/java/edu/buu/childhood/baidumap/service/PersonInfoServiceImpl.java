package edu.buu.childhood.baidumap.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.buu.childhood.baidumap.pojo.ComputeCanInvite;
import edu.buu.childhood.baidumap.pojo.InfoList;
import edu.buu.childhood.baidumap.pojo.MarkItem;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.Message;

/**
 * Created by lcc on 2016/7/15.
 */
public class PersonInfoServiceImpl implements PersonInfoService {
    Gson json = new Gson();
    public CallBackPage<MarkItem> getPersonInfoHeadInf(String result) {
        Message<InfoList<ComputeCanInvite>> message = json.fromJson(result, new TypeToken<Message<InfoList<ComputeCanInvite>>>() {
        }.getType());
        if (C.convene.COMPUTE_CAN_INVITE_SUCCESS.equals(message.getMessageCode())) {
            CallBackPage<MarkItem> callBackPage = new CallBackPage<MarkItem>();
            InfoList<ComputeCanInvite> page = (InfoList<ComputeCanInvite>) message.getMessageContent();
            if (page!=null){
            List<MarkItem> list = new ArrayList<MarkItem>();
            Iterator iter = page.getDataList().iterator();
            while (iter.hasNext()) {
                MarkItem Info = new MarkItem();
                ComputeCanInvite item = (ComputeCanInvite) iter.next();
                Info.setLastLatitude(item.getLastLatitude());
                Info.setLastLongitude(item.getLastLogitude());
                Info.setDistance(item.getDistance());
                Info.setSelect("2");
                list.add(Info);
            }
            callBackPage.setDatalist(list);

        return callBackPage;
    }
        }
    return null;
    }
    public String getmyPersonInfoContentInf() {
        return null;
    }
}