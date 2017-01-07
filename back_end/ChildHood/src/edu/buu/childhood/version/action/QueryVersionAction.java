package edu.buu.childhood.version.action;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;
import edu.buu.childhood.version.pojo.VersionBean;
import edu.buu.childhood.version.service.VersionService;

public class QueryVersionAction extends ActionSupport {

	/**
	 * QueryVersionAction序列化ID
	 */
	private static final long serialVersionUID = -3090517367899593820L;
	private VersionService versionService;

	public void setVersionService(VersionService versionService) {
		this.versionService = versionService;
	}

	@Override
	public String execute() throws Exception {
		HttpSession session = ServletActionContext.getRequest().getSession();
		if (session.getAttribute("userName")!=null) {
			List<VersionBean> versionBeans = versionService.getAllVersions();
			session.setAttribute("versionBeans", versionBeans);
			return SUCCESS;
		}else{
			return ERROR;
		}
	}
}
