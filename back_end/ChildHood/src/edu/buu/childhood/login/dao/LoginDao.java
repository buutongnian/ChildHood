package edu.buu.childhood.login.dao;

import edu.buu.childhood.login.pojo.UserLogin;

public interface LoginDao {

	public UserLogin queryByUserName(String userName);

	public void update(UserLogin user);

	public void update(String userName, double lastLatitude,
			double lastLongitude);
}
