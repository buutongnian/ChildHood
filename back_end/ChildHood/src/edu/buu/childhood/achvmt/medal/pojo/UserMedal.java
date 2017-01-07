package edu.buu.childhood.achvmt.medal.pojo;

import java.io.Serializable;
import java.util.Date;

/*
 * 2016/9/27
 * @Author Joe
 * 
 * 映射T_ACHVMT_USER_MEDAL_LIST表
 */
public class UserMedal implements Serializable {
	/**
	 * UserMedal序列化ID
	 */
	private static final long serialVersionUID = -4416372194719126149L;
	private String userName;
	private int medalId;
	private Date getDate;
	private char enable;

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

	public Date getGetDate() {
		return getDate;
	}

	public void setGetDate(Date getDate) {
		this.getDate = getDate;
	}

	public char getEnable() {
		return enable;
	}

	public void setEnable(char enable) {
		this.enable = enable;
	}

}
