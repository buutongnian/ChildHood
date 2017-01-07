package edu.buu.childhood.my.action;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.my.service.MyService;

public class ChangeChildInfAction extends ActionSupport {
	/**
	 * ChangeChildInfAction序列化ID
	 */
	private static final long serialVersionUID = 5315929592068510193L;
	private String webOrApp;
	private String childInfId;
	private String childName;
	private String sex;
	private Date childBirthday;
	private int educationCode;
	private int schoolCode;
	private String customSchool;
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

	public String getChildInfId() {
		return childInfId;
	}

	public void setChildInfId(String childInfId) {
		this.childInfId = childInfId;
	}

	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getChildBirthday() {
		return childBirthday;
	}

	public void setChildBirthday(Date childBirthday) {
		this.childBirthday = childBirthday;
	}

	public int getEducationCode() {
		return educationCode;
	}

	public void setEducationCode(int educationCode) {
		this.educationCode = educationCode;
	}

	public int getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(int schoolCode) {
		this.schoolCode = schoolCode;
	}

	public String getCustomSchool() {
		return customSchool;
	}

	public void setCustomSchool(String customSchool) {
		this.customSchool = customSchool;
	}

	@Override
	public String execute() throws Exception {
		switch (webOrApp) {
		case C.def.WEB_REQUEST:
			return null;
		case C.def.APP_REQUEST:
			myService.updateChildInf(Integer.parseInt(childInfId), childName, sex, childBirthday,
					educationCode, schoolCode, customSchool);
			// 将Json数据放入Session中，变量名为"JsonString"
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			// 游戏信息更新成功标识
			session.setAttribute("JsonString", json.toJson(new Message<String>(
					C.my.CHILD_INF_UPDATE_SUCCESS,
					C.my.CHILD_INF_UPDATE_SUCCESS),
					new TypeToken<Message<String>>() {
					}.getType()));
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
