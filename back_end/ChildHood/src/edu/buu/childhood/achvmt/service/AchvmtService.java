package edu.buu.childhood.achvmt.service;

import edu.buu.childhood.achvmt.pojo.UserHistoryGameList;
import edu.buu.childhood.achvmt.pojo.UserKPI;
import edu.buu.childhood.common.Page;

public interface AchvmtService {
	public UserKPI getUserKPI(String userName);

	public Page<UserHistoryGameList> getUserHistoryGameList(String userName,
			int pageNum);
}
