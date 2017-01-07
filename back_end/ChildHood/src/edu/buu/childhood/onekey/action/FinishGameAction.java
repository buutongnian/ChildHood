package edu.buu.childhood.onekey.action;

import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.onekey.service.OnekeyService;

public class FinishGameAction extends ActionSupport {

	/**
	 * FinishGameAction序列化ID
	 */
	private static final long serialVersionUID = 1455027000205792957L;
	private OnekeyService onekeyService;
	private Gson json = new Gson();
	private String webOrApp;

	private int gameId;

	public void setOnekeyService(OnekeyService onekeyService) {
		this.onekeyService = onekeyService;
	}

	public String getWebOrApp() {
		return webOrApp;
	}

	public void setWebOrApp(String webOrApp) {
		this.webOrApp = webOrApp;
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
			if (onekeyService.finishGame(gameId)) {
				// 将Json数据放入Session中，变量名为"JsonString"
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				// 游戏结束成功标识
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.onekey.GAME_FINISHED_SUCCESS,
								C.onekey.GAME_FINISHED_SUCCESS),
						new TypeToken<Message<String>>() {
						}.getType()));
			} else {
				// 将Json数据放入Session中，变量名为"JsonString"
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				// 游戏结束失败标识
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.onekey.GAME_FINISHED_UNSUCCESS,
								C.onekey.GAME_FINISHED_UNSUCCESS),
						new TypeToken<Message<String>>() {
						}.getType()));
			}
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
