package edu.buu.childhood.achvmt.medal.service;

import java.util.List;

import edu.buu.childhood.achvmt.medal.pojo.UserMedalList;

public interface MedalService {
	public List<UserMedalList> getUserMedalList(String userName);
}
