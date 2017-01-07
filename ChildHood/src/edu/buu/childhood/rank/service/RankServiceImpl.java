package edu.buu.childhood.rank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import edu.buu.childhood.common.Page;
import edu.buu.childhood.my.pojo.UserRegInf;
import edu.buu.childhood.rank.dao.RankDao;
import edu.buu.childhood.rank.pojo.GameRank;
import edu.buu.childhood.rank.pojo.UserRank;

/**
 * 2016/06/28
 * 
 * @author tt
 * @note 游戏排行以及孩子王排行业务操作
 * 
 *
 */

public class RankServiceImpl implements RankService {

	@Autowired
	private RankDao rankDao;
	
	/**
	 * 
	 *2016/06/28
	 *@author tt
	 *@note 查询用户注册所在区域的游戏排行
	 *@param pageNum 当前页的页码
	 * 
	 */
	@Override
	@Transactional
	public Page<GameRank> getGameRank(String userName, int pageNum) {
		UserRegInf userFromDB = rankDao.getUserInfo(userName);
		if (userFromDB != null) {
			Page<GameRank> gameRank = rankDao.getGameRank(
					userFromDB.getBelongingArea(), pageNum);
			return gameRank;
		} else {
			return null;
		}
	}
	/**
	 * 
	 * 2016/06/28
	 * @author tt
	 * @note 查询用户注册所在地区孩子王排行榜
	 * 
	 */
	@Override
	@Transactional
	public Page<UserRank> getUserRank(String userName, int pageNum) {
		UserRegInf userFromDB = rankDao.getUserInfo(userName);
		if(userFromDB!=null){
			Page<UserRank> userRank=rankDao.getUserRank(userFromDB.getBelongingArea(), pageNum);
			return userRank;
		}else{
			return null;
		}
	}
}
