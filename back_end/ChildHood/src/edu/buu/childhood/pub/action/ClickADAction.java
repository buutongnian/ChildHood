package edu.buu.childhood.pub.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.log.service.LogService;

public class ClickADAction extends ActionSupport {
	private static final long serialVersionUID = -8382655851788686772L;
	private String webOrApp;
	private String userName;
	private int adId;

	@Autowired
	private LogService logService;

	private Gson json = new Gson();

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

	public int getAdId() {
		return adId;
	}

	public void setAdId(int adId) {
		this.adId = adId;
	}

	@Override
	public String execute() throws Exception {
		switch (webOrApp) {
		case C.def.WEB_REQUEST:
			return null;
		case C.def.APP_REQUEST:
			logService.writeADClickLog(userName, adId);
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			session.setAttribute("JsonString", json.toJson(new Message<String>(
					"success", "success"), new TypeToken<Message<String>>() {
			}.getType()));
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
