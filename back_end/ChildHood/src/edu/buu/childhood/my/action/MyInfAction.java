package edu.buu.childhood.my.action;

import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.my.pojo.User;
import edu.buu.childhood.my.service.MyService;

public class MyInfAction extends ActionSupport {
	/**
	 * MyInfAction序列化ID
	 */
	private static final long serialVersionUID = 6908621710569692625L;
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
			User userInf = myService.getUserInf(userName);
			// 将Json数据放入Session中，变量名为"JsonString"
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			// 个人信息查询成功，返回个人信息
			session.setAttribute("JsonString", json.toJson(
					new Message<User>(C.my.USER_INF_QUERY_SUCCESS, userInf),
					new TypeToken<Message<User>>() {
					}.getType()));
			return "MessagePage";
		default:
			return ERROR;

		}
	}
}
