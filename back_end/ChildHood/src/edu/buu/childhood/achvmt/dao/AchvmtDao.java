package edu.buu.childhood.achvmt.dao;

import edu.buu.childhood.achvmt.pojo.UserHistoryGameList;
import edu.buu.childhood.achvmt.pojo.UserKPI;
import edu.buu.childhood.common.Page;

public interface AchvmtDao {
	public UserKPI queryUserKPI(String userName);

	public Page<UserHistoryGameList> queryUserGameList(String userName,
			int pageNum,int pageSize);
}
