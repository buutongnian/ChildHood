package edu.buu.childhood.find.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.find.service.FindService;

public class FindPartnerAction extends ActionSupport {

	/**
	 * FindPartenerAction序列化ID
	 */
	private static final long serialVersionUID = -2817522779360374709L;

	private String webOrApp;

	private String userName;
	private int age;
	private char sex;
	private double youngLatitude;
	private double youngLongitude;
	private String youngNickname;
	private String youngRoad;
	private String message;

	private FindService findService;

	private Gson json = new Gson();

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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public double getYoungLatitude() {
		return youngLatitude;
	}

	public void setYoungLatitude(double youngLatitude) {
		this.youngLatitude = youngLatitude;
	}

	public double getYoungLongitude() {
		return youngLongitude;
	}

	public void setYoungLongitude(double youngLongitude) {
		this.youngLongitude = youngLongitude;
	}

	public String getYoungNickname() {
		return youngNickname;
	}

	public void setYoungNickname(String youngNickname) {
		this.youngNickname = youngNickname;
	}

	public String getYoungRoad() {
		return youngRoad;
	}

	public void setYoungRoad(String youngRoad) {
		this.youngRoad = youngRoad;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String execute() throws Exception {
		switch (webOrApp) {
		case C.def.WEB_REQUEST:
			return null;
		case C.def.APP_REQUEST:
			findService.insertFindReg(userName, age, sex, youngLatitude,
					youngLongitude, youngNickname, youngRoad, message);
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			session.setAttribute("JsonString", json.toJson(new Message<String>(
					C.find.FIND_REG_SUCCESS, C.find.FIND_REG_SUCCESS),
					new TypeToken<Message<String>>() {
					}.getType()));
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
