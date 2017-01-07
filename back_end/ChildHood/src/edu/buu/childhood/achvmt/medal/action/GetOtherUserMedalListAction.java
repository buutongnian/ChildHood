package edu.buu.childhood.achvmt.medal.action;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.achvmt.medal.pojo.UserMedalList;
import edu.buu.childhood.achvmt.medal.service.MedalService;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;

public class GetOtherUserMedalListAction extends ActionSupport {
	private static final long serialVersionUID = -6215581440454192394L;
	private String webOrApp;
	private String userName;
	private String targetUser;

	private Gson json = new Gson();

	private MedalService medalService;

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
			List<UserMedalList> userMedalList = medalService
					.getUserMedalList(targetUser);
			if (userMedalList != null && !userMedalList.isEmpty()) {
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<List<UserMedalList>>(
								C.achvmt.GET_USER_MEDAL_LIST_SUCCESS,
								userMedalList),
						new TypeToken<Message<List<UserMedalList>>>() {
						}.getType()));
			} else {
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.achvmt.GET_USER_MEDAL_LIST_EMPTY,
								C.achvmt.GET_USER_MEDAL_LIST_EMPTY),
						new TypeToken<Message<String>>() {
						}.getType()));
			}
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
