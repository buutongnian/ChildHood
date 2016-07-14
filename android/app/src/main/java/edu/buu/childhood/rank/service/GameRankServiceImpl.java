package edu.buu.childhood.rank.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.common.Page;
import edu.buu.childhood.rank.pojo.GameRank;
import edu.buu.childhood.rank.pojo.ranking_gamelist_itembean;

/**
 * Created by lcc on 2016/7/13.
 */
public class GameRankServiceImpl implements GameRankService {
    Gson json = new Gson();

    public CallBackPage<ranking_gamelist_itembean>getGameRankHeadInf(String result){
        Message<Page<GameRank>> message = json.fromJson(result, new TypeToken<Message<Page<GameRank>>>() {
        }.getType());
        if(C.rank.GAME_RANK_QUERY_SUCCESS.equals(message.getMessageCode())){
            CallBackPage<ranking_gamelist_itembean>callBackPage=new CallBackPage<ranking_gamelist_itembean>();
            Page<GameRank> page = (Page<GameRank>) message.getMessageContent();

            List<ranking_gamelist_itembean> list = new ArrayList<ranking_gamelist_itembean>();
            Iterator iter=page.getDataList().iterator();
            while(iter.hasNext()){
                ranking_gamelist_itembean gamelist=new ranking_gamelist_itembean();
                GameRank gamerank=(GameRank)iter.next();
                gamelist.setGameCode(gamerank.getGameCode()+"");
                //gamelist.setGameIcon(gamerank.getGameHead().getGameIcon()+"");
                gamelist.setGameName(gamerank.getGameHead().getGametTitle());
                gamelist.setGameHeat(gamerank.getGameHeat()+"");
                list.add(gamelist);

            }
            callBackPage.setDatalist(list);
            callBackPage.setCurrentPage(page.getCurrentPage());
            callBackPage.setPageSize(page.getPageSize());
            callBackPage.setTotalPage(page.getTotalPage());
            Log.d("page+message",callBackPage+"");
            return callBackPage;
        }
        return null;
    }
    @Override
    public String getGameRankContentInf() {
        return null;
    }

}
