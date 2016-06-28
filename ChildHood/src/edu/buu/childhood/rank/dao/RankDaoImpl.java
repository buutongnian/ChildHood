package edu.buu.childhood.rank.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Page;
import edu.buu.childhood.my.pojo.UserRegInf;
import edu.buu.childhood.rank.pojo.GameRank;
import edu.buu.childhood.rank.pojo.UserRank;

/**
 * 
 * 2016/06/28
 * 
 * @author tt
 * @note 排行模块数据库操作类
 */
@Repository
public class RankDaoImpl implements RankDao {
	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 2016/06/28
	 * 
	 * @author tt
	 * @note 根据用户名查询用户信息
	 */
	@Override
	public UserRegInf getUserInfo(String userName) {

		try {
			Session session = getSession();
			String hql = "FROM UserRegInf where userName=:userName";
			Query query = session.createQuery(hql);
			query.setString("userName", userName);
			return (UserRegInf) query.uniqueResult();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 2016/06/28
	 * 
	 * @author tt
	 * @note 根据所属区域查询该区域的游戏热度（游戏排名）
	 * @param pageNum
	 *            当前页编号
	 * @param belongingArea
	 *            当前游戏的大区
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page<GameRank> getGameRank(int belongingArea, int pageNum) {
		int pageSize = C.def.PAGE_SIZE;
		try {
			Session session = getSession();
			String hql = "FROM GameRank where belongingArea=:belongingArea order by gameHeat desc";
			Query query = session.createQuery(hql);
			query.setInteger("belongingArea", belongingArea);
			query.setFetchSize((pageNum - 1) * pageSize);
			query.setMaxResults(pageSize);
			List<?> list = query.list();
			String hqlCount = "select count(*) from GameRank where belongingArea=:belongingArea";
			Query queryCount = session.createQuery(hqlCount);
			queryCount.setSerializable("belongingArea", belongingArea);
			int countRecords = ((Long) queryCount.iterate().next()).intValue();
			Page page = new Page(countRecords, pageNum, pageSize, list);
			return page;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Page<UserRank> getUserRank(int belongingArea, int pageNum) {
		int pageSize = C.def.PAGE_SIZE;
		try {
			Session session = getSession();
			String hql = "From UserRank where belongingArea=:belongingArea order by likeCount desc";
			Query query=session.createQuery(hql);
			query.setInteger("belongingArea", belongingArea);
			query.setFetchSize((pageNum - 1) * pageSize);
			query.setMaxResults(pageSize);
			List<?> list = query.list();
			String hqlCount = "select count(*) from UserRank where belongingArea=:belongingArea";
			Query queryCount = session.createQuery(hqlCount);
			queryCount.setSerializable("belongingArea", belongingArea);
			int countRecords = ((Long) queryCount.iterate().next()).intValue();
			Page page = new Page(countRecords, pageNum, pageSize, list);
			return page;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;

		}

		
	}

}
