package edu.buu.childhood.version.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.buu.childhood.common.C;
import edu.buu.childhood.version.pojo.VersionBean;

@Repository
public class VersionDaoImpl implements VersionDao {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public VersionBean getLeastVersion(String system, char enable) {
		try {
			Session session = getSession();
			String hql = "From VersionBean where system=:system and enable=:enable order by versionId desc";
			Query query = session.createQuery(hql);
			query.setString("system", system);
			query.setCharacter("enable", enable);
			return (VersionBean) query.list().get(0);
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VersionBean> query() {
		try {
			Session session = getSession();
			String hql = "From VersionBean where enable=:enable";
			Query query = session.createQuery(hql);
			query.setCharacter("enable", C.def.ENABLED);
			return query.list();
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@Override
	public VersionBean query(int versionId) {
		try {
			Session session = getSession();
			String hql = "From VersionBean where versionId=:versionId";
			Query query = session.createQuery(hql);
			query.setInteger("versionId", versionId);
			return (VersionBean) query.uniqueResult();
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@Override
	public void insert(VersionBean versionBean) {
		try {
			Session session = getSession();
			session.save(versionBean);
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	@Override
	public void update(VersionBean versionBean) {
		try {
			Session session = getSession();
			session.update(versionBean);
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	@Override
	public void delete(int versionId) {
		try {
			Session session = getSession();
			String hql = "update VersionBean set enable=:enable where versionId=:versionId";
			Query query = session.createQuery(hql);
			query.setCharacter("enable", C.def.DISABLED);
			query.setInteger("versionId", versionId);
			query.executeUpdate();
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

}
