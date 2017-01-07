package edu.buu.childhood.log.service;

public interface LogService {
	public void writeLoginLog(String userName, double loginLatitude,
			double loginLongitude, String loginIp);

	public void writeADCoveredLog(String userName, int adId);

	public void writeADClickLog(String userName, int adId);
}
