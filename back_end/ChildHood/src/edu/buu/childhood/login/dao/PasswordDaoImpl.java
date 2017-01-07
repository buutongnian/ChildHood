package edu.buu.childhood.login.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.buu.childhood.login.pojo.VCode;

@Repository
public class PasswordDaoImpl implements PasswordDao {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public VCode queryByTel(String telnum) {
		try {
			Session session = getSession();
			String hql = "From VCode where telnum=:telnum order by vcodeId desc";
			Query query = session.createQuery(hql);
			query.setString("telnum", telnum);
			List<VCode> vcodeList = query.list();
			if (vcodeList.size() != 0) {
				return vcodeList.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@Override
	public void insert(VCode vcode) {
		try {
			Session session = getSession();
			session.save(vcode);
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	@Override
	public void update(VCode vcode) {
		try {
			Session session = getSession();
			session.update(vcode);
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	@Override
	public int update(String userName, String userPwd) {
		try {
			Session session = getSession();
			String hql = "update User set userPwd=:userPwd where userName=:userName";
			Query query = session.createQuery(hql);
			query.setString("userPwd", userPwd);
			query.setString("userName", userName);
			return query.executeUpdate();
		} catch (Exception e) {
			logger.error(e, e);
			return 0;
		}
	}
}
