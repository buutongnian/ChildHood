package edu.buu.childhood.login.dao;

import edu.buu.childhood.login.pojo.VCode;

public interface PasswordDao {
	public VCode queryByTel(String telnum);

	public void insert(VCode vcode);
	
	public void update(VCode vcode);
	
	public int update(String userName,String userPwd);
}
