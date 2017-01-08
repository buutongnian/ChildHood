package edu.buu.childhood.my.pojo;

import edu.buu.childhood.common.C;

/**
 * 2016/7/7
 * 
 * @Author Joe
 * @note 映射T_FIND_REG表
 * 
 *       2016/9/10 添加sex,message两个字段
 */
public class FindReg {
	private String userName;
	private int age;
	private char sex;
	private double youngLatitude;
	private double youngLongitude;
	private String youngNickname;
	private String youngRoad;
	private String message;
	private char enable = C.def.ENABLED;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public double getYoungLatitude() {
		return youngLatitude;
	}

	public void setYoungLatitude(double youngLatitude) {
		this.youngLatitude = youngLatitude;
	}

	public double getYoungLongitude() {
		return youngLongitude;
	}

	public void setYoungLongitude(double youngLongitude) {
		this.youngLongitude = youngLongitude;
	}

	public String getYoungNickname() {
		return youngNickname;
	}

	public void setYoungNickname(String youngNickname) {
		this.youngNickname = youngNickname;
	}

	public String getYoungRoad() {
		return youngRoad;
	}

	public void setYoungRoad(String youngRoad) {
		this.youngRoad = youngRoad;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public char getEnable() {
		return enable;
	}

	public void setEnable(char enable) {
		this.enable = enable;
	}

	@Override
	public String toString() {
		return "FindReg [userName=" + userName + ", age=" + age + ", sex="
				+ sex + ", youngLatitude=" + youngLatitude
				+ ", youngLongitude=" + youngLongitude + ", youngNickname="
				+ youngNickname + ", youngRoad=" + youngRoad + ", message="
				+ message + ", enable=" + enable + "]";
	}

}
