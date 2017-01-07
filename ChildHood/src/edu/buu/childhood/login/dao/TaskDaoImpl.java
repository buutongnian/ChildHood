package edu.buu.childhood.login.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.buu.childhood.login.pojo.User;

@Repository
public class TaskDaoImpl implements TaskDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> queryAllUsers() {
		Session session=getSession();
		String hql="From User";
		Query query=session.createQuery(hql);
		List<User> users=query.list();
		return users;
	}

	@Override
	public void update(String userName, String loginStatus) {
		Session session=getSession();
		String hql="update User set loginStatus=:loginStatus where userName=:userName";
		Query query=session.createQuery(hql);
		query.setString("loginStatus", loginStatus);
		query.setString("userName", userName);
		query.executeUpdate();
	}

}
