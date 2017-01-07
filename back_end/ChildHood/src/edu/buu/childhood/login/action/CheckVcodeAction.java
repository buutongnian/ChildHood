package edu.buu.childhood.login.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.login.service.PasswordService;

public class CheckVcodeAction extends ActionSupport {
	/**
	 * CheckVcodeAction序列化ID
	 */
	private static final long serialVersionUID = -6259873034000741222L;
	private String webOrApp;
	private String telNum;
	private String vcode;

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

	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}

	@Override
	public String execute() throws Exception {
		switch (webOrApp) {
		// TODO WEB端API请求方法预留实现接口
		case C.def.WEB_REQUEST:
			return null;
		case C.def.APP_REQUEST:
			if (passwordService.check(telNum, vcode)) {
				// 将Json数据放入Session中，变量名为"JsonString"
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.vcode.VERIFIED_SUCCESS,
								C.vcode.VERIFIED_SUCCESS),
						new TypeToken<Message<String>>() {
						}.getType()));
			} else {
				// 将Json数据放入Session中，变量名为"JsonString"
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.vcode.VERIFIED_FAILURE,
								C.vcode.VERIFIED_FAILURE),
						new TypeToken<Message<String>>() {
						}.getType()));
			}
			return "MessagePage";
		default:
			return ERROR;
		}
	}

}
