package edu.buu.childhood.login.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.login.service.LoginService;
import edu.buu.childhood.login.service.PasswordService;
import edu.buu.childhood.login.service.RegisterService;
import edu.buu.childhood.my.pojo.User;
import edu.buu.childhood.my.service.MyService;

public class SMSLoginAction extends ActionSupport {
	/**
	 * SMSLoginAction序列化ID
	 */
	private static final long serialVersionUID = -2921481398542673149L;
	private String webOrApp;// 请求方式（Web端或App端），内容由C.java常量文件定义
	private String userName;// 用户名，即手机号
	private double lastLatitude;// 登录时纬度
	private double lastLongitude;// 登录时经度
	private String vcode;// 验证码

	private Gson json = new Gson();

	private LoginService loginService;
	private PasswordService passwordService;
	private RegisterService registerService;
	private MyService myService;

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public void setPasswordService(PasswordService passwordService) {
		this.passwordService = passwordService;
	}

	public void setRegisterService(RegisterService registerService) {
		this.registerService = registerService;
	}

	public void setMyService(MyService myService) {
		this.myService = myService;
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

	public double getLastLatitude() {
		return lastLatitude;
	}

	public void setLastLatitude(double lastLatitude) {
		this.lastLatitude = lastLatitude;
	}

	public double getLastLongitude() {
		return lastLongitude;
	}

	public void setLastLongitude(double lastLongitude) {
		this.lastLongitude = lastLongitude;
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
			if (registerService.isRegistered(userName)) {
				if (passwordService.check(userName, vcode)) {

					// 更新登录状态
					loginService.changeLoginStatus(userName, C.status.ONLINE);
					// 更新经纬度信息
					loginService.updateLoginInf(userName, lastLatitude,
							lastLongitude);

					User user = myService.getUserInf(userName);

					// 将Json数据放入Session中，变量名为"JsonString"
					HttpSession session = ServletActionContext.getRequest()
							.getSession();
					session.setAttribute("JsonString", json
							.toJson(new Message<User>(
									C.LoginStatus.LOGIN_SUCCESS, user),
									new TypeToken<Message<User>>() {
									}.getType()));
				} else {
					// 将Json数据放入Session中，变量名为"JsonString"
					HttpSession session = ServletActionContext.getRequest()
							.getSession();
					session.setAttribute("JsonString", json.toJson(
							new Message<String>(C.LoginStatus.LOGIN_UNSUCCESS,
									C.vcode.VERIFIED_FAILURE),
							new TypeToken<Message<String>>() {
							}.getType()));
				}
			} else {
				// 将Json数据放入Session中，变量名为"JsonString"
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.LoginStatus.LOGIN_UNSUCCESS,
								C.LoginStatus.USER_NOT_EXIST),
						new TypeToken<Message<String>>() {
						}.getType()));
			}
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
