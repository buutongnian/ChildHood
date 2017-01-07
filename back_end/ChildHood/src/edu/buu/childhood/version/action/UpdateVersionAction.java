package edu.buu.childhood.version.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.version.service.VersionService;

public class UpdateVersionAction extends ActionSupport {
	/**
	 * UpdateVersionAction序列化ID
	 */
	private static final long serialVersionUID = -7455813856115235230L;
	private int versionId;
	private String version;
	private String packageUrl;
	private String system;

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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPackageUrl() {
		return packageUrl;
	}

	public void setPackageUrl(String packageUrl) {
		this.packageUrl = packageUrl;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	@Override
	public String execute() throws Exception {
		HttpSession session = ServletActionContext.getRequest().getSession();
		if (session.getAttribute("userName")!=null) {
			versionService.updateVersionInfo(versionId, version, packageUrl,
					system);
			return SUCCESS;
		} else {
			return ERROR;
		}
	}
}
