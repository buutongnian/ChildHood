package edu.buu.childhood.login.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.login.service.LoginService;

public class LogoutAction extends ActionSupport {
	private static final long serialVersionUID = -3274647535847413696L;
	private String webOrApp;// 请求方式（Web端或App端），内容由C.java常量文件定义
	private String userName;

	private Gson json = new Gson();

	private LoginService loginService;

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

	@Override
	public String execute() throws Exception {
		switch (webOrApp) {
		// TODO WEB端API请求方法预留实现接口
		case C.def.WEB_REQUEST:
			return null;
		case C.def.APP_REQUEST:
			if (loginService.changeLoginStatus(userName, C.status.OFFLINE)) {
				// 将Json数据放入Session中，变量名为"JsonString"
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.LoginStatus.LOGOUT_SUCCESS,
								C.LoginStatus.LOGOUT_SUCCESS),
						new TypeToken<Message<String>>() {
						}.getType()));
			} else {

				// 将Json数据放入Session中，变量名为"JsonString"
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.LoginStatus.LOGOUT_UNSUCCESS,
								C.LoginStatus.LOGOUT_UNSUCCESS),
						new TypeToken<Message<String>>() {
						}.getType()));
			}
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
