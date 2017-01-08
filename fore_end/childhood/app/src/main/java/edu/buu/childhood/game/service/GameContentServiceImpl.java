package edu.buu.childhood.game.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.game.pojo.GameContent;
import edu.buu.childhood.game.pojo.ItemBean;

/**
 * Created by lcc on 2016/7/9.
 */
public class GameContentServiceImpl{
    Gson json = new Gson();
    public CallBackPage<ItemBean> getGameContentInf(String result){
        Message message1 = json.fromJson(result, new TypeToken<Message>() {
        }.getType());
       // Log.i("code",message1.getMessageCode());
        if (C.game.GAME_CONTENT_QUERY_SUCCESS.equals(message1.getMessageCode())) {
            Message<GameContent> message = json.fromJson(result, new TypeToken<Message<GameContent>>() {
            }.getType());
            CallBackPage<ItemBean> callBackPage = new CallBackPage<ItemBean>();
            GameContent page = (GameContent) message.getMessageContent();

            //Log.i("page",page.getGameContent());
            List<ItemBean> list = new ArrayList<ItemBean>();
                ItemBean itemBean = new ItemBean();
                itemBean.setGameContent(page.getGameContent());
                itemBean.setGameCode(page.getGameCode());
                itemBean.setSelect("content");
                list.add(itemBean);
            callBackPage.setFalg("gameContent");
            callBackPage.setDatalist(list);
            return callBackPage;
        }
        return null;
    }

    public String getGameContentInf() {
        return null;
    }
}
