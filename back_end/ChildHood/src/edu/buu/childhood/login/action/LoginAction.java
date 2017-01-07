package edu.buu.childhood.login.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.log.service.LogService;
import edu.buu.childhood.login.service.LoginService;
import edu.buu.childhood.my.pojo.User;
import edu.buu.childhood.my.service.MyService;

/**
 * 2016/6/28 登录Action API接口，用于App端或Web端登录 保留webOrApp标志位，为后期添加WEB端支持留下接口
 * 
 * @author joe
 *
 */
public class LoginAction extends ActionSupport {

	/**
	 * LoginAction序列化ID
	 */
	private static final long serialVersionUID = 302089185916002870L;

	private String webOrApp;// 请求方式（Web端或App端），内容由C.java常量文件定义
	private String userName;
	private String userPwd;
	private double lastLatitude;// 登录时纬度
	private double lastLongitude;// 登录时经度

	private Gson json = new Gson();

	private LoginService loginService;

	@Autowired
	private LogService logService;
	@Autowired
	private MyService myService;

	// 通过Spring装配loginServiceImpl
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
		String loginIp = ServletActionContext.getRequest().getRemoteAddr();
		switch (webOrApp) {
		// TODO WEB端API请求方法预留实现接口
		case C.def.WEB_REQUEST:
			return null;
		case C.def.APP_REQUEST:
			if (C.LoginStatus.LOGIN_SUCCESS.equals(loginService.loginCheck(
					userName, userPwd))) {
				// 更新登录状态
				loginService.changeLoginStatus(userName, C.status.ONLINE);
				// 更新经纬度信息
				loginService.updateLoginInf(userName, lastLatitude,
						lastLongitude);

				logService.writeLoginLog(userName, lastLatitude, lastLongitude,
						loginIp);

				User userFromDB = myService.getUserInf(userName);

				// 将Json数据放入Session中，变量名为"JsonString"
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<User>(C.LoginStatus.LOGIN_SUCCESS,
								userFromDB), new TypeToken<Message<User>>() {
						}.getType()));
			} else if (C.LoginStatus.PASSWORD_INCORRECT.equals(loginService
					.loginCheck(userName, userPwd))) {

				// 将Json数据放入Session中，变量名为"JsonString"
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.LoginStatus.LOGIN_UNSUCCESS,
								C.LoginStatus.PASSWORD_INCORRECT),
						new TypeToken<Message<String>>() {
						}.getType()));
			} else if (C.LoginStatus.USER_NOT_EXIST.equals(loginService
					.loginCheck(userName, userPwd))) {
				// 将Json数据放入Session中，变量名为"JsonString"
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.LoginStatus.LOGIN_UNSUCCESS,
								C.LoginStatus.USER_NOT_EXIST),
						new TypeToken<Message<String>>() {
						}.getClass()));
			} else {
				// 将Json数据放入Session中，变量名为"JsonString"
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.def.ERROR, C.def.ERROR),
						new TypeToken<Message<String>>() {
						}.getType()));
			}
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
