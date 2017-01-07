package edu.buu.childhood.onekey.action;

import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.onekey.service.OnekeyService;

public class ChangeGameInfoAction extends ActionSupport {

	/**
	 * ChangeGameInfoAction序列化ID
	 */
	private static final long serialVersionUID = -4343243283615351326L;
	private OnekeyService onekeyService;
	private Gson json = new Gson();
	private String webOrApp;

	private int gameId;
	private String gatherPlace;
	private String customInf;
	private int customCount;

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

	public String getGatherPlace() {
		return gatherPlace;
	}

	public void setGatherPlace(String gatherPlace) {
		this.gatherPlace = gatherPlace;
	}

	public String getCustomInf() {
		return customInf;
	}

	public void setCustomInf(String customInf) {
		this.customInf = customInf;
	}

	public int getCustomCount() {
		return customCount;
	}

	public void setCustomCount(int customCount) {
		this.customCount = customCount;
	}

	@Override
	public String execute() throws Exception {
		switch (webOrApp) {
		case C.def.WEB_REQUEST:
			return null;
		case C.def.APP_REQUEST:
			if (onekeyService.changeGameInfo(gameId, gatherPlace, customInf,
					customCount)) {
				// 将Json数据放入Session中，变量名为"JsonString"
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				// 游戏信息更新成功标识
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.onekey.CHANGE_GAME_INFO_SUCCESS,
								C.onekey.CHANGE_GAME_INFO_SUCCESS),
						new TypeToken<Message<String>>() {
						}.getType()));
			} else {
				// 将Json数据放入Session中，变量名为"JsonString"
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				// 游戏信息更新失败标识
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.onekey.CHANGE_GAME_INFO_UNSUCCESS,
								C.onekey.CHANGE_GAME_INFO_UNSUCCESS),
						new TypeToken<Message<String>>() {
						}.getType()));
			}
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
