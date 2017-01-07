package edu.buu.childhood.pub.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.buu.childhood.common.C;
import edu.buu.childhood.pub.pojo.ADInfo;

@Repository
public class PubDaoImpl implements PubDao {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void insert(ADInfo adInfo) {
		try {
			Session session = getSession();
			session.save(adInfo);
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ADInfo> queryAll() {
		try {
			Session session = getSession();
			String hql = "From ADInfo";
			Query query = session.createQuery(hql);
			return query.list();
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ADInfo> queryAllByEnabled(char enabled) {
		try {
			Session session = getSession();
			String hql = "From ADInfo where adEnabled=:adEnabled";
			Query query = session.createQuery(hql);
			query.setCharacter("adEnabled", enabled);
			return query.list();
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ADInfo> queryTop5ByPriority() {
		try {
			Session session = getSession();
			String hql = "From ADInfo where adEnabled=:adEnabled order by adPriority asc";
			Query query = session.createQuery(hql);
			query.setCharacter("adEnabled", C.def.ENABLED);
			query.setFirstResult(0);
			query.setMaxResults(5);
			return query.list();
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ADInfo> queryByPriority(int priority) {
		try {
			Session session = getSession();
			String hql = "From ADInfo where adPriority=:adPriority";
			Query query = session.createQuery(hql);
			query.setInteger("adPriority", priority);
			return query.list();
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@Override
	public boolean update(ADInfo adInfo) {
		try {
			Session session = getSession();
			session.update(adInfo);
			return true;
		} catch (Exception e) {
			logger.error(e, e);
			return false;
		}
	}

}
