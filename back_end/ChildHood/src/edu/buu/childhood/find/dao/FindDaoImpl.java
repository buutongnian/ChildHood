package edu.buu.childhood.find.dao;

import java.util.List;
import javax.persistence.ParameterMode;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.ProcedureCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import edu.buu.childhood.common.Page;
import edu.buu.childhood.find.pojo.FindReg;
import edu.buu.childhood.find.pojo.FindResult;

@Repository
public class FindDaoImpl implements FindDao {

	@Autowired
	private SessionFactory sessionFactory;

	Logger logger = Logger.getLogger(getClass());

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void computePartener(String userName, int disRange, int ageRange) {
		try {
			Session session = getSession();
			ProcedureCall procedureCall = session
					.createStoredProcedureCall("COMPUTE_YOUNG_PARTNER");
			procedureCall.registerParameter("userName", String.class,
					ParameterMode.IN).bindValue(userName);
			procedureCall.registerParameter("disRange", Integer.class,
					ParameterMode.IN).bindValue(disRange);
			procedureCall.registerParameter("ageRange", Integer.class,
					ParameterMode.IN).bindValue(ageRange);
			procedureCall.getOutputs().getCurrent();
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> query() {
		Session session = getSession();
		String hql = "select userName From FindReg";
		Query query = session.createQuery(hql);
		return query.list();
	}

	@Override
	public FindReg query(String userName) {
		try {
			Session session = getSession();
			String hql = "From FindReg where userName=:userName";
			Query query = session.createQuery(hql);
			query.setString("userName", userName);
			return (FindReg) query.uniqueResult();
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@Override
	public void insert(FindReg findReg) {
		try {
			Session session = getSession();
			session.save(findReg);
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	@Override
	public void update(FindReg findReg) {
		try {
			Session session = getSession();
			session.update(findReg);
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	@Override
	public void update(String userName, char enable) {
		try {
			Session session = getSession();
			String hql = "update FindReg set enable=:enable where userName=:userName";
			Query query = session.createQuery(hql);
			query.setCharacter("enable", enable);
			query.setString("userName", userName);
			query.executeUpdate();
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<FindResult> queryResult(String userName, int pageNum,
			int pageSize) {
		try {
			Session session = getSession();
			String hql = "From FindResult where userName=:userName";
			System.out.println("FindDaoImpl---userName:" + userName);
			System.out.println("FindDaoImpl---pageNum:" + pageNum);
			Query query = session.createQuery(hql);
			query.setString("userName", userName);
			query.setFirstResult((pageNum - 1) * pageSize);
			query.setMaxResults(pageSize);
			List<FindResult> pageList = query.list();
			String countHql = "select count(*) from FindResult where userName=:userName";
			Query countQuery = session.createQuery(countHql);
			countQuery.setString("userName", userName);
			System.out.println(pageList);
			int recordsCount = ((Long) countQuery.uniqueResult()).intValue();
			return new Page<FindResult>(recordsCount, pageNum, pageSize,
					pageList);
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

}
