package edu.buu.childhood.login.service;

public interface PasswordService {
	public boolean sendSms(String userName);

	public boolean check(String userName, String vcode);

	public boolean resetPassword(String userName, String password);

	public String changePassword(String userName, String oldPwd, String newPwd);
}
