package edu.buu.childhood.log.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import edu.buu.childhood.log.dao.LogDao;
import edu.buu.childhood.log.pojo.ADClickLog;
import edu.buu.childhood.log.pojo.ADCoveredLog;
import edu.buu.childhood.log.pojo.LoginLog;

public class LogServiceImpl implements LogService {

	@Autowired
	private LogDao logDao;

	@Override
	@Transactional
	public void writeLoginLog(String userName, double loginLatitude,
			double loginLongitude, String loginIp) {
		LoginLog loginLog = new LoginLog();
		loginLog.setUserName(userName);
		loginLog.setLoginLatitude(loginLatitude);
		loginLog.setLoginLongitude(loginLongitude);
		loginLog.setLoginIp(loginIp);
		logDao.insert(loginLog);
	}

	@Override
	@Transactional
	public void writeADCoveredLog(String userName, int adId) {
		ADCoveredLog adCoveredLog = new ADCoveredLog();
		adCoveredLog.setAdId(adId);
		adCoveredLog.setUserName(userName);
		logDao.insert(adCoveredLog);
	}

	@Override
	@Transactional
	public void writeADClickLog(String userName, int adId) {
		ADClickLog adClickLog = new ADClickLog();
		adClickLog.setAdId(adId);
		adClickLog.setUserName(userName);
		logDao.insert(adClickLog);
	}
}
