package edu.buu.childhood.login.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.login.service.PasswordService;
import edu.buu.childhood.login.service.RegisterService;
import edu.buu.childhood.my.pojo.User;
import edu.buu.childhood.my.service.MyService;

public class RegisterAction extends ActionSupport {

	/**
	 * RegisterAction序列化ID
	 */
	private static final long serialVersionUID = -6857593146545615188L;

	private RegisterService registerService;

	public void setRegisterService(RegisterService registerService) {
		this.registerService = registerService;
	}

	private PasswordService passwordService;

	private MyService myService;

	public void setPasswordService(PasswordService passwordService) {
		this.passwordService = passwordService;
	}

	public void setMyService(MyService myService) {
		this.myService = myService;
	}

	private String webOrApp;// 请求方式（Web端或App端），内容由C.java常量文件定义

	private String userName;
	private double regLatitude;
	private double regLongitude;
	private String vcode;

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

	public double getRegLatitude() {
		return regLatitude;
	}

	public void setRegLatitude(double regLatitude) {
		this.regLatitude = regLatitude;
	}

	public double getRegLongitude() {
		return regLongitude;
	}

	public void setRegLongitude(double regLongitude) {
		this.regLongitude = regLongitude;
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
			if (passwordService.check(userName, vcode)) {
				String result = registerService.register(userName, regLatitude,
						regLongitude);
				if (C.reg.REG_SUCCESS.equals(result)) {
					User userFromDB = myService.getUserInf(userName);
					// 将Json数据放入Session中，变量名为"JsonString"
					HttpSession session = ServletActionContext.getRequest()
							.getSession();
					session.setAttribute("JsonString", json.toJson(
							new Message<User>(C.reg.REG_SUCCESS, userFromDB),
							new TypeToken<Message<User>>() {
							}.getType()));
				} else if (C.reg.USER_EXISTS.equals(result)) {
					// 将Json数据放入Session中，变量名为"JsonString"
					HttpSession session = ServletActionContext.getRequest()
							.getSession();
					session.setAttribute("JsonString", json.toJson(
							new Message<String>(C.reg.REG_UNSUCCESS,
									C.reg.USER_EXISTS),
							new TypeToken<Message<String>>() {
							}.getType()));
				} else {
					return ERROR;
				}
			} else {
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
