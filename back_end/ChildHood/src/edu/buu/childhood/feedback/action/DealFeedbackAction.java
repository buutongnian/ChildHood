package edu.buu.childhood.feedback.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.common.C;
import edu.buu.childhood.feedback.service.FeedbackService;

public class DealFeedbackAction extends ActionSupport {
	private static final long serialVersionUID = -8275561715738776443L;
	private String webOrApp;
	private int feedbackId;
	private String result;

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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String execute() throws Exception {
		switch (webOrApp) {
		case C.def.WEB_REQUEST:
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			String dealUser = (String) session.getAttribute("userName");
			if (dealUser != null) {
				feedbackService.dealFeedback(feedbackId, dealUser, result);
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
