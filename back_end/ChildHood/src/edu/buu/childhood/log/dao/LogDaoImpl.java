package edu.buu.childhood.log.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.buu.childhood.log.pojo.ADClickLog;
import edu.buu.childhood.log.pojo.ADCoveredLog;
import edu.buu.childhood.log.pojo.LoginLog;

@Repository
public class LogDaoImpl implements LogDao {

	@Autowired
	private SessionFactory sessionFactory;

	Logger logger = Logger.getLogger(getClass());

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void insert(LoginLog loginLog) {
		try {
			Session session = getSession();
			session.save(loginLog);
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	@Override
	public void insert(ADCoveredLog adCoveredLog) {
		try {
			Session session = getSession();
			session.save(adCoveredLog);
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	@Override
	public void insert(ADClickLog adClickLog) {
		try {
			Session session = getSession();
			session.save(adClickLog);
		} catch (Exception e) {
			logger.error(e, e);
		}
	}
}
