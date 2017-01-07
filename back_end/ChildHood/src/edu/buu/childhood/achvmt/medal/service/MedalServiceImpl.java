package edu.buu.childhood.achvmt.medal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import edu.buu.childhood.achvmt.medal.dao.MedalDao;
import edu.buu.childhood.achvmt.medal.pojo.UserMedalList;

public class MedalServiceImpl implements MedalService {

	@Autowired
	private MedalDao medalDao;

	@Override
	@Transactional
	public List<UserMedalList> getUserMedalList(String userName) {
		return medalDao.queryUserMedalList(userName);
	}

}
