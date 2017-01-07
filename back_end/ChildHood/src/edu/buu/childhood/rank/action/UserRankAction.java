package edu.buu.childhood.rank.action;

//import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.rank.pojo.RankList;
//import edu.buu.childhood.my.pojo.User;
import edu.buu.childhood.rank.service.RankService;

//import edu.buu.childhood.status.pojo.MemberInfo;

/**
 * 
 * 2016/06/28 查询孩子王排行榜
 * 
 * @author tt
 * @param webOrApp
 *            请求的方式（web or app）
 * @param userName
 *            用户名
 * @param pageNum
 *            当前第几页
 *
 *            2016/10/4
 * 
 * @author joe
 * @modify 去掉pageNum页码信息，返回TOPN数据
 */
public class UserRankAction extends ActionSupport {
	private static final long serialVersionUID = 6886465692978498541L;
	private String webOrApp;
	private String userName;
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

	@Override
	public String execute() throws Exception {
		switch (webOrApp) {
		case C.def.WEB_REQUEST:
			return null;
		case C.def.APP_REQUEST:
			// List<User> user = rankService.getUserRank(userName);
			// List<MemberInfo> memberInfoList =
			// rankService.getUserRank(userName);
			RankList rankList = rankService.getUserRank(userName);
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			if (rankList != null) {
				Message<RankList> message = new Message<RankList>(
						C.rank.USER_RANK_QUERY_SUCCESS, rankList);
				session.setAttribute("JsonString", json.toJson(message,
						new TypeToken<Message<RankList>>() {
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
