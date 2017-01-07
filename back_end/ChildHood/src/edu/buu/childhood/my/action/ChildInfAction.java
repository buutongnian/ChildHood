package edu.buu.childhood.my.action;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.my.pojo.ChildInf;
import edu.buu.childhood.my.service.MyService;

public class ChildInfAction extends ActionSupport {
	/**
	 * ChildInfAction序列化ID
	 */
	private static final long serialVersionUID = -8784098745689589278L;
	private String webOrApp;
	private String userName;
	private MyService myService;
	private Gson json = new Gson();

	public void setMyService(MyService myService) {
		this.myService = myService;
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
			List<ChildInf> list = myService.getChildInf(userName);
			// 将Json数据放入Session中，变量名为"JsonString"
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			// 游戏信息更新成功标识
			session.setAttribute("JsonString", json.toJson(
					new Message<List<ChildInf>>(C.my.CHILD_INF_QUERY_SUCCESS, list),
					new TypeToken<Message<List<ChildInf>>>() {
					}.getType()));
			return "MessagePage";
		default:
			return ERROR;
		}
	}

}
