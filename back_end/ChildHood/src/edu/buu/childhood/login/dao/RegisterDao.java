package edu.buu.childhood.login.dao;

import edu.buu.childhood.my.pojo.User;

public interface RegisterDao {

	public User queryByUserName(String userName);

	public User queryByTel(String tel);

	public boolean insert(User userReg);

	public void update(String userName, String userPwd, String userNickname,
			int belongingProvince, int belongingCity, int belongingDistrict,
			String community, String email);
}
