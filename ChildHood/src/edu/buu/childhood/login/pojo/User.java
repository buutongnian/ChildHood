package edu.buu.childhood.login.pojo;

import edu.buu.childhood.common.C;

/**
 * 2016/6/17
 * @Author Joe
 * 映射T_SYS_USER_REG表中的USER_NAME、USER_PWD和LOGIN_STATUS字段
 */
public class User {
	
	private String userName;
	private String userPwd;
	private String loginStatus=C.status.offline;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getLoginStatus() {
		return loginStatus;
	}
	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}
}
