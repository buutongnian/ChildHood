package edu.buu.childhood.my.pojo;

import java.util.Date;

/**
 * 2016/6/16
 * @Author Joe
 * @note 映射T_SYS_USER_REG表
 */
public class UserRegInf {
	
	private String userName;
	private Date regDate;
	private double regLatitude;
	private double regLongitude;
	private int belongingArea;
	private int liveCommunity;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public double getRegLatitude() {
		return regLatitude;
	}
	public void setRegLatitude(double regLatitude) {
		this.regLatitude = regLatitude;
	}
	public double getRegLongitude() {
		return regLongitude;
	}
	public void setRegLongitude(double regLongitude) {
		this.regLongitude = regLongitude;
	}
	public int getBelongingArea() {
		return belongingArea;
	}
	public void setBelongingArea(int belongingArea) {
		this.belongingArea = belongingArea;
	}
	public int getLiveCommunity() {
		return liveCommunity;
	}
	public void setLiveCommunity(int liveCommunity) {
		this.liveCommunity = liveCommunity;
	}
}
