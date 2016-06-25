package edu.buu.childhood.login.dao;

import edu.buu.childhood.login.pojo.User;
import edu.buu.childhood.my.pojo.ChildInf;
import edu.buu.childhood.my.pojo.ParentInf;
import edu.buu.childhood.my.pojo.UserInf;
import edu.buu.childhood.my.pojo.UserRegInf;

public interface RegisterDao {
	
	public User queryByUserName(String userName);
	
	public void insert(UserRegInf userRegInf,ParentInf parentInf,UserInf userInf,ChildInf childInf);
	
}
