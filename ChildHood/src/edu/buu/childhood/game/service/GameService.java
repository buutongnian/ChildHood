package edu.buu.childhood.game.service;

import edu.buu.childhood.common.Page;
import edu.buu.childhood.game.pojo.GameHead;

public interface GameService {
	public Page<GameHead> getGameHeadList(int gameArea,int ageCode,int memNumCode,int pageNum);
}
