package edu.buu.childhood.game.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.common.Page;
import edu.buu.childhood.game.pojo.GameHead;
import edu.buu.childhood.game.service.GameService;

/**
 * 2016/6/28 游戏头信息Action
 * API接口，主要用于App端按筛选条件请求游戏列表，后期可加入WEB端支持，作为WEB端游戏列表展现数据请求接口
 * 保留webOrApp标志位，为后期添加WEB端支持留下接口
 * 
 * @author joe
 *
 */
public class GameHeadAction extends ActionSupport {

	/**
	 * GameHeadAction序列化ID
	 */
	private static final long serialVersionUID = -3279404765864375697L;

	private String webOrApp;// 请求方式（Web端或App端），内容由C.java常量文件定义
	private GameService gameService;
	private Gson json = new Gson();

	private String userName;
	private int gameArea;
	private int ageCode;
	private int memNumCode;
	private int pageNum;

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

	public int getGameArea() {
		return gameArea;
	}

	public void setGameArea(int gameArea) {
		this.gameArea = gameArea;
	}

	public int getAgeCode() {
		return ageCode;
	}

	public void setAgeCode(int ageCode) {
		this.ageCode = ageCode;
	}

	public int getMemNumCode() {
		return memNumCode;
	}

	public void setMemNumCode(int memNumCode) {
		this.memNumCode = memNumCode;
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
			Page<GameHead> gameHeadPage = gameService.getGameHeadPage(userName,gameArea,
					ageCode, memNumCode, pageNum);
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			if (gameHeadPage != null) {
				Message<Page<GameHead>> message = new Message<Page<GameHead>>(
						C.game.GAME_HEAD_QUERY_SUCCESS, gameHeadPage);
				session.setAttribute("JsonString", json.toJson(message,
						new TypeToken<Message<Page<GameHead>>>() {
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
