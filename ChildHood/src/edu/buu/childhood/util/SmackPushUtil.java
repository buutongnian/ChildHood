package edu.buu.childhood.util;

import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.packet.Message;

/**
 * 2016/6/19
 * @author joe
 * @note 通过Smack包连接Openfire进行消息推送
 */
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
