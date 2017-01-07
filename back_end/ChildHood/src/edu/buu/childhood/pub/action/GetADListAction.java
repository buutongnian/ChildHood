package edu.buu.childhood.pub.action;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.pub.pojo.ADInfo;
import edu.buu.childhood.pub.service.PubService;

public class GetADListAction extends ActionSupport {
	private static final long serialVersionUID = -8196077808917334801L;
	private String webOrApp;
	private String userName;

	private PubService pubService;

	private Gson json = new Gson();

	public void setPubService(PubService pubService) {
		this.pubService = pubService;
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
			List<ADInfo> list = pubService.getADList(userName);
			if (list != null) {
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<List<ADInfo>>(C.pub.GET_ADLIST_SUCCESS,
								list), new TypeToken<Message<List<ADInfo>>>() {
						}.getType()));
			} else {
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.pub.GET_ADLIST_UNSUCCESS,
								C.pub.GET_ADLIST_UNSUCCESS),
						new TypeToken<Message<String>>() {
						}.getType()));
			}
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
