package edu.buu.childhood.rank.action;

import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.common.Page;
import edu.buu.childhood.rank.pojo.GameRank;
import edu.buu.childhood.rank.service.RankService;

/**
 * 2016/06/28
 * 游戏排名（游戏热度）
 * @author tt
 * @param webOrApp 请求的方式（web or app）
 * @param userName 用户名
 * @param pageNum 当前第几页
 *
 */
public class GameRankAction extends ActionSupport {
	/**
	 * GameRankActing序列化ID
	 */
	private static final long serialVersionUID = 4151621449767738495L;
	
	private String webOrApp;// 请求方式（Web端或App端），内容由C.java常量文件定义
	private String userName;
	private int pageNum;
	private Gson json = new Gson();
	private RankService rankService;

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
			Page<GameRank> gameRank = rankService
					.getGameRank(userName, pageNum);// 获取游戏排名
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			if (gameRank != null) {
				Message<Page<GameRank>> message=new Message<Page<GameRank>>("Page<GameRank>",gameRank);
				session.setAttribute("JsonString", json.toJson(message,new TypeToken<Message<Page<GameRank>>>(){}.getType()));
			}else{
				Message<String> message=new Message<String>("error","error");
				session.setAttribute("JsonString", json.toJson(message,new TypeToken<Message<String>>(){}.getType()));
				
			}

		}
		return "MessagePage";
	}

}
