package edu.buu.childhood.achvmt.event.pojo;

/*
 * 2016/9/27
 * @Author Joe
 * 
 * 映射T_ACHVMT_EVENT_TEMPLATE表
 */
public class EventTemplate {
	private int templateId;
	private String templateCode;
	private String templateName;
	private String templateStr;
	private String templateParams;
	private int templateModule;
	private String templateMopduleName;

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateStr() {
		return templateStr;
	}

	public void setTemplateStr(String templateStr) {
		this.templateStr = templateStr;
	}

	public String getTemplateParams() {
		return templateParams;
	}

	public void setTemplateParams(String templateParams) {
		this.templateParams = templateParams;
	}

	public int getTemplateModule() {
		return templateModule;
	}

	public void setTemplateModule(int templateModule) {
		this.templateModule = templateModule;
	}

	public String getTemplateMopduleName() {
		return templateMopduleName;
	}

	public void setTemplateMopduleName(String templateMopduleName) {
		this.templateMopduleName = templateMopduleName;
	}

}
