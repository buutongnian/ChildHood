package edu.buu.childhood.find.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import edu.buu.childhood.achvmt.service.EventSupport;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Page;
import edu.buu.childhood.find.dao.FindDao;
import edu.buu.childhood.find.pojo.FindReg;
import edu.buu.childhood.find.pojo.FindResult;

public class FindServiceImpl implements FindService, EventSupport {
	@Override
	public String moduleName() {
		return "find";
	}

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private FindDao findDao;

	@Override
	@Transactional
	public FindReg getFindReg(String userName) {
		return findDao.query(userName);
	}

	@Override
	@Transactional
	public void insertFindReg(String userName, int age, char sex,
			double youngLatitude, double youngLongitude, String youngNickname,
			String youngRoad, String message) {
		FindReg findReg = new FindReg();
		findReg.setUserName(userName);
		findReg.setAge(age);
		findReg.setSex(sex);
		findReg.setYoungLatitude(youngLatitude);
		findReg.setYoungLongitude(youngLongitude);
		findReg.setYoungNickname(youngNickname);
		findReg.setYoungRoad(youngRoad);
		findReg.setMessage(message);
		findReg.setEnable(C.def.ENABLED);
		findDao.insert(findReg);
	}

	@Override
	@Transactional
	public void updateFindReg(String userName, int age, char sex,
			double youngLatitude, double youngLongitude, String youngNickname,
			String youngRoad, String message) {
		FindReg oldReg = getFindReg(userName);
		if (oldReg != null) {
			FindReg findReg = new FindReg();
			findReg.setUserName(userName);
			findReg.setAge(age);
			findReg.setSex(sex);
			findReg.setYoungLatitude(youngLatitude);
			findReg.setYoungLongitude(youngLongitude);
			findReg.setYoungNickname(youngNickname);
			findReg.setYoungRoad(youngRoad);
			findReg.setMessage(message);
			findReg.setEnable(C.def.ENABLED);
			findDao.update(findReg);
			logger.info("用户[" + userName + "]将寻找童年小伙伴登记信息从{"
					+ oldReg.toString() + "}更改为{" + findReg.toString() + "}");
		}
	}

	@Override
	@Transactional
	public void deleteFindReg(String userName) {
		findDao.update(userName, C.def.DISABLED);
	}

	@Override
	@Transactional
	public Page<FindResult> getFindResult(String userName, int pageNum) {
		System.out.println("FindServiceImpl---userName:" + userName);
		findDao.computePartener(userName, C.def.DIS_RANGE, C.def.AGE_RANGE);
		return findDao.queryResult(userName, pageNum, C.def.PAGE_SIZE);
	}

}
