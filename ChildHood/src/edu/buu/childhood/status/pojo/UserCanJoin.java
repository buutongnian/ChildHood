package edu.buu.childhood.status.pojo;

/*
 * 2016/6/17
 * @Author Joe
 * 映射V_STATUS_USER_CANJOIN视图
 */
public class UserCanJoin {
	
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