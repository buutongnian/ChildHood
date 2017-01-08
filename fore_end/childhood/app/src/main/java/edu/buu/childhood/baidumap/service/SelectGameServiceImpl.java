package edu.buu.childhood.baidumap.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.buu.childhood.MemorySelectGame;
import edu.buu.childhood.baidumap.pojo.MarkItem;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.common.Page;
import edu.buu.childhood.game.pojo.GameHead;

/**
 * Created by lcc on 2016/7/9.
 */
public class SelectGameServiceImpl {
    Gson json = new Gson();

    public CallBackPage<MarkItem> getGameHeadInf(String result) {
        Message<Page<GameHead>> message = json.fromJson(result, new TypeToken<Message<Page<GameHead>>>() {
        }.getType());
        if (C.game.GAME_HEAD_QUERY_SUCCESS.equals(message.getMessageCode())) {
            CallBackPage<MarkItem> callBackPage = new CallBackPage<MarkItem>();
            Page<GameHead> page = message.getMessageContent();
            List<MarkItem> list = new ArrayList<MarkItem>();
            Iterator iter = page.getDataList().iterator();
            while (iter.hasNext()) {
                MarkItem itemBean = new MarkItem();
                GameHead gameHead = (GameHead) iter.next();
                itemBean.setSelectGameTitle(gameHead.getGameTitle() + "");
                itemBean.setGameSelectCode(gameHead.getGameCode());
                itemBean.setSelect("6");
                list.add(itemBean);
            }
            callBackPage.setPageSize(page.getPageSize());
            callBackPage.setDatalist(list);
            MemorySelectGame.getInstance().setCbp1(callBackPage);
            return callBackPage;
        }
        return null;
    }


    public String getGameContentInf() {
        return null;
    }
}
