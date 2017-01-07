package edu.buu.childhood.achvmt.event.dao;

import edu.buu.childhood.achvmt.event.pojo.UserEventList;
import edu.buu.childhood.common.Page;

public interface EventDao {
	public Page<UserEventList> queryUserFootPrint(String userName, int pageNum,
			int pageSize);
}
