package edu.buu.childhood.find.action;

import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.find.service.FindService;

public class DeleteFindInfoAction extends ActionSupport{
	/**
	 * DeleteFindInfoAction序列化ID
	 */
	private static final long serialVersionUID = -6872005198373306057L;
	private String webOrApp;
	private String userName;
	private FindService findService;
	private Gson json=new Gson();
	
	public void setFindService(FindService findService) {
		this.findService = findService;
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
			findService.deleteFindReg(userName);
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			session.setAttribute("JsonString", json.toJson(new Message<String>(
					C.find.DELETE_FIND_INFO_SUCCESS, C.find.DELETE_FIND_INFO_SUCCESS),
					new TypeToken<Message<String>>() {
					}.getType()));
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
