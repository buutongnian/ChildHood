package edu.buu.childhood.my.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import edu.buu.childhood.my.pojo.ChildInf;
import edu.buu.childhood.my.pojo.User;

@Repository
public class MyDaoImpl implements MyDao {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public User getUserInf(String userName) {
		try {
			Session session = getSession();
			String hql = "From User where userName=:userName";
			Query query = session.createQuery(hql);
			query.setString("userName", userName);
			return (User) query.uniqueResult();
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@Override
	public void updateUserInfo(String userName, String set) {
		try {
			Session session = getSession();
			String hql = "Update User set userName=userName" + set
					+ " where userName=:userName";
			Query query = session.createQuery(hql);
			query.setString("userName", userName);
			query.executeUpdate();
		} catch (Exception e) {
			logger.error(e, e);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChildInf> getChildInf(String userName) {
		try {
			Session session = getSession();
			String hql = "From ChildInf where userName=:userName";
			Query query = session.createQuery(hql);
			query.setString("userName", userName);
			return query.list();
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@Override
	public void updateChildInfo(int childInfId, String set) {
		try {
			Session session = getSession();
			String hql = "Update ChildInf set childInf=childInf" + set
					+ "where childInf=:childInf";
			Query query = session.createQuery(hql);
			query.setInteger("childInf", childInfId);
			query.executeUpdate();
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	@Override
	public String getHeadImage(String userName) {
		try {
			Session session = getSession();
			String hql = "select userHeadImage from User where userName=:userName";
			Query query = session.createQuery(hql);
			query.setString("userName", userName);
			return (String) query.uniqueResult();
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@Override
	public void updateHeadImage(String userName, String userHeadImage) {
		try {
			Session session = getSession();
			String hql = "update User set userHeadImage=:userHeadImage where userName=:userName";
			Query query = session.createQuery(hql);
			query.setString("userHeadImage", userHeadImage);
			query.setString("userName", userName);
			query.executeUpdate();
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	@Override
	public void insert(ChildInf childInf) {
		try {
			Session session = getSession();
			session.save(childInf);
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

}
