package edu.buu.childhood.login.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.login.service.LoginService;

public class LoginAction extends ActionSupport{
	
	
	/**
	 * LoginAction序列化ID
	 */
	private static final long serialVersionUID = 302089185916002870L;
	
	private String webOrApp;//请求方式（Web端或App端），内容由C.java常量文件定义
	private String userName;
	private String userPwd;
	private double lastLatitude;//登录时纬度
	private double lastLongitude;//登录时经度
	
	private Gson json=new Gson();
	
	private LoginService loginService;
	//通过Spring装配loginServiceImpl
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	
	public String getWebOrApp() {
		return webOrApp;
	}

	public void setWebOrApp(String webOrApp) {
		this.webOrApp = webOrApp;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public double getLastLatitude() {
		return lastLatitude;
	}

	public void setLastLatitude(double lastLatitude) {
		this.lastLatitude = lastLatitude;
	}

	public double getLastLongitude() {
		return lastLongitude;
	}

	public void setLastLongitude(double lastLongitude) {
		this.lastLongitude = lastLongitude;
	}

	@Override
	public String execute() throws Exception {
		switch(webOrApp){
		//TODO WEB端API请求方法预留实现接口
		case C.def.WEB_REQUEST:return null;
		case C.def.APP_REQUEST:
			if(C.LoginStatus.
					LOGIN_SUCCESS.
					equals(loginService.
							loginCheck(userName, userPwd))){
				//更新登录状态
				loginService.
				changeLoginStatus(userName, C.status.ONLINE);
				//更新经纬度信息
				loginService.
				updateLoginInf(userName, lastLatitude, lastLongitude);
				
				//将Json数据放入Session中，变量名为"JsonString"
				HttpSession session=ServletActionContext.getRequest().getSession();
				//TODO 2016/6/24 定义登录成功返回信息格式
				session.setAttribute("JsonString", json.toJson(new Message<String>("LoginSuccess","LoginSuccess"),new TypeToken<Message<String>>(){}.getType()));
			}else if(C.LoginStatus.
					PASSWORD_INCORRECT.
					equals(loginService.
							loginCheck(userName, userPwd))){
				
				//将Json数据放入Session中，变量名为"JsonString"
				HttpSession session=ServletActionContext.getRequest().getSession();
				//TODO 2016/6/24 定义密码错误返回信息格式
				session.setAttribute("JsonString", json.toJson(new Message<String>("LoginUnsuccess","PasswordIncorrect"),new TypeToken<Message<String>>(){}.getType()));
			}else if(C.LoginStatus.
					USER_NOT_EXIST.
					equals(loginService.
							loginCheck(userName, userPwd))){
				//将Json数据放入Session中，变量名为"JsonString"
				HttpSession session=ServletActionContext.getRequest().getSession();
				//TODO 2016/6/24 定义用户不存在返回信息格式
				session.setAttribute("JsonString", json.toJson(new Message<String>("LoginUnsuccess","UserNotExist"),new TypeToken<Message<String>>(){}.getClass()));
			}else{
				//将Json数据放入Session中，变量名为"JsonString"
				HttpSession session=ServletActionContext.getRequest().getSession();
				//TODO 2016/6/25 定义错误返回信息格式
				session.setAttribute("JsonString", json.toJson(new Message<String>("",""),new TypeToken<Message<String>>(){}.getType()));
			}
			return "MessagePage";
			default:return ERROR;
		}
	}
}
