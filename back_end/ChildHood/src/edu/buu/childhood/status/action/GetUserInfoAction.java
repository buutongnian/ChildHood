package edu.buu.childhood.status.action;

import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.status.pojo.UserInfo;
import edu.buu.childhood.status.service.StatusService;

public class GetUserInfoAction extends ActionSupport {
	/**
	 * GetUserInfoAction序列化ID
	 */
	private static final long serialVersionUID = 9186341372253744256L;
	private StatusService statusService;
	private String webOrApp;
	private String userName;
	private String targetUser;

	private Gson json = new Gson();

	public void setStatusService(StatusService statusService) {
		this.statusService = statusService;
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

	public String getTargetUser() {
		return targetUser;
	}

	public void setTargetUser(String targetUser) {
		this.targetUser = targetUser;
	}

	@Override
	public String execute() throws Exception {
		switch (webOrApp) {
		case C.def.WEB_REQUEST:
			return null;
		case C.def.APP_REQUEST:
			UserInfo userInfo = statusService.getUserinfo(targetUser);
			// 将Json数据放入Session中，变量名为"JsonString"
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			session.setAttribute("JsonString", json.toJson(
					new Message<UserInfo>(C.convene.GET_USER_INFO_SUCCESS,
							userInfo), new TypeToken<Message<UserInfo>>() {
					}.getType()));
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
