package edu.buu.childhood.rank.action;

import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.common.Page;
import edu.buu.childhood.rank.pojo.UserRank;
import edu.buu.childhood.rank.service.RankService;

/**
 * 
 * 2016/06/28
 * 查询孩子王排行榜
 * @author tt
 * @param webOrApp 请求的方式（web or app）
 * @param userName 用户名
 * @param pageNum 当前第几页
 *
 *
 */
public class UserRankAction extends ActionSupport {
	
	/**
	 * UserRankAction序列化ID
	 */
	private static final long serialVersionUID = 1944191797070993572L;
	
	private String webOrApp;
	private String userName;
	private int pageNum;
	private Gson json=new Gson();
	private RankService rankService;
	
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

	public RankService getRankService() {
		return rankService;
	}

	public void setRankService(RankService rankService) {
		this.rankService = rankService;
	}

	@Override
	public String execute() throws Exception {
		switch(webOrApp){
		case C.def.WEB_REQUEST:return null;
		case C.def.APP_REQUEST:
			Page<UserRank> userRank=rankService.getUserRank(userName, pageNum);
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			if (userRank != null) {
				Message<Page<UserRank>> message=new Message<Page<UserRank>>("Page<UserRank>",userRank);
				session.setAttribute("JsonString", json.toJson(message,new TypeToken<Message<Page<UserRank>>>(){}.getType()));
			}else{
				Message<String> message=new Message<String>("error","error");
				session.setAttribute("JsonString", json.toJson(message,new TypeToken<Message<String>>(){}.getType()));
				
			}
		}
		return "MessagePage";
	}
}
