package edu.buu.childhood.achvmt.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.achvmt.pojo.UserHistoryGameList;
import edu.buu.childhood.achvmt.service.AchvmtService;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.common.Page;

public class GetUserGameListAction extends ActionSupport {
	private static final long serialVersionUID = 2322647150701507969L;
	private String webOrApp;
	private String userName;
	private int pageNum;

	private AchvmtService achvmtService;

	private Gson json = new Gson();

	public void setAchvmtService(AchvmtService achvmtService) {
		this.achvmtService = achvmtService;
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

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	@Override
	public String execute() throws Exception {
		switch (webOrApp) {
		case C.def.WEB_REQUEST:
			return null;
		case C.def.APP_REQUEST:
			Page<UserHistoryGameList> userHistoryGameList = achvmtService
					.getUserHistoryGameList(userName, pageNum);
			if (userHistoryGameList != null) {
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<Page<UserHistoryGameList>>(
								C.achvmt.GET_USER_GAME_LIST_SUCCESS,
								userHistoryGameList),
						new TypeToken<Message<Page<UserHistoryGameList>>>() {
						}.getType()));
			} else {
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(
								C.achvmt.GET_USER_GAME_LIST_UNSUCCESS,
								C.achvmt.GET_USER_GAME_LIST_UNSUCCESS),
						new TypeToken<Message<String>>() {
						}.getType()));
			}
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
