package edu.buu.childhood.onekey.action;

import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.onekey.service.OnekeyService;

public class UserExitGameAction extends ActionSupport {

	/**
	 * UserExitGameAction序列化ID
	 */
	private static final long serialVersionUID = 1511312127126013558L;
	private OnekeyService onekeyService;
	private Gson json = new Gson();
	private String webOrApp;

	private int gameId;
	private String userName;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String execute() throws Exception {
		switch (webOrApp) {
		case C.def.WEB_REQUEST:
			return null;
		case C.def.APP_REQUEST:
			onekeyService.exitGame(gameId, userName);
			// 将Json数据放入Session中，变量名为"JsonString"
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			// 用户退出游戏成功标识
			session.setAttribute("JsonString", json.toJson(new Message<String>(
					C.onekey.USER_EXIT_SUCCESS, C.onekey.USER_EXIT_SUCCESS),
					new TypeToken<Message<String>>() {
					}.getType()));
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
