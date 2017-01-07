package edu.buu.childhood.login.service;

public interface RegisterService {
	
	public boolean isRegistered(String userName);

	public String perfectInformation(String userName, String userPwd,
			String userNickname, int belongingProvince, int belongingCity,
			int belongingDistrict, String community, String email);
	
	public String register(String telNum, double regLatitude,
			double regLongitude);
}
