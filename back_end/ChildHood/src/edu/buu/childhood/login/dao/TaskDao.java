package edu.buu.childhood.login.dao;

import java.util.List;
import edu.buu.childhood.login.pojo.UserLogin;

public interface TaskDao {
	public List<UserLogin> queryAllUsers();

	public void update(String userName, String loginStatus);
}
