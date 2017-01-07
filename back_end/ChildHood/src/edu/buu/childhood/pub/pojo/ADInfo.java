package edu.buu.childhood.pub.pojo;

/**
 * 映射T_PUB_AD
 * 
 * @author joe
 *
 */
public class ADInfo {
	private int adId;
	private String adName;
	private String adInfo;
	private String adImgUrl;
	private String adUrl;
	private int adPriority;
	private char adEnabled;

	public int getAdId() {
		return adId;
	}

	public void setAdId(int adId) {
		this.adId = adId;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public String getAdInfo() {
		return adInfo;
	}

	public void setAdInfo(String adInfo) {
		this.adInfo = adInfo;
	}

	public String getAdImgUrl() {
		return adImgUrl;
	}

	public void setAdImgUrl(String adImgUrl) {
		this.adImgUrl = adImgUrl;
	}

	public String getAdUrl() {
		return adUrl;
	}

	public void setAdUrl(String adUrl) {
		this.adUrl = adUrl;
	}

	public int getAdPriority() {
		return adPriority;
	}

	public void setAdPriority(int adPriority) {
		this.adPriority = adPriority;
	}

	public char getAdEnabled() {
		return adEnabled;
	}

	public void setAdEnabled(char adEnabled) {
		this.adEnabled = adEnabled;
	}

}
