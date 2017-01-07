package edu.buu.childhood.onekey.action;

import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.onekey.pojo.GameInfo;
import edu.buu.childhood.onekey.service.OnekeyService;

public class GetGameInfoAction extends ActionSupport{
	/**
	 * GetGameInfoAction序列化ID
	 */
	private static final long serialVersionUID = -219313439150408954L;
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
			GameInfo gameInfo = onekeyService.getGameInfo(gameId);
			// 将Json数据放入Session中，变量名为"JsonString"
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			// 游戏信息
			session.setAttribute("JsonString", json.toJson(new Message<GameInfo>(
					C.onekey.GET_GAME_INFO_SUCCESS, gameInfo),
					new TypeToken<Message<GameInfo>>() {
					}.getType()));
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
