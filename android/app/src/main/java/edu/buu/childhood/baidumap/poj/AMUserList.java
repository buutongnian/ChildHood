package edu.buu.childhood.baidumap.poj;

public class AMUserList {
	private double lastLatitude;
	private double lastLogitude;
	private int distance;

	public AMUserList(double lastLatitude, double lastLogitude, int distance) {
		super();
		this.lastLatitude = lastLatitude;
		this.lastLogitude = lastLogitude;
		this.distance = distance;
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
