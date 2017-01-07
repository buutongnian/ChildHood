package edu.buu.childhood.login.service;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import api.ucenter.Client;
import edu.buu.childhood.common.C;
import edu.buu.childhood.login.dao.PasswordDao;
import edu.buu.childhood.login.dao.RegisterDao;
import edu.buu.childhood.login.pojo.VCode;
import edu.buu.childhood.my.pojo.User;
import edu.buu.childhood.status.service.StatusService;
import edu.buu.childhood.util.MD5;
import edu.buu.childhood.util.ParamUtil;
import edu.buu.childhood.util.SMSUtil;

public class PasswordServiceImpl implements PasswordService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private PasswordDao passwordDao;
	@Autowired
	private StatusService statusService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private RegisterDao registerDao;

	@Override
	@Transactional
	public boolean sendSms(String telnum) {
		try {
			VCode vcodeFromDB = passwordDao.queryByTel(telnum);
			if (vcodeFromDB != null) {
				if (((new Date()).getTime() - vcodeFromDB.getGenerateTime()
						.getTime()) < C.vcode.VCODE_TIMEOUT) {
					return false;
				}
				vcodeFromDB.setVcodeStatus(C.vcode.SMS_RESEND);
				passwordDao.update(vcodeFromDB);
			}
			String vcode = ParamUtil.VCode.getVcode();
			String errCode = SMSUtil.sendSMS(telnum,
					ParamUtil.VCode.generate(vcode));
			String smsStatus = errCode == null ? "0" : errCode;
			if ("0".equals(smsStatus)) {
				passwordDao.insert(new VCode(telnum, vcode, smsStatus,
						C.vcode.VCODE_CANT_RESEND));
				return true;
			} else {
				passwordDao.insert(new VCode(telnum, vcode, smsStatus,
						C.vcode.SMS_SEND_ERROR));
				return false;
			}
		} catch (Exception e) {
			logger.error(e.getStackTrace());
			return false;
		}
	}

	@Override
	@Transactional
	public boolean check(String userName, String vcode) {
		VCode vcodeFromDB = passwordDao.queryByTel(userName);
		if (vcodeFromDB == null) {
			return false;
		}
		if (!C.vcode.VCODE_VERIFIED.equals(vcodeFromDB.getVcodeStatus())) {
			if (vcodeFromDB.getVcode().equals(vcode)) {
				vcodeFromDB.setVcodeStatus(C.vcode.VCODE_VERIFIED);
				passwordDao.update(vcodeFromDB);
				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional
	public boolean resetPassword(String userName, String password) {
		User userFromDB = registerDao.queryByUserName(userName);
		if (userFromDB != null) {
			String email = userFromDB.getEmail();
			if (email == null) {
				email = userFromDB.getUserId().toLowerCase() + "@abc.com";
			}
			Client client = new Client();
			String returns = null;
			try {
				returns = client.uc_user_edit(userFromDB.getUserName(),
						userFromDB.getUserPwd(), password, email, 1, "", "");
				if (returns == null || returns.isEmpty()) {
					throw new Exception("同步更新论坛用户方法执行失败，无返回值");
				} else if (Integer.parseInt(returns) < 0) {
					throw new Exception("同步更新论坛用户失败，错误码：" + returns);
				}
				if (passwordDao.update(userName, MD5.enc32Bit(password)) == 1) {
					logger.info("[" + userName + "]修改密码成功");
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				logger.error(e, e);
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public String changePassword(String userName, String oldPwd, String newPwd) {
		String checkResult = loginService.loginCheck(userName, oldPwd);
		if (C.LoginStatus.LOGIN_SUCCESS.equals(checkResult)) {
			User userFromDB = registerDao.queryByUserName(userName);
			String email = userFromDB.getEmail();
			if (email == null) {
				email = userFromDB.getUserId().toLowerCase() + "@abc.com";
			}
			Client client = new Client();
			String returns = null;
			try {
				returns = client.uc_user_edit(userFromDB.getUserName(),
						userFromDB.getUserPwd(), newPwd, email, 1, "", "");
				if (returns == null || returns.isEmpty()) {
					throw new Exception("同步更新论坛用户方法执行失败，无返回值");
				} else if (Integer.parseInt(returns) < 0) {
					throw new Exception("同步更新论坛用户失败，错误码：" + returns);
				}
				logger.info("[" + userName + "]修改密码成功");
			} catch (Exception e) {
				logger.error(e, e);
				return C.def.ERROR;
			}
			passwordDao.update(userName, newPwd);
		}
		return checkResult;
	}
}
