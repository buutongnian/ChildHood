package edu.buu.childhood.util;

import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.packet.Message;

public abstract class SmackPushUtil {
	public static void sendMessageToUser(String user,String message){
		try {
			SmackUtil.getChat(user).sendMessage(message);
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
	}
	
	public static void sendMessageToUser(String user,Message message){
		try {
			SmackUtil.getChat(user).sendMessage(message);
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
	}
}
