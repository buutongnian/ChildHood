package edu.buu.childhood.login.dao;

import java.util.List;
import edu.buu.childhood.login.pojo.User;

public interface TaskDao {
	public List<User> queryAllUsers();
	
	public void update(String userName,String loginStatus);
}
