package edu.buu.childhood.rank.service;

import edu.buu.childhood.common.Page;
import edu.buu.childhood.rank.pojo.GameRank;
import edu.buu.childhood.rank.pojo.UserRank;

public interface RankService {
	public Page<GameRank> getGameRank(String userName,int pageNum);
	public Page<UserRank> getUserRank(String userName,int pageNum);
}
