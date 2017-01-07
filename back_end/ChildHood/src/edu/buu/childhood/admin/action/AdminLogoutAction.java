package edu.buu.childhood.admin.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class AdminLogoutAction extends ActionSupport {
	/**
	 * AdminLogoutAction序列化ID
	 */
	private static final long serialVersionUID = -7527361311009685908L;

	@Override
	public String execute() throws Exception {
		HttpSession session=ServletActionContext.getRequest().getSession();
		if(session.getAttribute("userName")!=null){
			session.removeAttribute("userName");
		}
		return SUCCESS;
	}
}
