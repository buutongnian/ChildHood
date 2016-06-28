package edu.buu.childhood.game.dao;

import edu.buu.childhood.common.Page;
import edu.buu.childhood.game.pojo.GameContent;
import edu.buu.childhood.game.pojo.GameHead;

public interface GameDao {
	public Page<GameHead> queryGameHeadInf(String where,int pageNum,int pageSize);
	public GameContent queryGameContentById(int gameCode);
}
