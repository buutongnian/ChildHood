package edu.buu.childhood.rank.pojo;

import java.io.Serializable;

import edu.buu.childhood.my.pojo.UserInf;
import edu.buu.childhood.my.pojo.UserRegInf;

/**
 * 2016/6/17
 * @Author Joe
 * @note 映射T_RANK_USER表
 */
public class UserRank implements Serializable{
	
	/**
	 * UserRank类序列化唯一编码
	 */
	private static final long serialVersionUID = 8591872515045105387L;
	
	private String userName;
	private int likeCount;
	
	private UserRegInf userRegInf;
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
	public UserRegInf getUserRegInf() {
		return userRegInf;
	}
	public void setUserRegInf(UserRegInf userRegInf) {
		this.userRegInf = userRegInf;
	}
	public UserInf getUserInf() {
		return userInf;
	}
	public void setUserInf(UserInf userInf) {
		this.userInf = userInf;
	}
}
