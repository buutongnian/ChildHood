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

public class GameContentAction extends ActionSupport{
	
	/**
	 * GameContentAction序列化ID
	 */
	private static final long serialVersionUID = -5810893810082907955L;
	
	private String webOrApp;//请求方式（Web端或App端），内容由C.java常量文件定义
	private GameService gameService;
	private Gson json=new Gson();
	
	private int gameCode;
	
	//通过Spring装配gameServiceImpl
	public void setGameService(GameService gameService) {
		this.gameService = gameService;
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

	@Override
	public String execute() throws Exception {
		switch(webOrApp){
		case C.def.WEB_REQUEST:return null;
		case C.def.APP_REQUEST:
			GameContent gameContent=gameService.getGameConntent(gameCode);
			HttpSession session=ServletActionContext.getRequest().getSession();
			if(gameContent != null){
				Message<GameContent> message=new Message<GameContent>("GameContent",gameContent);
				session.setAttribute("JsonString", json.toJson(message,new TypeToken<Message<GameContent>>(){}.getType()));
			}else{
				Message<GameContent> message=new Message<GameContent>("error","error");
				session.setAttribute("JsonString", json.toJson(message,new TypeToken<Message<GameContent>>(){}.getType()));
			}
			return "MessagePage";
		default:return ERROR;
		}
	}
}
