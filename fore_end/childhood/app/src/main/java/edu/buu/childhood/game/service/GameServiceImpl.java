package edu.buu.childhood.game.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.buu.childhood.MemoryCache;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.common.Page;
import edu.buu.childhood.game.pojo.GameHead;
import edu.buu.childhood.game.pojo.ItemBean;

/**
 * Created by lcc on 2016/7/9.
 */
public class GameServiceImpl implements GameService {
    Gson json = new Gson();

    @Override
    public CallBackPage<ItemBean> getGameHeadInf(String result) {
        if (result != null) {
            Message<Page<GameHead>> message = json.fromJson(result, new TypeToken<Message<Page<GameHead>>>() {
            }.getType());
            if (C.game.GAME_HEAD_QUERY_SUCCESS.equals(message.getMessageCode())) {
                CallBackPage<ItemBean> callBackPage = new CallBackPage<ItemBean>();
                Page<GameHead> page = (Page<GameHead>) message.getMessageContent();
                List<ItemBean> list = new ArrayList<ItemBean>();
                if (page.getDataList() != null) {
                    Iterator iter = page.getDataList().iterator();
                    while (iter.hasNext()) {
                        ItemBean itemBean = new ItemBean();
                        GameHead gameHead = (GameHead) iter.next();
                        itemBean.setGameTitle(gameHead.getGameTitle());
                        itemBean.setAgeRank(String.valueOf(gameHead.getAgeCode()));
                        itemBean.setGameSynopsis(String.valueOf(gameHead.getGameSynopsis()));
                        itemBean.setMemNumSize(String.valueOf(gameHead.getMemNumCode()));
                        itemBean.setImage(gameHead.getGameScore());
                        itemBean.setGameCode(gameHead.getGameCode());
                        itemBean.setImageUrl(gameHead.getGameImage());
                        itemBean.setGameTitle(gameHead.getGameTitle());
                        list.add(itemBean);
                    }
                    callBackPage.setDatalist(list);
                    callBackPage.setCurrentPage(page.getCurrentPage());
                    callBackPage.setPageSize(page.getPageSize());
                    callBackPage.setTotalPage(page.getTotalPage());
                    MemoryCache.getInstance().setCbp1(callBackPage);
                    return callBackPage;
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public String getGameContentInf() {
        return null;
    }
}
