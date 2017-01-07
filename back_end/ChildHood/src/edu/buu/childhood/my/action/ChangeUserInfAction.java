package edu.buu.childhood.my.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.my.service.MyService;

public class ChangeUserInfAction extends ActionSupport {

	/**
	 * ChangeUserInfAction序列化ID
	 */
	private static final long serialVersionUID = -4118149047349580380L;
	private String webOrApp;
	private String userName;
	private String userNickname;
	private int belongingProvince;
	private int belongingCity;
	private int belongingDistrict;
	private String community;
	private String detailAddress;
	private String email;
	private MyService myService;
	private Gson json = new Gson();

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

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
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
		case C.def.WEB_REQUEST:
			return null;
		case C.def.APP_REQUEST:
			myService.updateUserInf(userName, userNickname, belongingProvince,
					belongingCity, belongingDistrict, community,
					detailAddress, email);
			// 将Json数据放入Session中，变量名为"JsonString"
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			// 游戏信息更新成功标识
			session.setAttribute("JsonString", json.toJson(
					new Message<String>(C.my.USER_INF_UPDATE_SUCCESS,
							C.my.USER_INF_UPDATE_SUCCESS),
					new TypeToken<Message<String>>() {
					}.getType()));
			return "MessagePage";
		default:
			return ERROR;
		}

	}
}
