package edu.buu.childhood.status.action;

import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.status.pojo.ComputeCanJoin;
import edu.buu.childhood.status.pojo.InfoList;
import edu.buu.childhood.status.service.StatusService;

public class GameCanJoinAction extends ActionSupport {

	/**
	 * GameCanJoinAction序列化ID
	 */
	private static final long serialVersionUID = -7394150141894611557L;

	private StatusService statusService;
	private String webOrApp;
	private String userName;

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

	@Override
	public String execute() throws Exception {
		switch (webOrApp) {
		case C.def.WEB_REQUEST:
			return null;
		case C.def.APP_REQUEST:
			InfoList<ComputeCanJoin> gameCanJoin = statusService
					.queryGameCanJoin(userName);
			// 将Json数据放入Session中，变量名为"JsonString"
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			session.setAttribute("JsonString", json.toJson(
					new Message<InfoList<ComputeCanJoin>>(
							C.convene.COMPUTE_CAN_JOIN_SUCCESS, gameCanJoin),
					new TypeToken<Message<InfoList<ComputeCanJoin>>>() {
					}.getType()));
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
