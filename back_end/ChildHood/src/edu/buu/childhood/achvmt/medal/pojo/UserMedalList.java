package edu.buu.childhood.achvmt.medal.pojo;

import java.io.Serializable;
import java.util.Date;

/*
 * 2016/9/27
 * @Author Joe
 * 
 * 映射V_ACHVMT_USER_MEDAL视图
 */
public class UserMedalList implements Serializable {
	/**
	 * UserMedalList序列化ID
	 */
	private static final long serialVersionUID = -4365525904253516524L;
	private String userName;// 用户名
	private int medalId;// 勋章ID
	private String medalDescribe;// 勋章简介
	private Date getDate;// 勋章获得时间戳
	private String medalName;// 勋章名称

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getMedalId() {
		return medalId;
	}

	public void setMedalId(int medalId) {
		this.medalId = medalId;
	}

	public String getMedalDescribe() {
		return medalDescribe;
	}

	public void setMedalDescribe(String medalDescribe) {
		this.medalDescribe = medalDescribe;
	}

	public Date getGetDate() {
		return getDate;
	}

	public void setGetDate(Date getDate) {
		this.getDate = getDate;
	}

	public String getMedalName() {
		return medalName;
	}

	public void setMedalName(String medalName) {
		this.medalName = medalName;
	}

}
