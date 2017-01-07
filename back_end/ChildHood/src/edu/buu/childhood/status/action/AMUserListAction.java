package edu.buu.childhood.status.action;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.status.pojo.AMUserList;
import edu.buu.childhood.status.service.StatusService;

public class AMUserListAction extends ActionSupport{
	/**
	 * AMUserListAction序列化ID
	 */
	private static final long serialVersionUID = 323575155489526684L;
	private StatusService statusService;
	private String webOrApp;
	private double latitude;
	private double longitude;
	
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

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	@Override
	public String execute() throws Exception {
		switch (webOrApp) {
		case C.def.WEB_REQUEST:
			return null;
		case C.def.APP_REQUEST:
			List<AMUserList> amUserList = statusService
					.queryAMUserList(latitude, longitude);
			// 将Json数据放入Session中，变量名为"JsonString"
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			session.setAttribute("JsonString", json.toJson(
					new Message<List<AMUserList>>(
							C.convene.COMPUTE_AM_USER_LIST_SUCCESS, amUserList),
					new TypeToken<Message<List<AMUserList>>>() {
					}.getType()));
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
