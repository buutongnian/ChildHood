package edu.buu.childhood.achvmt.event.pojo;

import java.io.Serializable;
import java.util.Date;

/*
 * 2016/9/27
 * @Author Joe
 * 
 * 映射V_ACHVMT_USER_EVENT视图
 */
public class UserEventList implements Serializable {
	/**
	 * UserEventList序列化ID
	 */
	private static final long serialVersionUID = -7742905461980603713L;
	private String userName;// 用户名
	private Date eventDate;// 事件日期
	private String templateStr;// 事件模板字符串
	private String eventParams;// 事件变量Json字符串
	private String day;//玩家加入天数

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getTemplateStr() {
		return templateStr;
	}

	public void setTemplateStr(String templateStr) {
		this.templateStr = templateStr;
	}

	public String getEventParams() {
		return eventParams;
	}

	public void setEventParams(String eventParams) {
		this.eventParams = eventParams;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

}
