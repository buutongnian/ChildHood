package edu.buu.childhood.status.pojo;

public class ComputeCanInvite {
	private String userName;
	private double lastLatitude;
	private double lastLogitude;
	private int distance;

	public ComputeCanInvite(String userName, double lastLatitude,
			double lastLogitude, int distance) {
		this.userName = userName;
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
