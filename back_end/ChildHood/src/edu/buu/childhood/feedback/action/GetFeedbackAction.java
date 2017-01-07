package edu.buu.childhood.feedback.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Page;
import edu.buu.childhood.feedback.pojo.Feedback;
import edu.buu.childhood.feedback.service.FeedbackService;

public class GetFeedbackAction extends ActionSupport {
	private static final long serialVersionUID = -2594341927526848778L;
	private String webOrApp;
	private String moduleName;
	private int pageNum;

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

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
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
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			if (session.getAttribute("userName") != null) {
				Page<Feedback> feedbackPage = feedbackService
						.getFeedbackByModule(moduleName, pageNum);
				session.setAttribute("feedbackPage", feedbackPage);
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
