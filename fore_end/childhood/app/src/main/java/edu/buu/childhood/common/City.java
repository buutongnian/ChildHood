package edu.buu.childhood.common;

/**
 * 2016/6/17
 * @Author Joe
 * @note 映射T_PUB_CITY表
 */
public class City {
	private int cityCode;
	private int belonging;
	private String cityName;
	
	private Province province;//关联省映射类Province，实现一对一关系

	public int getCityCode() {
		return cityCode;
	}

	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}

	public int getBelonging() {
		return belonging;
	}

	public void setBelonging(int belonging) {
		this.belonging = belonging;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}
}
