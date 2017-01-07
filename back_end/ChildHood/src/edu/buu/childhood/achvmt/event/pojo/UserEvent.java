package edu.buu.childhood.achvmt.event.pojo;

import java.util.Date;
/*
 * 2016/9/27
 * @Author Joe
 * 
 * 映射T_ACHVMT_USER_EVENT_LIST表
 */
public class UserEvent {
	private int listId;
	private String userName;
	private Date eventDate;
	private int eventTemplate;
	private String eventTemplateName;
	private String eventParams;

	public int getListId() {
		return listId;
	}

	public void setListId(int listId) {
		this.listId = listId;
	}

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

	public int getEventTemplate() {
		return eventTemplate;
	}

	public void setEventTemplate(int eventTemplate) {
		this.eventTemplate = eventTemplate;
	}

	public String getEventTemplateName() {
		return eventTemplateName;
	}

	public void setEventTemplateName(String eventTemplateName) {
		this.eventTemplateName = eventTemplateName;
	}

	public String getEventParams() {
		return eventParams;
	}

	public void setEventParams(String eventParams) {
		this.eventParams = eventParams;
	}

}
