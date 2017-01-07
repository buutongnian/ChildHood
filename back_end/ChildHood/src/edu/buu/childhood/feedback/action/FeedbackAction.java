package edu.buu.childhood.feedback.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.feedback.service.FeedbackService;

public class FeedbackAction extends ActionSupport {
	private static final long serialVersionUID = -6574053472862880191L;
	private String webOrApp;
	private String userName;
	private String feedbackContent;
	private String feedbackModule;

	private Gson json = new Gson();

	private FeedbackService feedbackService;

	public void setFeedbackService(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
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

	public String getFeedbackContent() {
		return feedbackContent;
	}

	public void setFeedbackContent(String feedbackContent) {
		this.feedbackContent = feedbackContent;
	}

	public String getFeedbackModule() {
		return feedbackModule;
	}

	public void setFeedbackModule(String feedbackModule) {
		this.feedbackModule = feedbackModule;
	}

	@Override
	public String execute() throws Exception {
		switch (webOrApp) {
		case C.def.WEB_REQUEST:
			return null;
		case C.def.APP_REQUEST:
			if (feedbackService.addFeedback(userName, feedbackContent,
					feedbackModule)) {
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.feedback.FEEDBACK_SUCCESS,
								C.feedback.FEEDBACK_SUCCESS),
						new TypeToken<Message<String>>() {
						}.getType()));
			} else {
				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				session.setAttribute("JsonString", json.toJson(
						new Message<String>(C.feedback.FEEDBACK_UNSUCCESS,
								C.feedback.FEEDBACK_UNSUCCESS),
						new TypeToken<Message<String>>() {
						}.getType()));
			}
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
