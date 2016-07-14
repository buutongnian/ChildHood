package edu.buu.childhood.rank.service;

import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.rank.pojo.ranking_gamelist_itembean;
import edu.buu.childhood.rank.pojo.ranking_personlist_itembean;

/**
 * Created by lcc on 2016/7/13.
 */
public interface UserRankService {
    public CallBackPage<ranking_personlist_itembean> getUserRankHeadInf(String result);
    public String getUserRankContentInf();
}
