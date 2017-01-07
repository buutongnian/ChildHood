package edu.buu.childhood.admin.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.common.C;
import edu.buu.childhood.login.service.LoginService;

public class AdminLoginAction extends ActionSupport {
	/**
	 * AdminLoginAction序列化ID
	 */
	private static final long serialVersionUID = 8812849270296824012L;

	private static List<String> adminList = new ArrayList<String>() {
		private static final long serialVersionUID = 8033209438273619400L;

		{
			add("admin");
			add("13269128687");
			add("13240150408");
			add("13269158110");
			add("15110237585");
			add("13611108060");
		}
	};
	private String userName;
	private String userPwd;

	private LoginService loginService;

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
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

	@Override
	public String execute() throws Exception {
		HttpSession session = ServletActionContext.getRequest().getSession();
		if (adminList.contains(userName)) {
			if (C.LoginStatus.LOGIN_SUCCESS.equals(loginService.loginCheck(
					userName, userPwd))) {
				session.setAttribute("userName", userName);
				return SUCCESS;
			} else {
				session.setAttribute("errorMsg", "password");
				return ERROR;
			}
		} else {
			session.setAttribute("errorMsg", "username");
			return ERROR;
		}
	}
}
