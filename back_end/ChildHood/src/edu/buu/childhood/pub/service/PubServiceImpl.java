package edu.buu.childhood.pub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import edu.buu.childhood.log.service.LogService;
import edu.buu.childhood.pub.dao.PubDao;
import edu.buu.childhood.pub.pojo.ADInfo;

public class PubServiceImpl implements PubService {

	@Autowired
	private PubDao pubDao;
	@Autowired
	private LogService logService;

	@Override
	public List<ADInfo> getADList(String userName) {
		List<ADInfo> list = pubDao.queryTop5ByPriority();
		System.out.println(list);
		for (int i = 0; i < list.size(); i++) {
			ADInfo adInfo = list.get(i);
			adInfo.setAdInfo("");
			logService.writeADCoveredLog(userName, adInfo.getAdId());
		}
		return list;
	}

}
