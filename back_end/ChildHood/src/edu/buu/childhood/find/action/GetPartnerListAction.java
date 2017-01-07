package edu.buu.childhood.find.action;

import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.common.Page;
import edu.buu.childhood.find.pojo.FindResult;
import edu.buu.childhood.find.service.FindService;

public class GetPartnerListAction extends ActionSupport {
	/**
	 * GetParterListAction序列化ID
	 */
	private static final long serialVersionUID = 6267089071110954678L;
	private String webOrApp;
	private String userName;
	private int pageNum;

	private FindService findService;
	private Gson json = new Gson();

	public void setFindService(FindService findService) {
		this.findService = findService;
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
			System.out.println("GetPartnerListAction---userName:" + userName);
			Page<FindResult> resultPage = findService.getFindResult(userName,
					pageNum);
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			session.setAttribute("JsonString", json.toJson(
					new Message<Page<FindResult>>(C.find.GET_FIND_INFO_SUCCESS,
							resultPage),
					new TypeToken<Message<Page<FindResult>>>() {
					}.getType()));
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
