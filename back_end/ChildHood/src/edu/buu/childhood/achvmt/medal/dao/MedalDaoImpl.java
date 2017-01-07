package edu.buu.childhood.achvmt.medal.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.buu.childhood.achvmt.medal.pojo.UserMedalList;

@Repository
public class MedalDaoImpl implements MedalDao {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserMedalList> queryUserMedalList(String userName) {
		try {
			Session session = getSession();
			String hql = "From UserMedalList where userName=:userName";
			Query query = session.createQuery(hql);
			query.setString("userName", userName);
			return query.list();
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}
}
