package edu.buu.childhood.rank.service;

import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.rank.pojo.ranking_gamelist_itembean;
/**
 * Created by lcc on 2016/7/13.
 */
public interface GameRankService {
    public CallBackPage<ranking_gamelist_itembean> getGameRankHeadInf(String result);
    public String getGameRankContentInf();
}
