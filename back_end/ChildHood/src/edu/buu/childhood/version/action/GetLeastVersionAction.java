package edu.buu.childhood.version.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.version.pojo.VersionBean;
import edu.buu.childhood.version.service.VersionService;

public class GetLeastVersionAction extends ActionSupport {
	/**
	 * GetLeastVersionAction序列化ID
	 */
	private static final long serialVersionUID = 8256625359297759635L;
	private String webOrApp;
	private String system;

	private VersionService versionService;
	private Gson json = new Gson();

	public void setVersionService(VersionService versionService) {
		this.versionService = versionService;
	}

	public String getWebOrApp() {
		return webOrApp;
	}

	public void setWebOrApp(String webOrApp) {
		this.webOrApp = webOrApp;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	@Override
	public String execute() throws Exception {
		switch (webOrApp) {
		case C.def.WEB_REQUEST:
			return null;
		case C.def.APP_REQUEST:
			VersionBean leastVersion = versionService.getLeastVersion(system);
			// 将Json数据放入Session中，变量名为"JsonString"
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			session.setAttribute("JsonString", json.toJson(new Message<VersionBean>(
					C.version.GET_LEAST_VERSION_SUCCESS, leastVersion),
					new TypeToken<Message<VersionBean>>() {
					}.getType()));
			return "MessagePage";
		default:
			return ERROR;
		}
	}
}
