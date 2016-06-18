package edu.buu.childhood.status.pojo;

import java.io.Serializable;

/*
 * 2016/6/17
 * @Author Joe
 * 映射V_STATUS_USER_CANJOIN视图
 */
public class UserCanJoin implements Serializable{
	
	/**
	 * UserCanJoin类序列化唯一编码
	 */
	private static final long serialVersionUID = -5877537379976311980L;
	
	private String userName;
	private double lastLatitude;
	private double lastLongitude;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public double getLastLatitude() {
		return lastLatitude;
	}
	public void setLastLatitude(double lastLatitude) {
		this.lastLatitude = lastLatitude;
	}
	public double getLastLongitude() {
		return lastLongitude;
	}
	public void setLastLongitude(double lastLongitude) {
		this.lastLongitude = lastLongitude;
	}
}
