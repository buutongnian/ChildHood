package edu.buu.childhood.find.pojo;

import java.io.Serializable;

/**
 * 2016/7/7
 * 
 * @Author Joe
 * @note 映射V_FIND_RESULT表
 */
public class FindResult implements Serializable {
	/**
	 * FindResult序列化ID
	 */
	private static final long serialVersionUID = -4198651840540055091L;
	private String userName;
	private String matchUser;
	private int age;
	private char sex;
	private String youngNickname;
	private String youngRoad;
	private String message;
	private String userHeadimage;
	private String currentCity;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMatchUser() {
		return matchUser;
	}

	public void setMatchUser(String matchUser) {
		this.matchUser = matchUser;
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

	public String getUserHeadimage() {
		return userHeadimage;
	}

	public void setUserHeadimage(String userHeadimage) {
		this.userHeadimage = userHeadimage;
	}

	public String getCurrentCity() {
		return currentCity;
	}

	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}

}
