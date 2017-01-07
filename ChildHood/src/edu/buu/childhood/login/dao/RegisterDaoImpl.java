package edu.buu.childhood.login.dao;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import edu.buu.childhood.login.pojo.User;
import edu.buu.childhood.my.pojo.ChildInf;
import edu.buu.childhood.my.pojo.ParentInf;
import edu.buu.childhood.my.pojo.UserInf;
import edu.buu.childhood.my.pojo.UserRegInf;

public class RegisterDaoImpl implements RegisterDao {

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
	public void insert(UserRegInf userRegInf,ParentInf parentInf,UserInf userInf,ChildInf childInf) {
		try{
			Session session=getSession();
			session.save(userRegInf);
			session.save(parentInf);
			session.save(userInf);
			session.save(childInf);
//			String hql="insert into UserRegInf(userName,regLatitude,regLongitude,liveCommunity)"
//					+ " values(:userName,:regLatitude,:regLongitude,:liveCommunity)";
//			Query query=session.createQuery(hql);
//			/**
//			 * 注册用户时先添加T_SYS_USER_REG中的如下几个字段
//			 */
//			query.setString("userName", userRegInf.getUserName());
//			query.setDouble("regLatitude", userRegInf.getRegLatitude());
//			query.setDouble("regLongitude", userRegInf.getRegLongitude());
//			query.setInteger("liveCommunity", userRegInf.getLiveCommunity());
//			query.
			/*
			 *  private String userName;
				private Date regDate;
				private double regLatitude;
				private double regLongitude;
				private int belongingArea;
				private int liveCommunity;
			*/
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	}

}
