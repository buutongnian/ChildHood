package edu.buu.childhood.common;

/**
 * 2016/6/17
 * @Author Joe
 * @note 映射T_PUB_COMMUNITY表
 */
public class Community {
	private int communityCode;
	private int belonging;
	private String communityName;
	
	private District district;//关联区县映射类District，实现一对一关系

	public int getCommunityCode() {
		return communityCode;
	}

	public void setCommunityCode(int communityCode) {
		this.communityCode = communityCode;
	}

	public int getBelonging() {
		return belonging;
	}

	public void setBelonging(int belonging) {
		this.belonging = belonging;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}
}
