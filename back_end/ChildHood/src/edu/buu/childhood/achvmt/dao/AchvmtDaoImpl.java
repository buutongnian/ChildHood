package edu.buu.childhood.achvmt.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.buu.childhood.achvmt.pojo.UserHistoryGameList;
import edu.buu.childhood.achvmt.pojo.UserKPI;
import edu.buu.childhood.common.Page;

@Repository
public class AchvmtDaoImpl implements AchvmtDao {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public UserKPI queryUserKPI(String userName) {
		try {
			Session session = getSession();
			String hql = "From UserKPI where userName=:userName";
			Query query = session.createQuery(hql);
			query.setString("userName", userName);
			return (UserKPI) query.uniqueResult();
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<UserHistoryGameList> queryUserGameList(String userName,
			int pageNum, int pageSize) {
		try {
			Session session = getSession();
			String hql = "From UserHistoryGameList where userName=:userName";
			Query query = session.createQuery(hql);
			query.setString("userName", userName);
			query.setFirstResult((pageNum - 1) * pageSize);
			query.setMaxResults(pageSize);
			List<UserHistoryGameList> pageList = query.list();
			String countHql = "select count(*) from UserHistoryGameList where userName=:userName";
			Query countQuery = session.createQuery(countHql);
			countQuery.setString("userName", userName);
			int recordsCount = ((Long) countQuery.uniqueResult()).intValue();
			return new Page<UserHistoryGameList>(recordsCount, pageNum,
					pageSize, pageList);
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

}
