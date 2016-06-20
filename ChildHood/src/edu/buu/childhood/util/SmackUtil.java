package edu.buu.childhood.util;

import java.io.IOException;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
/**
 * Smack连接Openfire做推送工具类
 * @Ahtuor Joe
 * @param PROPER_FILE 配置文件存储路径
 * @param userName 登录到openfire服务器的用户名，默认用pushmaster登录
 * @param password 登录用户的密码
 * @param host 主机地址
 * @param port 链接端口
 */
public abstract class SmackUtil{
	public static final String PROPER_FILE=System.getProperty("user.dir")+"\\Smack.property";
	private static String userName;
	private static String password;
	private static String host;
	private static int port;
	private static AbstractXMPPConnection connection;
	private static ChatManager chatManager;
	
	static{
		try {
			userName=ProperUtil.getProperties("push","pushmaster", PROPER_FILE);
			password=ProperUtil.getProperties("code", PROPER_FILE);
			host=ProperUtil.getProperties("host","127.0.0.1", PROPER_FILE);
			port=Integer.valueOf(ProperUtil.getProperties("port","5222", PROPER_FILE));
		} catch (Exception e) {
			e.printStackTrace();
		}
		XMPPTCPConnectionConfiguration config =
				XMPPTCPConnectionConfiguration.builder()
				.setUsernameAndPassword(userName, password)
				.setServiceName("childhood")
				.setHost(host)
				.setPort(port)
				.setResource("childhood")
				.setSecurityMode(SecurityMode.disabled)
				.build();
		connection = new XMPPTCPConnection(config);
		try {
			connection.connect();
			connection.login();
		} catch (SmackException | IOException | XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		chatManager=ChatManager.getInstanceFor(connection);
	}
	
	public static AbstractXMPPConnection getConnection(){
		return connection;
	}
	
	public static ChatManager getChatManager(){
		return chatManager;
	}
	
	public static Chat getChat(String user){
		return chatManager.createChat(user);
	}
	
	public static Chat getChat(String user,ChatMessageListener listener){
		return chatManager.createChat(user,listener);
	}
	
	public static Roster getRoster(){
		return Roster.getInstanceFor(connection);
	}
}
