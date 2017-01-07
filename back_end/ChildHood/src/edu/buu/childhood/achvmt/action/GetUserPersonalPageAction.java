package edu.buu.childhood.achvmt.action;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.achvmt.medal.pojo.UserMedalList;
import edu.buu.childhood.achvmt.medal.service.MedalService;
import edu.buu.childhood.achvmt.pojo.UserHistoryGameList;
import edu.buu.childhood.achvmt.pojo.UserKPI;
import edu.buu.childhood.achvmt.pojo.UserPersonalPage;
import edu.buu.childhood.achvmt.service.AchvmtService;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;

public class GetUserPersonalPageAction extends ActionSupport {
	private static final long serialVersionUID = 4649307130340229116L;
	private String webOrApp;
	private String userName;
	private String targetUser;

	private Gson json = new Gson();

	private AchvmtService achvmtService;
	private MedalService medalService;

	public void setAchvmtService(AchvmtService achvmtService) {
		this.achvmtService = achvmtService;
	}

	public void setMedalService(MedalService medalService) {
		this.medalService = medalService;
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

	public String getTargetUser() {
		return targetUser;
	}

	public void setTargetUser(String targetUser) {
		this.targetUser = targetUser;
	}

	@Override
	public String execute() throws Exception {
		switch (webOrApp) {
		case C.def.WEB_REQUEST:
			return null;
		case C.def.APP_REQUEST:
			List<UserHistoryGameList> userHistoryGameList = achvmtService
					.getUserHistoryGameList(targetUser, 1).getDataList();
			List<UserMedalList> userMedalList = medalService
					.getUserMedalList(targetUser);
			UserKPI userKPI = achvmtService.getUserKPI(targetUser);

			UserPersonalPage userPersonalPage = new UserPersonalPage(
					userHistoryGameList, userMedalList, userKPI);
			if (userHistoryGameList != null && userMedalList != null
					&& userKPI != null) {
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<UserPersonalPage>(
								C.achvmt.GET_USER_PERSONAL_PAGE_SUCCESS,
								userPersonalPage),
						new TypeToken<Message<UserPersonalPage>>() {
						}.getType()));
			} else {
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(
								C.achvmt.GET_USER_PERSONAL_PAGE_UNSUCCESS,
								C.achvmt.GET_USER_PERSONAL_PAGE_UNSUCCESS),
						new TypeToken<Message<String>>() {
						}.getType()));
			}
			return "MessagePage";
		default:
			return ERROR;
		}
	}

}
