package edu.buu.childhood.login.dao;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.buu.childhood.my.pojo.User;

@Repository
public class RegisterDaoImpl implements RegisterDao {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public User queryByUserName(String userName) {
		try {
			Session session = getSession();
			String hql = "FROM User where userName=:userName";
			Query query = session.createQuery(hql);
			query.setString("userName", userName);
			return (User) query.uniqueResult();
		} catch (Exception e) {
			logger.error(e,e);
			return null;
		}
	}

	@Override
	public User queryByTel(String tel) {
		try {
			Session session = getSession();
			String hql = "FROM User where userTelNum=:tel";
			Query query = session.createQuery(hql);
			query.setString("tel", tel);
			return (User) query.uniqueResult();
		} catch (Exception e) {
			logger.error(e,e);
			return null;
		}
	}

	@Override
	public boolean insert(User userReg) {
		try {
			Session session = getSession();
			session.save(userReg);
			return true;
		} catch (Exception e) {
			logger.error(e, e);
			return false;
		}
	}

	@Override
	public void update(String userName, String userPwd, String userNickname,
			int belongingProvince, int belongingCity, int belongingDistrict,
			String community, String email) {
		Session session = getSession();
		try {
			String hql = "update User set userPwd=:userPwd,"
					+ "userNickname=:userNickname,belongingProvince=:belongingProvince,"
					+ "belongingCity=:belongingCity,belongingDistrict=:belongingDistrict,"
					+ "community=:community,email=:email where userName=:userName";
			Query query = session.createQuery(hql);
			query.setString("userName", userName);
			query.setString("userPwd", userPwd);
			query.setString("userNickname", userNickname);
			query.setInteger("belongingProvince", belongingProvince);
			query.setInteger("belongingCity", belongingCity);
			query.setInteger("belongingDistrict", belongingDistrict);
			query.setString("community", community);
			query.setString("email", email);
			query.executeUpdate();
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

}
