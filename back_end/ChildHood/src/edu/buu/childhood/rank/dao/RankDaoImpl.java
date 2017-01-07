package edu.buu.childhood.rank.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ParameterMode;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.result.Output;
import org.hibernate.result.ResultSetOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Page;
import edu.buu.childhood.my.pojo.User;
import edu.buu.childhood.rank.pojo.GameRank;
import edu.buu.childhood.rank.pojo.LikeDetail;
import edu.buu.childhood.rank.pojo.RankList;
import edu.buu.childhood.rank.pojo.StatusUserRank;
import edu.buu.childhood.rank.pojo.StatusUserRankDetail;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<StatusUserRankDetail> queryTodayLikeDetail(String userName,
			String likeUser, String access) {
		try {
			Session session = getSession();
			String hql = "From StatusUserRankDetail where DATE_FORMAT(likeTime,'%Y-%m%d')=DATE_FORMAT(now(),'%Y-%m%d') and userName=:userName and likeUser=:likeUser and access=:access";
			Query query = session.createQuery(hql);
			query.setString("userName", userName);
			query.setString("likeUser", likeUser);
			query.setString("access", access);
			return query.list();
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StatusUserRankDetail> queryTodayLikeDetail(String userName,
			String likeUser, String access, int gameId) {
		try {
			Session session = getSession();
			String hql = "From StatusUserRankDetail where userName=:userName and likeUser=:likeUser and access=:access and gameId=:gameId";
			Query query = session.createQuery(hql);
			query.setString("userName", userName);
			query.setString("likeUser", likeUser);
			query.setString("access", access);
			query.setInteger("gameId", gameId);
			return query.list();
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	/**
	 * 2016/09/10
	 * 
	 * @author Joe
	 * @note 更新点赞信息
	 */
	@Override
	public void update(String likeUser) {
		try {
			Session session = getSession();
			String hql = "update StatusUserRank set likeCount=likeCount + 1 where userName=:userName";
			Query query = session.createQuery(hql);
			query.setString("userName", likeUser);
			query.executeUpdate();
		} catch (Exception e) {
			logger.error(e, e);
		}

	}

	/**
	 * 2016/09/10
	 * 
	 * @author Joe
	 * @note 插入点赞信息明细
	 */
	@Override
	public void insert(StatusUserRankDetail statusUserRankDetail) {
		try {
			Session session = getSession();
			session.save(statusUserRankDetail);
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	/**
	 * 2016/06/28
	 * 
	 * @author tt
	 * @note 根据用户名查询用户信息
	 */
	@Override
	public User getUserInfo(String userName) {

		try {
			Session session = getSession();
			String hql = "FROM User where userName=:userName";
			Query query = session.createQuery(hql);
			query.setString("userName", userName);
			return (User) query.uniqueResult();
		} catch (Exception e) {
			logger.error(e, e);
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

	// @SuppressWarnings({ "unchecked", "rawtypes" })
	// @Override
	// public Page<GameRank> getGameRank(int belongingArea, int pageNum) {
	// int pageSize = C.def.PAGE_SIZE;
	// try {
	// Session session = getSession();
	// String hql =
	// "FROM GameRank where belongingArea=:belongingArea order by gameHeat desc";
	// Query query = session.createQuery(hql);
	// query.setInteger("belongingArea", belongingArea);
	// query.setFetchSize((pageNum - 1) * pageSize);
	// query.setMaxResults(pageSize);
	// List<?> list = query.list();
	// String hqlCount =
	// "select count(*) from GameRank where belongingArea=:belongingArea";
	// Query queryCount = session.createQuery(hqlCount);
	// queryCount.setInteger("belongingArea", belongingArea);
	// int countRecords = ((Long) queryCount.uniqueResult()).intValue();
	// Page page = new Page(countRecords, pageNum, pageSize, list);
	// return page;
	// } catch (Exception e) {
	// logger.error(e, e);
	// return null;
	// }
	//
	// }

	@SuppressWarnings("unchecked")
	@Override
	public List<GameRank> getGameRank(int belongingArea) {
		try {
			Session session = getSession();
			String hql = "FROM GameRank where belongingArea=:belongingArea order by gameHeat desc";
			Query query = session.createQuery(hql);
			query.setInteger("belongingArea", belongingArea);
			query.setFirstResult(0);
			query.setMaxResults(C.def.TOP_N);
			return query.list();
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	// @SuppressWarnings("unchecked")
	// @Override
	// public Page<User> getUserRank(int belongingArea, int pageNum) {
	// int pageSize = C.def.PAGE_SIZE;
	// try {
	// Session session = getSession();
	// String hql =
	// "From User where belongingArea=:belongingArea and userHeadImage != 'system' order by achievementPoint desc";
	// Query query = session.createQuery(hql);
	// query.setInteger("belongingArea", belongingArea);
	// query.setFirstResult((pageNum - 1) * pageSize);
	// query.setMaxResults(pageSize);
	// List<User> list = query.list();
	// for (User user : list) {
	// user.setUserPwd("");
	// }
	// String hqlCount =
	// "select count(*) from User where belongingArea=:belongingArea and userHeadImage != 'system'";
	// Query queryCount = session.createQuery(hqlCount);
	// queryCount.setInteger("belongingArea", belongingArea);
	// int countRecords = ((Long) queryCount.uniqueResult()).intValue();
	// return new Page<User>(countRecords, pageNum, pageSize, list);
	// } catch (Exception e) {
	// logger.error(e, e);
	// return null;
	// }
	// }

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserRank(int belongingArea) {
		try {
			Session session = getSession();
			String hql = "From User where belongingArea=:belongingArea and userHeadImage != 'system' order by achievementPoint desc";
			Query query = session.createQuery(hql);
			query.setInteger("belongingArea", belongingArea);
			query.setFirstResult(0);
			query.setMaxResults(C.def.TOP_N);
			List<User> list = query.list();
			List<User> modifiedList = new ArrayList<User>();
			for (User user : list) {
				user.setUserPwd("");
				modifiedList.add(user);
			}
			list = null;
			return modifiedList;
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@Override
	public StatusUserRank uniqueUserRank(String userName) {
		try {
			Session session = getSession();
			String hql = "From StatusUserRank where userName=:userName";
			Query query = session.createQuery(hql);
			query.setString("userName", userName);
			return (StatusUserRank) query.uniqueResult();
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<LikeDetail> queryLikeDetailList(String userName, int pageNum,
			int pageSize) {
		try {
			Session session = getSession();
			String hql = "From LikeDetail where likeUser = :likeUser order by likeTime desc";
			Query query = session.createQuery(hql);
			query.setString("likeUser", userName);
			query.setFirstResult((pageNum - 1) * pageSize);
			query.setMaxResults(pageSize);
			List<LikeDetail> list = query.list();
			String hqlCount = "select count(*) from LikeDetail where likeUser = :likeUser";
			Query queryCount = session.createQuery(hqlCount);
			queryCount.setString("likeUser", userName);
			int countRecords = ((Long) queryCount.uniqueResult()).intValue();
			return new Page<LikeDetail>(countRecords, pageNum, pageSize, list);
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public RankList callUserRank(String userName, int maxRow) {
		try {
			Session session = getSession();
			ProcedureCall procedureCall = session
					.createStoredProcedureCall("PRC_GET_USER_RANK");
			procedureCall.registerParameter("userName", String.class,
					ParameterMode.IN).bindValue(userName);
			procedureCall.registerParameter("disRange", Integer.class,
					ParameterMode.IN).bindValue(maxRow);
			Output output = procedureCall.getOutputs().getCurrent();
			List<Object[]> tempObj = ((ResultSetOutput) output).getResultList();
			UserRank selfRank = new UserRank(
					((Double) tempObj.get(0)[0]).intValue(),
					(String) tempObj.get(0)[1], (String) tempObj.get(0)[2],
					(String) tempObj.get(0)[3], (Integer) tempObj.get(0)[4],
					(Integer) tempObj.get(0)[5], false);
			List<UserRank> rankList = new ArrayList<UserRank>();
			if (procedureCall.getOutputs().goToNext()) {
				output = procedureCall.getOutputs().getCurrent();
				List<Object[]> tempList = ((ResultSetOutput) output)
						.getResultList();
				for (Object[] obj : tempList) {
					rankList.add(new UserRank(((Double) obj[0]).intValue(),
							(String) obj[1], (String) obj[2], (String) obj[3],
							(Integer) obj[4], (Integer) obj[5]));
				}
			}
			return new RankList(selfRank, rankList);
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}
}
