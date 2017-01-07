package edu.buu.childhood.login.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.login.service.PasswordService;

public class ChangePasswordAction extends ActionSupport {
	private static final long serialVersionUID = -4535039115906595646L;
	private String webOrApp;
	private String userName;
	private String oldPwd;
	private String newPwd;

	private Gson json = new Gson();

	private PasswordService passwordService;

	public void setPasswordService(PasswordService passwordService) {
		this.passwordService = passwordService;
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

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	@Override
	public String execute() throws Exception {
		switch (webOrApp) {
		// TODO WEB端API请求方法预留实现接口
		case C.def.WEB_REQUEST:
			return null;
		case C.def.APP_REQUEST:
			String result = passwordService.changePassword(userName, oldPwd,
					newPwd);
			if (C.LoginStatus.LOGIN_SUCCESS.equals(result)) {
				// 将Json数据放入Session中，变量名为"JsonString"
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.Account.CHANGE_PASSWORD_SUCCESS,
								C.Account.CHANGE_PASSWORD_SUCCESS),
						new TypeToken<Message<String>>() {
						}.getType()));
			} else if (C.LoginStatus.PASSWORD_INCORRECT.equals(result)) {
				// 将Json数据放入Session中，变量名为"JsonString"
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(
								C.Account.CHANGE_PASSWORD_UNSUCCESS,
								C.Account.OLD_PASSWORD_INCORRECT),
						new TypeToken<Message<String>>() {
						}.getType()));
			} else if (C.LoginStatus.USER_NOT_EXIST.equals(result)) {
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(
								C.Account.CHANGE_PASSWORD_UNSUCCESS,
								C.Account.USER_NOT_EXISTS),
						new TypeToken<Message<String>>() {
						}.getType()));
			} else {
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(
								C.Account.CHANGE_PASSWORD_UNSUCCESS,
								C.def.ERROR), new TypeToken<Message<String>>() {
						}.getType()));
			}
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
