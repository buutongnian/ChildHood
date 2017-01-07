package edu.buu.childhood.achvmt.event.service;

import edu.buu.childhood.achvmt.event.pojo.UserEventList;
import edu.buu.childhood.common.Page;

public interface EventService {
	public Page<UserEventList> getUserFootPrint(String userName, int pageNum);
}
