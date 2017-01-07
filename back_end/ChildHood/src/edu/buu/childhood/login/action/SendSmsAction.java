package edu.buu.childhood.login.action;

import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.login.service.PasswordService;

public class SendSmsAction extends ActionSupport {
	/**
	 * SendMessageAction序列化ID
	 */
	private static final long serialVersionUID = -5088202633918603394L;
	private String webOrApp;
	private String telNum;

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

	public String getTelNum() {
		return telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	@Override
	public String execute() throws Exception {
		switch (webOrApp) {
		// TODO WEB端API请求方法预留实现接口
		case C.def.WEB_REQUEST:
			return null;
		case C.def.APP_REQUEST:
			if (passwordService.sendSms(telNum)) {
				// 将Json数据放入Session中，变量名为"JsonString"
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.vcode.SMS_SEND_SUCCESS,
								C.vcode.SMS_SEND_SUCCESS),
						new TypeToken<Message<String>>() {
						}.getType()));
			} else {
				// 将Json数据放入Session中，变量名为"JsonString"
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.vcode.SMS_SEND_ERROR,
								C.vcode.SMS_SEND_ERROR),
						new TypeToken<Message<String>>() {
						}.getType()));
			}
			return "MessagePage";
		default:
			return ERROR;
		}
	}

}
