package edu.buu.childhood.game.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.game.pojo.GameContent;
import edu.buu.childhood.game.service.GameService;

/**
 * 2016/6/28 游戏详情Action API接口，用于App端或WEB端请求游戏详情信息 保留webOrApp标志位，为后期添加WEB端支持留下接口
 * 
 * @author joe
 *
 */
public class GameContentAction extends ActionSupport {

	/**
	 * GameContentAction序列化ID
	 */
	private static final long serialVersionUID = -5810893810082907955L;

	private String webOrApp;// 请求方式（Web端或App端），内容由C.java常量文件定义
	private GameService gameService;
	private Gson json = new Gson();

	private String userName;
	private int gameCode;

	// 通过Spring装配gameServiceImpl
	public void setGameService(GameService gameService) {
		this.gameService = gameService;
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

	@Override
	public String execute() throws Exception {
		switch (webOrApp) {
		case C.def.WEB_REQUEST:
			return null;
		case C.def.APP_REQUEST:
			GameContent gameContent = gameService.getGameConntent(userName,
					gameCode);
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			if (gameContent != null) {
				session.setAttribute("JsonString",
						json.toJson(
								new Message<GameContent>(
										C.game.GAME_CONTENT_QUERY_SUCCESS,
										gameContent),
								new TypeToken<Message<GameContent>>() {
								}.getType()));
			} else {
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.def.ERROR, C.def.ERROR),
						new TypeToken<Message<GameContent>>() {
						}.getType()));
			}
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
