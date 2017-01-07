package edu.buu.childhood.game.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.game.pojo.GameHead;
import edu.buu.childhood.game.service.GameService;

public class UniqueGameHeadAction extends ActionSupport {
	/**
	 * UniqueGameHeadAction序列化ID
	 */
	private static final long serialVersionUID = -8443869342316385693L;
	private String webOrApp;// 请求方式（Web端或App端），内容由C.java常量文件定义
	private GameService gameService;
	private Gson json = new Gson();

	private int gameCode;

	public String getWebOrApp() {
		return webOrApp;
	}

	public void setWebOrApp(String webOrApp) {
		this.webOrApp = webOrApp;
	}

	public GameService getGameService() {
		return gameService;
	}

	public void setGameService(GameService gameService) {
		this.gameService = gameService;
	}

	public int getGameCode() {
		return gameCode;
	}

	public void setGameCode(int gameCode) {
		this.gameCode = gameCode;
	}

	@Override
	public String execute() throws Exception {
		switch (webOrApp) {
		case C.def.WEB_REQUEST:
			return null;
		case C.def.APP_REQUEST:
			GameHead gameHead = gameService.getGameHead(gameCode);
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			if (gameHead != null) {
				Message<GameHead> message = new Message<GameHead>(
						C.game.UNIQUE_GAME_HEAD_QUERY_SUCCESS, gameHead);
				session.setAttribute("JsonString", json.toJson(message,
						new TypeToken<Message<GameHead>>() {
						}.getType()));
			} else {
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.def.ERROR, C.def.ERROR),
						new TypeToken<Message<String>>() {
						}.getType()));
			}
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
