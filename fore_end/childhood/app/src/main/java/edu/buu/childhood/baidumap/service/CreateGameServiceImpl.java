package edu.buu.childhood.baidumap.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import edu.buu.childhood.baidumap.pojo.GameStatus;
import edu.buu.childhood.baidumap.pojo.MarkItem;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.Message;

/**
 * Created by lcc on 2016/7/15.
 */
public class CreateGameServiceImpl {
    Gson json = new Gson();
    public CallBackPage<MarkItem> getCreateGameHeadInf(String result) {
        Message message = json.fromJson(result, new TypeToken<Message>() {
        }.getType());
        if (C.onekey.CONVENE_SUCCESS.equals(message.getMessageCode())) {
            Message<GameStatus> messageGame = json.fromJson(result, new TypeToken<Message<GameStatus>>() {
            }.getType());
            GameStatus gameStatus= (GameStatus) messageGame.getMessageContent();
            CallBackPage<MarkItem> callBackPage = new CallBackPage<MarkItem>();
            List<MarkItem> list = new ArrayList<MarkItem>();
            MarkItem info=new MarkItem();
            info.setGameId(gameStatus.getGameId());
            info.setSelect("5");
            list.add(info);
            callBackPage.setDatalist(list);
           return callBackPage;
         }
        else {
            CallBackPage<MarkItem> callBackPage = new CallBackPage<MarkItem>();
            List<MarkItem> list = new ArrayList<MarkItem>();
            MarkItem info=new MarkItem();
            info.setSelect("6");
            list.add(info);
            callBackPage.setDatalist(list);
            return callBackPage;
        }
    }
    public String getJoinExitContentInf() {
        return null;
    }
}