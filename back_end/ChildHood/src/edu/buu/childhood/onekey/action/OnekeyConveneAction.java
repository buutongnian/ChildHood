package edu.buu.childhood.onekey.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.onekey.pojo.GameStatus;
import edu.buu.childhood.onekey.service.OnekeyService;

public class OnekeyConveneAction extends ActionSupport {

	/**
	 * OnekeyConveneAction序列化ID
	 */
	private static final long serialVersionUID = 3217223161228517810L;
	private OnekeyService onekeyService;
	private Gson json = new Gson();
	private String webOrApp;

	private int gameCode;
	private String userName;
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

	public int getGameCode() {
		return gameCode;
	}

	public void setGameCode(int gameCode) {
		this.gameCode = gameCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
			if (onekeyService.onekeyConvene(gameCode, userName, gatherPlace,
					customInf, customCount)) {
				GameStatus gameStatus = onekeyService
						.getUserFoundGame(userName);
				// 将Json数据放入Session中，变量名为"JsonString"
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				// 一键呼唤成功标识
				session.setAttribute("JsonString", json.toJson(
						new Message<GameStatus>(C.onekey.CONVENE_SUCCESS,
								gameStatus),
						new TypeToken<Message<GameStatus>>() {
						}.getType()));
			} else {
				// 将Json数据放入Session中，变量名为"JsonString"
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				// 一键呼唤失败标识
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.onekey.CONVENE_UNSUCCESS,
								C.onekey.CONVENE_UNSUCCESS),
						new TypeToken<Message<String>>() {
						}.getType()));
			}
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
