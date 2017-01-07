package edu.buu.childhood.log.pojo;

import java.util.Date;

/*
 * 2016/10/10
 * @Author Joe
 * 
 * 映射T_SYS_LOG_LOGIN表
 */
public class LoginLog {
	private int logId;
	private String userName;
	private double loginLatitude;
	private double loginLongitude;
	private Date loginTime;
	private String loginIp;

	public int getLogId() {
		return logId;
	}

	public void setLogId(int logId) {
		this.logId = logId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public double getLoginLatitude() {
		return loginLatitude;
	}

	public void setLoginLatitude(double loginLatitude) {
		this.loginLatitude = loginLatitude;
	}

	public double getLoginLongitude() {
		return loginLongitude;
	}

	public void setLoginLongitude(double loginLongitude) {
		this.loginLongitude = loginLongitude;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

}
