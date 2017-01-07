package edu.buu.childhood.feedback.pojo;

import java.util.Date;

/*
 * 2016/10/09
 * @Author Joe
 * 
 * 映射T_SYS_FEEDBACK表
 */
public class Feedback {
	private int feedbackId;
	private String userName;
	private Date feedbackTime;
	private String feedbackContent;
	private String feedbackModule;
	private String result;
	private Date resultTime;
	private String resultUser;
	private char processible;

	public int getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(int feedbackId) {
		this.feedbackId = feedbackId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getFeedbackTime() {
		return feedbackTime;
	}

	public void setFeedbackTime(Date feedbackTime) {
		this.feedbackTime = feedbackTime;
	}

	public String getFeedbackContent() {
		return feedbackContent;
	}

	public void setFeedbackContent(String feedbackContent) {
		this.feedbackContent = feedbackContent;
	}

	public String getFeedbackModule() {
		return feedbackModule;
	}

	public void setFeedbackModule(String feedbackModule) {
		this.feedbackModule = feedbackModule;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Date getResultTime() {
		return resultTime;
	}

	public void setResultTime(Date resultTime) {
		this.resultTime = resultTime;
	}

	public String getResultUser() {
		return resultUser;
	}

	public void setResultUser(String resultUser) {
		this.resultUser = resultUser;
	}

	public char getProcessible() {
		return processible;
	}

	public void setProcessible(char processible) {
		this.processible = processible;
	}

}
