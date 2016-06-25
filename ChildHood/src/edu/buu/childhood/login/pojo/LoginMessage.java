package edu.buu.childhood.login.pojo;

public class LoginMessage {
	
	private String messageCode;
	private String messageContent;
		
	public LoginMessage(String messageCode, String messageContent) {
		this.messageCode = messageCode;
		this.messageContent = messageContent;
	}
	
	public String getMessageCode() {
		return messageCode;
	}
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
}
