package edu.buu.childhood.log.dao;

import edu.buu.childhood.log.pojo.ADClickLog;
import edu.buu.childhood.log.pojo.ADCoveredLog;
import edu.buu.childhood.log.pojo.LoginLog;

public interface LogDao {
	public void insert(LoginLog loginLog);

	public void insert(ADCoveredLog adCoveredLog);

	public void insert(ADClickLog adClickLog);
}
