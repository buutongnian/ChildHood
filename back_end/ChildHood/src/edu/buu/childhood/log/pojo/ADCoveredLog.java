package edu.buu.childhood.log.pojo;

import java.util.Date;

public class ADCoveredLog {
	private int logId;
	private int adId;
	private Date coveredTime;
	private String userName;

	public int getLogId() {
		return logId;
	}

	public void setLogId(int logId) {
		this.logId = logId;
	}

	public int getAdId() {
		return adId;
	}

	public void setAdId(int adId) {
		this.adId = adId;
	}

	public Date getCoveredTime() {
		return coveredTime;
	}

	public void setCoveredTime(Date coveredTime) {
		this.coveredTime = coveredTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
