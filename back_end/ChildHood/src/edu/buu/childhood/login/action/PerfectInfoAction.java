package edu.buu.childhood.login.action;

import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.login.service.RegisterService;

public class PerfectInfoAction extends ActionSupport {
	/**
	 * PerfectInfoAction序列化ID
	 */
	private static final long serialVersionUID = -4278222367670427048L;

	private String webOrApp;

	private String userName;
	private String userPwd;
	private String userNickname;
	private int belongingProvince;
	private int belongingCity;
	private int belongingDistrict;
	private String community;
	private String email;

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

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public int getBelongingProvince() {
		return belongingProvince;
	}

	public void setBelongingProvince(int belongingProvince) {
		this.belongingProvince = belongingProvince;
	}

	public int getBelongingCity() {
		return belongingCity;
	}

	public void setBelongingCity(int belongingCity) {
		this.belongingCity = belongingCity;
	}

	public int getBelongingDistrict() {
		return belongingDistrict;
	}

	public void setBelongingDistrict(int belongingDistrict) {
		this.belongingDistrict = belongingDistrict;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String execute() throws Exception {
		switch (webOrApp) {
		// TODO WEB端API请求方法预留实现接口
		case C.def.WEB_REQUEST:
			return null;
		case C.def.APP_REQUEST:
			String result = registerService.perfectInformation(userName,
					userPwd, userNickname, belongingProvince, belongingCity,
					belongingDistrict, community, email);
			if (C.reg.PERFECT_INFO_SUCCESS.equals(result)) {
				// 将Json数据放入Session中，变量名为"JsonString"
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.reg.PERFECT_INFO_SUCCESS,
								C.reg.PERFECT_INFO_SUCCESS),
						new TypeToken<Message<String>>() {
						}.getType()));
			} else if (C.reg.REG_ERROR.equals(result)) {
				// 将Json数据放入Session中，变量名为"JsonString"
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.reg.PERFECT_INFO_UNSUCCESS,
								C.reg.REG_ERROR),
						new TypeToken<Message<String>>() {
						}.getType()));
			} else {
				// 将Json数据放入Session中，变量名为"JsonString"
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.reg.PERFECT_INFO_UNSUCCESS,
								result), new TypeToken<Message<String>>() {
						}.getType()));
			}
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
