package edu.buu.childhood.game.service;

import edu.buu.childhood.common.Page;
import edu.buu.childhood.game.pojo.GameHead;

public class GameServiceImpl implements GameService {

	@Override
	public Page<GameHead> getGameHeadList(int gameArea, int ageCode,
			int memNumCode, int pageNum) {
		StringBuffer where=new StringBuffer("");
		if(gameArea!=0){
			where.append(" and gameArea="+gameArea);
		}
		if(ageCode!=0){
			where.append(" and ageCode="+ageCode);
		}
		if(memNumCode!=0){
			where.append(" and memNumCode="+memNumCode);
		}
		return null;
	}

}
