package edu.buu.childhood.login.dao;

import edu.buu.childhood.login.pojo.User;

public interface LoginDao {
	
	public User queryByUserName(String userName);
	
	public void update(User user);
	
	public void update(String userName,double lastLatitude,double lastLongitude);
}
