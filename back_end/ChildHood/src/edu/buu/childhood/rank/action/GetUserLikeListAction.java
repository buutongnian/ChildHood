package edu.buu.childhood.rank.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.common.Page;
import edu.buu.childhood.rank.pojo.LikeDetail;
import edu.buu.childhood.rank.service.RankService;

public class GetUserLikeListAction extends ActionSupport {
	private static final long serialVersionUID = 3434397927233823732L;
	private String webOrApp;
	private String userName;
	private int pageNum;
	private Gson json = new Gson();
	private RankService rankService;

	public void setRankService(RankService rankService) {
		this.rankService = rankService;
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
			Page<LikeDetail> likeDetailList = rankService
					.getUserLikeDetailList(userName, pageNum);
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			if (likeDetailList != null) {
				Message<Page<LikeDetail>> message = new Message<Page<LikeDetail>>(
						C.rank.LIKE_LIST_QUERY_SUCCESS, likeDetailList);
				session.setAttribute("JsonString", json.toJson(message,
						new TypeToken<Message<Page<LikeDetail>>>() {
						}.getType()));
			} else {
				Message<String> message = new Message<String>(C.def.ERROR,
						C.def.ERROR);
				session.setAttribute("JsonString",
						json.toJson(message, new TypeToken<Message<String>>() {
						}.getType()));
			}
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
