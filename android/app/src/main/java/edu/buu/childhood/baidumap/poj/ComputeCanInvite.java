package edu.buu.childhood.baidumap.poj;

public class ComputeCanInvite {
	private String userName;
	private String userHeadimage;
	private String userNickname;
	private double lastLatitude;
	private double lastLogitude;
	private int distance;
	
	public ComputeCanInvite(String userName, String userHeadimage,
			String userNickname, double lastLatitude, double lastLogitude,
			int distance) {
		this.userName = userName;
		this.userHeadimage = userHeadimage;
		this.userNickname = userNickname;
		this.lastLatitude = lastLatitude;
		this.lastLogitude = lastLogitude;
		this.distance = distance;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserHeadimage() {
		return userHeadimage;
	}

	public void setUserHeadimage(String userHeadimage) {
		this.userHeadimage = userHeadimage;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public double getLastLatitude() {
		return lastLatitude;
	}

	public void setLastLatitude(double lastLatitude) {
		this.lastLatitude = lastLatitude;
	}

	public double getLastLogitude() {
		return lastLogitude;
	}

	public void setLastLogitude(double lastLogitude) {
		this.lastLogitude = lastLogitude;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
}
