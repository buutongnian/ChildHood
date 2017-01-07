package edu.buu.childhood.feedback.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.buu.childhood.common.Page;
import edu.buu.childhood.feedback.pojo.Feedback;

@Repository
public class FeedbacnDaoImpl implements FeedbackDao {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<Feedback> queryAll(int pageNum, int pageSize) {
		try {
			Session session = getSession();
			String hql = "select feedbackId,userName,feedbackTime,feedbackModule,processible,resultTime,resultUser From Feedback";
			Query query = session.createQuery(hql);
			query.setFirstResult((pageNum - 1) * pageSize);
			query.setMaxResults(pageSize);
			List<Feedback> pageList = query.list();
			String countHql = "select count(*) from Feedback";
			Query countQuery = session.createQuery(countHql);
			int recordsCount = ((Long) countQuery.uniqueResult()).intValue();
			return new Page<Feedback>(recordsCount, pageNum, pageSize, pageList);
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<Feedback> queryByModule(String moduleName, int pageNum,
			int pageSize) {
		try {
			Session session = getSession();
			String hql = "select feedbackId,userName,feedbackTime,feedbackModule,processible,resultTime,resultUser From Feedback where feedbackModule=:feedbackModule";
			Query query = session.createQuery(hql);
			query.setString("feedbackModule", moduleName);
			query.setFirstResult((pageNum - 1) * pageSize);
			query.setMaxResults(pageSize);
			List<Feedback> pageList = query.list();
			String countHql = "select count(*) from Feedback where feedbackModule=:feedbackModule";
			Query countQuery = session.createQuery(countHql);
			countQuery.setString("feedbackModule", moduleName);
			int recordsCount = ((Long) countQuery.uniqueResult()).intValue();
			return new Page<Feedback>(recordsCount, pageNum, pageSize, pageList);
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@Override
	public Feedback queryUniqueById(int feedbackId) {
		try {
			Session session = getSession();
			String hql = "From Feedback where feedbackId=:feedbackId";
			Query query = session.createQuery(hql);
			query.setInteger("feedbackId", feedbackId);
			return (Feedback) query.uniqueResult();
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@Override
	public boolean insert(Feedback feedback) {
		try {
			Session session = getSession();
			session.save(feedback);
			return true;
		} catch (Exception e) {
			logger.error(e, e);
			return false;
		}
	}

	@Override
	public boolean update(Feedback feedback) {
		try {
			Session session = getSession();
			session.update(feedback);
			return true;
		} catch (Exception e) {
			logger.error(e, e);
			return false;
		}
	}

}
