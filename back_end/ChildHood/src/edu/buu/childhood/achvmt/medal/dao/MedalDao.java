package edu.buu.childhood.achvmt.medal.dao;

import java.util.List;

import edu.buu.childhood.achvmt.medal.pojo.UserMedalList;

public interface MedalDao {
	public List<UserMedalList> queryUserMedalList(String userName);
}
