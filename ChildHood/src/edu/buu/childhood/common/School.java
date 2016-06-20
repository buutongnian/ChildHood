package edu.buu.childhood.common;

/**
 * 2016/6/17
 * @Author Joe
 * 映射T_PUB_SCHOOL表
 */
public class School {
	private int schoolCode;
	private int belonging;
	private String schoolName;

	private District district;//关联区县映射类District，实现一对一关系

	public int getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(int schoolCode) {
		this.schoolCode = schoolCode;
	}

	public int getBelonging() {
		return belonging;
	}

	public void setBelonging(int belonging) {
		this.belonging = belonging;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}
}
