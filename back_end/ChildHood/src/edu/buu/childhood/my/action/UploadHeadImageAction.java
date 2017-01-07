package edu.buu.childhood.my.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.my.service.MyService;
import edu.buu.childhood.util.Base64;
import edu.buu.childhood.util.ImageXMLUtil;

public class UploadHeadImageAction extends ActionSupport {
	/**
	 * UploadHeadImageAction序列化ID
	 */
	private static final long serialVersionUID = -4532438945779506252L;
	private String webOrApp;
	private String userName;
	private String userHeadImage;

	private MyService myService;
	private Gson json = new Gson();

	public void setMyService(MyService myService) {
		this.myService = myService;
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

	public String getUserHeadImage() {
		return userHeadImage;
	}

	public void setUserHeadImage(String userHeadImage) {
		this.userHeadImage = userHeadImage;
	}

	@Override
	public String execute() throws Exception {
		switch (webOrApp) {
		case C.def.WEB_REQUEST:
			return null;
		case C.def.APP_REQUEST:
			
			String root = ServletActionContext.getServletContext().getRealPath(
					"/avatar");
			
			byte[] imageData = Base64.decode(ImageXMLUtil.getBase64Image(userHeadImage));
			
			String imageUrl = myService.updateHeadImage(userName,root, imageData);

			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			session.setAttribute("JsonString", json.toJson(new Message<String>(
					C.my.HEAD_IMAGE_UPDATE_SUCCESS, imageUrl),
					new TypeToken<Message<String>>() {
					}.getType()));
			return "MessagePage";
		default:
			return ERROR;
		}

	}
}
