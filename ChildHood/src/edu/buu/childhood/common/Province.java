package edu.buu.childhood.common;

/*
 * 2016/6/17
 * @Author Joe
 * 映射T_PUB_PROVINCE表
 */
public class Province {
	
	private int provinceCode;
	private int belonging;
	private String provinceName;
	
	private Area area;//关联大区映射类Area，实现一对一关系

	public int getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(int provinceCode) {
		this.provinceCode = provinceCode;
	}

	public int getBelonging() {
		return belonging;
	}

	public void setBelonging(int belonging) {
		this.belonging = belonging;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
}
