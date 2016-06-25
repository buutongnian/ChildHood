package edu.buu.childhood.login.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.buu.childhood.login.pojo.User;

/**
 * 2016/6/22
 * @author joe
 * @note 登录模块数据库操作层实现类
 */
@Repository
public class LoginDaoImpl implements LoginDao {
	
	private Logger logger=Logger.getLogger(this.getClass());
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	@Override
	public User queryByUserName(String userName) {
		try{
			Session session=getSession();
			String hql="FROM User where userName=:userName";
			Query query=session.createQuery(hql);
			query.setString("userName", userName);
			return (User)query.uniqueResult();
		}catch(Exception e){
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public void update(User user) {
		try{
			Session session=getSession();
			session.update(user);
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	}

	@Override
	public void update(String userName,double lastLatitude, double lastLongitude) {
		try{
			Session session=getSession();
			String hql="update UserInf set lastLatitude=:lastLatitude,lastLongitude=:lastLongitude where userName=:userName";
			Query query=session.createQuery(hql);
			query.setDouble("lastLatitude", lastLatitude);
			query.setDouble("lastLongitude", lastLongitude);
			query.executeUpdate();
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	}
	
}
