package edu.buu.childhood.login.service;

public interface LoginService {

	public String loginCheck(String userName, String userPwd);

	public boolean changeLoginStatus(String userName, String loginStatus);

	public boolean updateLoginInf(String userName, double lastLatitude,
			double lastLongitude);

	public String statusCheck(String userName);

}
