package edu.buu.childhood.login.task;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

import edu.buu.childhood.common.C;

public class UserStatus {
	
	private static Logger logger=Logger.getLogger(UserStatus.class);
	
	public static String getUserStatus(String urlStr)
    {
        String shOnLineState= C.LoginStatus.USER_NOT_EXIST;    //用户不存在
        try{
            URL oUrl= new URL(urlStr);
            URLConnection oConn= oUrl.openConnection();
            if(oConn!=null)
            {
            	BufferedReader oIn = new BufferedReader(new InputStreamReader(oConn.getInputStream()));
            	if(null!=oIn)
            	{
            		String strFlag = oIn.readLine();
            		oIn.close();
            		if(strFlag.indexOf("type=\"unavailable\"")>=0)
            		{
            			shOnLineState = C.status.offline; //用戶不在线
            		}
            		if(strFlag.indexOf("type=\"error\"")>=0)
               		{
            			shOnLineState = C.LoginStatus.USER_NOT_EXIST; //请求错误
               		}
            		else if(strFlag.indexOf("priority")>=0 || strFlag.indexOf("id=\"")>=0)
            		{
            			shOnLineState = C.status.ONLINE; //用户在线
            		}
            	}
            }
        }
        catch(Exception e){    
        	logger.error(e.getMessage());
        }
        return shOnLineState;
    }
}
