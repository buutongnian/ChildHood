package edu.buu.childhood.achvmt.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import edu.buu.childhood.achvmt.event.dao.EventDao;
import edu.buu.childhood.achvmt.event.pojo.UserEventList;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Page;

public class EventServiceImpl implements EventService {

	@Autowired
	private EventDao eventDao;

	@Override
	@Transactional
	public Page<UserEventList> getUserFootPrint(String userName, int pageNum) {
		return eventDao.queryUserFootPrint(userName, pageNum,
				C.def.FOOTPRINT_PAGE_SIZE);
	}

}
