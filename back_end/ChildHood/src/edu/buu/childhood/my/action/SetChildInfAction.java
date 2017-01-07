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

public class SetChildInfAction extends ActionSupport {
	/**
	 * SetChildInfAction序列化ID
	 */
	private static final long serialVersionUID = -7515331796669454112L;
	private String webOrApp;
	private String userName;
	private String childName;
	private char childSex;
	private Date childBirthday;
	private int educationCode;
	private int gradeCode;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

	public char getChildSex() {
		return childSex;
	}

	public void setChildSex(char childSex) {
		this.childSex = childSex;
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

	public int getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(int gradeCode) {
		this.gradeCode = gradeCode;
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
			myService.setChildInf(userName, childName, childSex, childBirthday,
					educationCode, gradeCode, schoolCode,customSchool);
			// 将Json数据放入Session中，变量名为"JsonString"
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			// 孩子信息插入成功标识
			session.setAttribute("JsonString", json.toJson(new Message<String>(
					C.my.SET_CHILD_INF_SUCCESS, C.my.SET_CHILD_INF_SUCCESS),
					new TypeToken<Message<String>>() {
					}.getType()));
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
