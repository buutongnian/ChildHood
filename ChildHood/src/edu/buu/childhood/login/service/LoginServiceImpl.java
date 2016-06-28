package edu.buu.childhood.login.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import edu.buu.childhood.common.C;
import edu.buu.childhood.login.dao.LoginDao;
import edu.buu.childhood.login.pojo.User;
import edu.buu.childhood.util.MD5;

/**
 * 2016/6/22
 * @author joe
 * @note 登录模块业务逻辑操作类
 */
public class LoginServiceImpl implements LoginService {
	
	private Logger logger=Logger.getLogger(this.getClass());
	
	@Autowired
	private LoginDao loginDao;

	/**
	 * 2016/6/22
	 * 判断登录是否合法，检测用户是否存在、用户名是否正确
	 * 密码采用MD5小写32位加密方法
	 * @author joe
	 * @param userName 用户名
	 * @param password 密码
	 * @param userFromDB 通过用户名从数据库中取出的用户登录信息对象
	 * @return 返回登录状态编码，对应C.java常量文件中定义
	 */
	@Override
	@Transactional
	public String loginCheck(String userName,String userPwd) {
		User userFromDB=loginDao.queryByUserName(userName);
		if(userFromDB!=null){
			if(userFromDB.getUserPwd().
					trim().
					equals(MD5.
							enc32Bit(userPwd).
							trim())){
				// 返回登录成功信息,供Action判断转发信息（Web端或App端分别处理）
				return C.LoginStatus.LOGIN_SUCCESS;
			}else{
				// 返回密码错误信息,供Action判断转发信息（Web端或App端分别处理）
				return C.LoginStatus.PASSWORD_INCORRECT;
			}
		}else{
			// 返回用户不存在信息,供Action判断转发信息（Web端或App端分别处理）
			return C.LoginStatus.USER_NOT_EXIST;
		}
	}

	/**
	 * 2016/6/22
	 * 更改登录状态（主要供App端使用）
	 * @author joe
	 * @param userName 用户名
	 * @param loginStatus 登录状态编码，传入时使用C.java中定义的常量
	 * @return 返回是否修改成功（true或false）
	 */
	@Override
	@Transactional
	public boolean changeLoginStatus(String userName,String loginStatus) {
		try{
			User user=new User();
			user.setUserName(userName);
			user.setLoginStatus(loginStatus);
			loginDao.update(user);
			//记录登录状态修改日志
			logger.info(userName+"修改登录状态为："+loginStatus);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	/**
	 * 2016/6/24
	 * 更改登录经纬度信息（主要供App端使用）
	 * @author joe
	 * @param userName 用户名
	 * @param lastLatitude 登录纬度
	 * @param lastLongitude 登录经度
	 * @return 返回是否修改成功（true或false）
	 */
	@Override
	@Transactional
	public boolean updateLoginInf(String userName,double lastLatitude,double lastLongitude){
		try{
			loginDao.update(userName, lastLatitude, lastLongitude);
			logger.info(userName+"在纬度："+lastLatitude+" 经度："+lastLongitude+"登录");
			return true;
		}catch(Exception e){
			return false;
		}
	}

}
