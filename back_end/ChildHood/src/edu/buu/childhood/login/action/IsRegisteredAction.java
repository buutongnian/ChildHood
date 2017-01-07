package edu.buu.childhood.login.action;

import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.login.service.RegisterService;

public class IsRegisteredAction extends ActionSupport {
	/**
	 * IsRegisteredAction序列化ID
	 */
	private static final long serialVersionUID = 8949044465529644161L;
	private String webOrApp;
	private String userName;

	private Gson json = new Gson();

	private RegisterService registerService;

	public void setRegisterService(RegisterService registerService) {
		this.registerService = registerService;
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
			if (registerService.isRegistered(userName)) {
				// 将Json数据放入Session中，变量名为"JsonString"
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.reg.USER_EXISTS,
								C.reg.USER_EXISTS),
						new TypeToken<Message<String>>() {
						}.getType()));
			} else {
				// 将Json数据放入Session中，变量名为"JsonString"
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.reg.USER_NOT_EXISTS,
								C.reg.USER_NOT_EXISTS),
						new TypeToken<Message<String>>() {
						}.getType()));
			}
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
