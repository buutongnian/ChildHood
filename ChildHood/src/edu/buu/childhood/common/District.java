package edu.buu.childhood.common;

/*
 * 2016/6/17
 * @Author Joe
 * 映射T_PUB_DISTRICT表
 */
public class District {
	private int districtCode;
	private int belonging;
	private String districtName;
	
	private City city;//关联市映射类City，实现一对一关系

	public int getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(int districtCode) {
		this.districtCode = districtCode;
	}

	public int getBelonging() {
		return belonging;
	}

	public void setBelonging(int belonging) {
		this.belonging = belonging;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
}
