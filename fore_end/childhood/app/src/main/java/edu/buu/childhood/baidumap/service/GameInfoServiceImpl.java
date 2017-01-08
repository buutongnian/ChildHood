package edu.buu.childhood.baidumap.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.buu.childhood.baidumap.pojo.ComputeCanJoin;
import edu.buu.childhood.baidumap.pojo.InfoList;
import edu.buu.childhood.baidumap.pojo.MarkItem;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.game.pojo.GameHead;
import edu.buu.childhood.util.HttpUtil;
import edu.buu.childhood.util.URLUtil;

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
                Info.setGameId(item.getGameId());
                Info.setGameCode(item.getGameCode());
                Info.setGameTitle(item.getGameTitle());
                Info.setUserNickname(item.getUserNickname());
                Info.setGatherPlace(item.getGatherPlace());
                Info.setStartTime(item.getStartTime());
                Log.i("custom",item.getCustomCount()+"");
                Info.setCustomCount(item.getJoinedCount());
                Info.setGameLatitude(item.getGameLatitude());
                Info.setGameLongitude(item.getGameLongitude());
                Map url=new HashMap<>();
                url.put("gameCode",item.getGameCode());
                byte[] bytes=new HttpUtil(URLUtil.getURL("uniqueGameHead",url)).getHttpData();
                Message<GameHead> gameHeadMessage = json.fromJson(new String(bytes), new TypeToken<Message<GameHead>>() {
                }.getType());
                if (C.game.UNIQUE_GAME_HEAD_QUERY_SUCCESS.equals(gameHeadMessage.getMessageCode())) {
                        GameHead pageGameHead = (GameHead) gameHeadMessage.getMessageContent();
                        Info.setRecommendCount(pageGameHead.getRecommendCount());
                        Info.setGameContent(pageGameHead.getGameSynopsis());
                }
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