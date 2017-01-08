package edu.buu.childhood.rank.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.rank.pojo.GameRank;
import edu.buu.childhood.rank.pojo.ranking_gamelist_itembean;

/**
 * Created by lcc on 2016/7/13.
 */
public class GameRankServiceImpl implements GameRankService {
    Gson json = new Gson();

    public CallBackPage<ranking_gamelist_itembean>getGameRankHeadInf(String result){
        Message message1 = json.fromJson(result, new TypeToken<Message>() {
        }.getType());
        if(C.rank.GAME_RANK_QUERY_SUCCESS.equals(message1.getMessageCode())){
            Message<List<GameRank>> message = json.fromJson(result, new TypeToken<Message<List<GameRank>>>() {
            }.getType());
            CallBackPage<ranking_gamelist_itembean>callBackPage=new CallBackPage<ranking_gamelist_itembean>();
            List<GameRank> gameList = message.getMessageContent();
            List<ranking_gamelist_itembean> list = new ArrayList<ranking_gamelist_itembean>();
            Iterator iter=gameList.iterator();
            while(iter.hasNext()){
                ranking_gamelist_itembean gamelist=new ranking_gamelist_itembean();
                GameRank gamerank=(GameRank)iter.next();
                gamelist.setGameCode(gamerank.getGameCode()+"");
                //gamelist.setGameIcon(gamerank.getGameHead().getGameIcon()+"");
                gamelist.setGameName(gamerank.getGameHead().getGameTitle());
                gamelist.setGameHeat(gamerank.getGameHeat()+"");
                gamelist.setGameIcon(gamerank.getGameHead().getGameIcon());
              //  Log.i("gameHeat",gamerank.getGameHeat()+"kk");
                list.add(gamelist);
            }
            callBackPage.setDatalist(list);
            return callBackPage;
        }
        return null;
    }
    @Override
    public String getGameRankContentInf() {
        return null;
    }

}
