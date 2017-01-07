package edu.buu.childhood.rank.action;

import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.rank.service.RankService;

public class LikeUserAction extends ActionSupport {
	/**
	 * LikeUserAction序列化ID
	 */
	private static final long serialVersionUID = -745464378640234573L;
	private String webOrApp;
	private String userName;
	private String likeUser;
	private String access;
	private int gameId;
	private Gson json = new Gson();
	private RankService rankService;

	public void setRankService(RankService rankService) {
		this.rankService = rankService;
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

	public String getLikeUser() {
		return likeUser;
	}

	public void setLikeUser(String likeUser) {
		this.likeUser = likeUser;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	@Override
	public String execute() throws Exception {
		switch (webOrApp) {
		case C.def.WEB_REQUEST:
			return null;
		case C.def.APP_REQUEST:
			boolean result = false;
			if (!C.def.ACCESS_GAMEOVER.equals(access)) {
				result = rankService.likeUser(userName, likeUser, access);
			} else if (gameId > 0) {
				result = rankService.likeUser(userName, likeUser, access,
						gameId);
			} else {
				return ERROR;
			}
			if (result) {
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				Message<String> message = new Message<String>(
						C.rank.LIKE_USER_SUCCESS, C.rank.LIKE_USER_SUCCESS);
				session.setAttribute("JsonString",
						json.toJson(message, new TypeToken<Message<String>>() {
						}.getType()));
			} else {
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				Message<String> message = new Message<String>(
						C.rank.LIKE_USER_UNSUCCESS, C.rank.LIKE_USER_UNSUCCESS);
				session.setAttribute("JsonString",
						json.toJson(message, new TypeToken<Message<String>>() {
						}.getType()));
			}
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
