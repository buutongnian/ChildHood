package edu.buu.childhood.game.service;

import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.game.pojo.ItemBean;

/**
 * Created by lcc on 2016/7/9.
 */
public interface GameService {
    public CallBackPage<ItemBean> getGameHeadInf(String result);
    public String getGameContentInf();
}
