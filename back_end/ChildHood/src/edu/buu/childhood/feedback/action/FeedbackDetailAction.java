package edu.buu.childhood.feedback.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.common.C;
import edu.buu.childhood.feedback.pojo.Feedback;
import edu.buu.childhood.feedback.service.FeedbackService;

public class FeedbackDetailAction extends ActionSupport {
	private static final long serialVersionUID = -8334707619749723313L;
	private String webOrApp;
	private int feedbackId;

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

	public int getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(int feedbackId) {
		this.feedbackId = feedbackId;
	}

	@Override
	public String execute() throws Exception {
		switch (webOrApp) {
		case C.def.WEB_REQUEST:
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			if (session.getAttribute("userName") != null) {
				Feedback feedback = feedbackService.getFeedbackById(feedbackId);
				session.setAttribute("feedback", feedback);
				return SUCCESS;
			} else {
				return ERROR;
			}
		case C.def.APP_REQUEST:
			return null;
		default:
			return ERROR;
		}
	}
}
