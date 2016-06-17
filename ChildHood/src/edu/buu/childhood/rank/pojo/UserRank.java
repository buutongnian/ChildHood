package edu.buu.childhood.rank.pojo;

import edu.buu.childhood.my.pojo.UserInf;
import edu.buu.childhood.my.pojo.UserRegInf;

/*
 * 2016/6/17
 * @Author Joe
 * 映射T_RANK_USER表
 */
public class UserRank {
	
	private String userName;
	private int likeCount;
	
	private UserRegInf userReg;
	private UserInf userInf;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	public UserRegInf getUserReg() {
		return userReg;
	}
	public void setUserReg(UserRegInf userReg) {
		this.userReg = userReg;
	}
	public UserInf getUserInf() {
		return userInf;
	}
	public void setUserInf(UserInf userInf) {
		this.userInf = userInf;
	}
}
