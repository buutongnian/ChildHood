package edu.buu.childhood.achvmt.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.achvmt.pojo.UserKPI;
import edu.buu.childhood.achvmt.service.AchvmtService;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;

public class GetUserKPIAction extends ActionSupport {
	private static final long serialVersionUID = 8266232592816252897L;
	private String webOrApp;
	private String userName;

	private AchvmtService achvmtService;

	private Gson json = new Gson();

	public void setAchvmtService(AchvmtService achvmtService) {
		this.achvmtService = achvmtService;
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
		case C.def.WEB_REQUEST:
			return null;
		case C.def.APP_REQUEST:
			UserKPI userKPI = achvmtService.getUserKPI(userName);
			if (userKPI != null) {
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<UserKPI>(C.achvmt.GET_USER_KPI_SUCCESS,
								userKPI), new TypeToken<Message<UserKPI>>() {
						}.getType()));
			} else {
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.achvmt.GET_USER_KPI_UNSUCCESS,
								C.achvmt.GET_USER_KPI_UNSUCCESS),
						new TypeToken<Message<String>>() {
						}.getType()));
			}
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
