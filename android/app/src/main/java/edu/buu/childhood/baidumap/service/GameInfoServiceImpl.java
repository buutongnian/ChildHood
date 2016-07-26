package edu.buu.childhood.baidumap.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.buu.childhood.baidumap.poj.ComputeCanJoin;
import edu.buu.childhood.baidumap.poj.InfoList;
import edu.buu.childhood.baidumap.poj.MarkItem;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.Message;

/**
 * Created by lcc on 2016/7/15.
 */
public class GameInfoServiceImpl implements GameInfoService {
    Gson json = new Gson();
    public CallBackPage<MarkItem> getGameInfoHeadInf(String result) {
        Message<InfoList<ComputeCanJoin>> message = json.fromJson(result, new TypeToken<Message<InfoList<ComputeCanJoin>>>() {
        }.getType());
        if (C.convene.COMPUTE_CAN_JOIN_SUCCESS.equals(message.getMessageCode())) {
            CallBackPage<MarkItem> callBackPage = new CallBackPage<MarkItem>();
            InfoList<ComputeCanJoin> page = (InfoList<ComputeCanJoin>) message.getMessageContent();
            List<MarkItem> list = new ArrayList<MarkItem>();
            Iterator iter = page.getDataList().iterator();
            while (iter.hasNext()) {
                MarkItem Info = new MarkItem();
                ComputeCanJoin item = (ComputeCanJoin) iter.next();
                Info.setGameCode(item.getGameCode());
                Info.setUserNickname(item.getUserNickname());
                Info.setGatherPlace(item.getGatherPlace());
                Info.setStartTime(item.getStartTime());
                Info.setCustomCount(item.getCustomCount());
                Info.setGameLatitude(item.getGameLatitude());
                Info.setGameLongitude(item.getGameLongitude());
                Info.setSelect("1");
                list.add(Info);

            }
            callBackPage.setDatalist(list);

        return callBackPage;
    }
    return null;
    }
    public String getmyGameInfoContentInf() {
        return null;
    }
}