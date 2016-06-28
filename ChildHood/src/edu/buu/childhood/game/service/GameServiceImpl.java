package edu.buu.childhood.game.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Page;
import edu.buu.childhood.game.dao.GameDao;
import edu.buu.childhood.game.pojo.GameContent;
import edu.buu.childhood.game.pojo.GameHead;

public class GameServiceImpl implements GameService {
	
	private Logger logger=Logger.getLogger(this.getClass());
	
	@Autowired
	private GameDao gameDao;

	@Override
	public Page<GameHead> getGameHeadPage(int gameArea, int ageCode,
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
		return gameDao.queryGameHeadInf(where.toString(), pageNum, C.def.PAGE_SIZE);
	}

	@Override
	public GameContent getGameConntent(int gameCode) {
		logger.info("游戏编码："+gameCode+"被访问详情");
		return gameDao.queryGameContentById(gameCode);
	}

}
