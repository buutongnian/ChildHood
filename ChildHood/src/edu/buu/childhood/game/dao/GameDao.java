package edu.buu.childhood.game.dao;

import java.util.List;

import edu.buu.childhood.game.pojo.GameContent;
import edu.buu.childhood.game.pojo.GameHead;

public interface GameDao {
	public List<GameHead> queryGameHeadInf(int gameArea,int ageCode,int memNumCode,int pageNum,int pageSize);
	public GameContent queryGameContentById(int gameCode);
}
