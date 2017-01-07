package edu.buu.childhood.achvmt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import edu.buu.childhood.achvmt.dao.AchvmtDao;
import edu.buu.childhood.achvmt.pojo.UserHistoryGameList;
import edu.buu.childhood.achvmt.pojo.UserKPI;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Page;

public class AchvmtServiceImpl implements AchvmtService {

	@Autowired
	private AchvmtDao achvmtDao;

	@Override
	@Transactional
	public UserKPI getUserKPI(String userName) {
		return achvmtDao.queryUserKPI(userName);
	}

	@Override
	public Page<UserHistoryGameList> getUserHistoryGameList(String userName,
			int pageNum) {
		return achvmtDao.queryUserGameList(userName, pageNum, C.def.PAGE_SIZE);
	}
}
