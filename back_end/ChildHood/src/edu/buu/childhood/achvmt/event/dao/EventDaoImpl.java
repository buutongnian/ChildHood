package edu.buu.childhood.achvmt.event.dao;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.buu.childhood.achvmt.event.pojo.UserEventList;
import edu.buu.childhood.common.Page;

@Repository
public class EventDaoImpl implements EventDao {

	@Autowired
	private SessionFactory sessionFactory;

	Logger logger = Logger.getLogger(getClass());

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<UserEventList> queryUserFootPrint(String userName, int pageNum,
			int pageSize) {
		try {
			Session session = getSession();
			String subHql = "select distinct eventDate from UserEventList where userName=:userName order by eventDate desc";
			Query subQuery = session.createQuery(subHql);
			subQuery.setString("userName", userName);
			subQuery.setFirstResult((pageNum - 1) * pageSize);
			subQuery.setMaxResults(pageSize);
			List<Timestamp> subTableResult = subQuery.list();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyy-MM-dd");
			List<String> subList = new ArrayList<String>();
			for (Timestamp item : subTableResult) {
				subList.add(simpleDateFormat.format(item));
			}
			String hql = "From UserEventList where date_format(eventDate,'%Y-%m-%d') in (:subList) and userName=:userName";
			Query query = session.createQuery(hql);
			query.setString("userName", userName);
			query.setParameterList("subList", subList);
			List<UserEventList> pageList = query.list();
			String countHql = "select count(distinct eventDate) from UserEventList where userName=:userName";
			Query countQuery = session.createQuery(countHql);
			countQuery.setString("userName", userName);
			int recordsCount = ((Long) countQuery.uniqueResult()).intValue();
			return new Page<UserEventList>(recordsCount, pageNum, pageSize,
					pageList);
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

}
