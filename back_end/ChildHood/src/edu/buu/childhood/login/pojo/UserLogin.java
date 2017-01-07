package edu.buu.childhood.login.pojo;

import edu.buu.childhood.common.C;

/**
 * 2016/9/26
 * 
 * @Author Joe
 * @note 映射T_SYS_USER表中的USER_NAME、USER_PWD和LOGIN_STATUS字段
 */
public class UserLogin {

	private String userName;
	private String userPwd;
	private String loginStatus = C.status.OFFLINE;

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
