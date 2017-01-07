package edu.buu.childhood.my.dao;

import java.util.List;
import edu.buu.childhood.my.pojo.ChildInf;
import edu.buu.childhood.my.pojo.User;

public interface MyDao {
	public User getUserInf(String userName);

	public void updateUserInfo(String userName, String set);

	public List<ChildInf> getChildInf(String userName);

	public void updateChildInfo(int childInfId, String set);

	public String getHeadImage(String userName);

	public void updateHeadImage(String userName, String userHeadImage);
	
	public void insert(ChildInf childInf);
	
}
