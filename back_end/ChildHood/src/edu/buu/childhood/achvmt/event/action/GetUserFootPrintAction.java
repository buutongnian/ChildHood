package edu.buu.childhood.achvmt.event.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;
import edu.buu.childhood.achvmt.event.pojo.UserEventList;
import edu.buu.childhood.achvmt.event.service.EventService;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.common.Page;

public class GetUserFootPrintAction extends ActionSupport {
	private static final long serialVersionUID = -4288551549239768317L;
	private String webOrApp;
	private String userName;
	private int pageNum;

	private EventService eventService;

	private Gson json = new Gson();

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
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
			Page<UserEventList> userFootPrintList = eventService
					.getUserFootPrint(userName, pageNum);
			if (userFootPrintList != null
					&& !userFootPrintList.getDataList().isEmpty()) {
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<Page<UserEventList>>(
								C.achvmt.GET_USER_FOOT_PRINT_SUCCESS,
								userFootPrintList),
						new TypeToken<Message<Page<UserEventList>>>() {
						}.getType()));
			} else {
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(
								C.achvmt.GET_USER_FOOT_PRINT_UNSUCCESS,
								C.achvmt.GET_USER_FOOT_PRINT_UNSUCCESS),
						new TypeToken<Message<String>>() {
						}.getType()));
			}
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
