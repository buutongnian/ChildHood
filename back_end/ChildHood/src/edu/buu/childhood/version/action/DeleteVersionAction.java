package edu.buu.childhood.version.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.version.service.VersionService;

public class DeleteVersionAction extends ActionSupport {
	/**
	 * DeleteVersionAction序列化ID
	 */
	private static final long serialVersionUID = 392781535517442240L;
	private int versionId;
	private VersionService versionService;

	public void setVersionService(VersionService versionService) {
		this.versionService = versionService;
	}

	public int getVersionId() {
		return versionId;
	}

	public void setVersionId(int versionId) {
		this.versionId = versionId;
	}

	@Override
	public String execute() throws Exception {
		HttpSession session = ServletActionContext.getRequest().getSession();
		if (session.getAttribute("userName")!=null) {
			versionService.delete(versionId);
			return SUCCESS;
		} else {
			return ERROR;
		}
	}
}
