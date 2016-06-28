package edu.buu.childhood.rank.dao;

import edu.buu.childhood.common.Page;
import edu.buu.childhood.my.pojo.UserRegInf;
import edu.buu.childhood.rank.pojo.GameRank;
import edu.buu.childhood.rank.pojo.UserRank;

public interface RankDao {
	public UserRegInf getUserInfo(String userName);
	
	public Page<GameRank> getGameRank(int belongingArea,int pageNum);
	
	public Page<UserRank> getUserRank(int belongingArea,int pageNum);
}
