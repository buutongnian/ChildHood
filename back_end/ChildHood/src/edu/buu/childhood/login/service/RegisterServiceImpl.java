package edu.buu.childhood.login.service;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import api.ucenter.Client;
import edu.buu.childhood.achvmt.service.EventSupport;
import edu.buu.childhood.achvmt.service.TriggerService;
import edu.buu.childhood.common.C;
import edu.buu.childhood.login.dao.RegisterDao;
import edu.buu.childhood.my.pojo.User;
import edu.buu.childhood.util.MD5;
import edu.buu.childhood.util.UUIDUtil;

public class RegisterServiceImpl implements RegisterService, EventSupport {

	@Override
	public String moduleName() {
		return "register";
	}

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private RegisterDao registerDao;

	private TriggerService triggerService;

	public RegisterServiceImpl() {
		super();
	}

	public RegisterServiceImpl(TriggerService triggerService) {
		super();
		this.triggerService = triggerService;
		triggerService.registerModule(this);
	}

	@Override
	@Transactional
	public boolean isRegistered(String userName) {
		if (registerDao.queryByUserName(userName) != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public String perfectInformation(String userName, String userPwd,
			String userNickname, int belongingProvince, int belongingCity,
			int belongingDistrict, String community, String email) {
		User userFromDB = registerDao.queryByUserName(userName);
		if (userFromDB != null) {
			if (email == null) {
				email = userFromDB.getUserId().toLowerCase() + "@abc.com";
			}
			registerDao.update(userName, MD5.enc32Bit(userPwd), userNickname,
					belongingProvince, belongingCity, belongingDistrict,
					community, email);
			Client client = new Client();
			String returns = null;
			try {
				returns = client.uc_user_edit(userFromDB.getUserName(),
						userFromDB.getUserPwd(), userPwd, email, 1, "", "");
				if (returns == null || returns.isEmpty()) {
					throw new Exception("同步更新论坛用户方法执行失败，无返回值");
				} else if (Integer.parseInt(returns) < 0) {
					throw new Exception("同步更新论坛用户失败，错误码：" + returns);
				}
			} catch (Exception e) {
				logger.error(e, e);
			}
			return C.reg.PERFECT_INFO_SUCCESS;
		} else {
			return C.reg.REG_ERROR;
		}
	}

	@Override
	@Transactional
	public String register(String telNum, double regLatitude,
			double regLongitude) {
		if (registerDao.queryByTel(telNum) == null) {
			User userReg = new User();
			String uuid = UUIDUtil.generateShortUuid();
			String userPwd = MD5.enc32Bit(uuid);
			userReg.setUserName(telNum);
			userReg.setUserPwd(userPwd);
			userReg.setUserId(uuid);
			userReg.setUserTelNum(telNum);
			userReg.setRegLatitude(regLatitude);
			userReg.setRegLongitude(regLongitude);
			userReg.setUserNickname("TN-"
					+ String.valueOf(new Date().getTime()).substring(3));
			if (registerDao.insert(userReg)) {
				triggerService.triggerEvent(this, telNum);
				String returns = null;
				try {
					Client client = new Client();
					returns = client.uc_user_register(telNum, userPwd,
							uuid.toLowerCase() + "@abc.com");
					if (returns == null || returns.isEmpty()) {
						throw new Exception("同步注册论坛用户方法执行失败，无返回值");
					} else if (Integer.parseInt(returns) < 0) {
						throw new Exception("同步注册论坛用户失败，错误码：" + returns);
					}
					logger.info("用户：[" + telNum + "]同步注册论坛成功");
					triggerService.triggerEvent(this, telNum);
					return C.reg.REG_SUCCESS;
				} catch (Exception e) {
					logger.error(e, e);
					return returns;
				}
			} else {
				return C.def.ERROR;
			}
		} else {
			return C.reg.USER_EXISTS;
		}
	}
}
