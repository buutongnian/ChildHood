package edu.buu.childhood.status.action;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.status.service.StatusService;

public class AddFamilyGameAction extends ActionSupport {
	private static final long serialVersionUID = -6894581793884287922L;
	private String webOrApp;
	private String userName;
	private int gameCode;
	private Date gameTime;
	private String gameNote;
	private StatusService statusService;

	private Gson json = new Gson();

	public void setStatusService(StatusService statusService) {
		this.statusService = statusService;
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

	public int getGameCode() {
		return gameCode;
	}

	public void setGameCode(int gameCode) {
		this.gameCode = gameCode;
	}

	public Date getGameTime() {
		return gameTime;
	}

	public void setGameTime(Date gameTime) {
		this.gameTime = gameTime;
	}

	public String getGameNote() {
		return gameNote;
	}

	public void setGameNote(String gameNote) {
		this.gameNote = gameNote;
	}

	@Override
	public String execute() throws Exception {
		switch (webOrApp) {
		case C.def.WEB_REQUEST:
			return null;
		case C.def.APP_REQUEST:
			String result = statusService.addFamilyGame(userName, gameCode,
					gameTime, gameNote);
			if (C.parent.ADD_FAMILY_GAME_SUCCESS.equals(result)) {
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.parent.ADD_FAMILY_GAME_SUCCESS,
								C.parent.ADD_FAMILY_GAME_SUCCESS),
						new TypeToken<Message<String>>() {
						}.getType()));
			} else {
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.parent.ADD_FAMILY_GAME_UNSUCCESS,
								result), new TypeToken<Message<String>>() {
						}.getType()));
			}
			// 将Json数据放入Session中，变量名为"JsonString"
			return "MessagePage";
		default:
			return ERROR;
		}
	}

}
