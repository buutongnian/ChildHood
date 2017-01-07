package edu.buu.childhood.onekey.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.onekey.pojo.JoinedStatus;
import edu.buu.childhood.onekey.service.OnekeyService;

public class IsInGameAction extends ActionSupport{
	/**
	 * IsInGameAction序列化ID
	 */
	private static final long serialVersionUID = 5169731960960288194L;
	private OnekeyService onekeyService;
	private Gson json = new Gson();
	private String webOrApp;

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
			JoinedStatus joinedStatus = onekeyService.isInGame(userName);
			if(joinedStatus != null){
			// 将Json数据放入Session中，变量名为"JsonString"
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			// 用户在游戏中标识
			session.setAttribute("JsonString", json.toJson(new Message<JoinedStatus>(
					C.def.USER_IN_GAME, joinedStatus),
					new TypeToken<Message<JoinedStatus>>() {
					}.getType()));
			}else{
				// 将Json数据放入Session中，变量名为"JsonString"
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				// 用户不在游戏中标识
				session.setAttribute("JsonString", json.toJson(new Message<String>(
						C.def.USER_NOT_IN_GAME, C.def.USER_NOT_IN_GAME),
						new TypeToken<Message<String>>() {
						}.getType()));
			}
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
