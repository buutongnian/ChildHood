package edu.buu.childhood.common;

public class Message<T> {
	
	private String messageCode;
	private T messageContent;
		
	public Message(String messageCode, T messageContent) {
		this.messageCode = messageCode;
		this.messageContent = messageContent;
	}
	
	public String getMessageCode() {
		return messageCode;
	}
	
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}
	
	public T getMessageContent() {
		return messageContent;
	}
	
	public void setMessageContent(T messageContent) {
		this.messageContent = messageContent;
	}
}
