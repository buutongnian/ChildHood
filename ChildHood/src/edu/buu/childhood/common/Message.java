package edu.buu.childhood.common;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unchecked")
public class Message<T> {
	
	private String messageCode;
	private Set<T> messageContent=new HashSet<T>();
		
	public Message(String messageCode, Object messageContent) {
		this.messageCode = messageCode;
		this.messageContent.add((T) messageContent);
	}
	
	public String getMessageCode() {
		return messageCode;
	}
	
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}
	
	public Object getMessageContent() {
		return messageContent.iterator().next();
	}
	
	public void setMessageContent(Object messageContent) {
		this.messageContent.add((T) messageContent);
	}
}
