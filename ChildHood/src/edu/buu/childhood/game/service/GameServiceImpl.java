package edu.buu.childhood.game.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Page;
import edu.buu.childhood.game.dao.GameDao;
import edu.buu.childhood.game.pojo.GameContent;
import edu.buu.childhood.game.pojo.GameHead;
/**
 * 2016/6/28
 * 游戏规则获取服务实现类
 * @author joe
 */
public class GameServiceImpl implements GameService {
	
	private Logger logger=Logger.getLogger(this.getClass());
	
	@Autowired
	private GameDao gameDao;

	/**
	 * 获取游戏简介，用于App端游戏列表显示
	 * @param gameArea 游戏所属大区，查询筛选条件（可选）
	 * @param ageCode 游戏适合年龄段，查询筛选条件（可选）
	 * @param memNumCode 游戏适合人数段，查询筛选条件（可选）
	 * @param pageNum 请求页码，用于分页
	 * @return 当前页信息+游戏头信息
	 */
	@Override
	@Transactional
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

	/**
	 * @param gameCode 游戏编码，在游戏详情页面通过游戏编码请求游戏详细内容
	 * @return 游戏详细内容
	 */
	@Override
	@Transactional
	public GameContent getGameConntent(int gameCode) {
		logger.info("游戏编码：["+gameCode+"]被访问详情");
		return gameDao.queryGameContentById(gameCode);
	}

}
