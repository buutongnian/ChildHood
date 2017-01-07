package edu.buu.childhood.onekey.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.buu.childhood.common.C;
import edu.buu.childhood.onekey.pojo.GameStatus;
import edu.buu.childhood.onekey.pojo.JoinedStatus;

@Repository
public class OnkeyDaoImpl implements OnekeyDao {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public GameStatus getGameStatus(int gameId) {
		try {
			Session session = getSession();
			String hql = "From GameStatus where gameId=:gameId";
			Query query = session.createQuery(hql);
			query.setInteger("gameId", gameId);
			return (GameStatus) query.uniqueResult();
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getTimeOutGames(Date outTime) {
		try {
			Session session = getSession();
			String hql = "select gameId from GameStatus where startTime<:outTime and gameStatus=:gameStatus";
			Query query = session.createQuery(hql);
			query.setDate("outTime", outTime);
			query.setString("gameStatus", C.status.STARTED);
			return query.list();
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@Override
	public void insert(GameStatus gameStatus) {
		try {
			Session session = getSession();
			session.save(gameStatus);
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	@Override
	public void updateStartTime(int gameId, String gameStatus) {
		try {
			Session session = getSession();
			String hql = "update GameStatus set startTime=now(),gameStatus=:gameStatus where gameId=:gameId";
			Query query = session.createQuery(hql);
			query.setString("gameStatus", gameStatus);
			query.setInteger("gameId", gameId);
			query.executeUpdate();
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	@Override
	public void updateStopTime(int gameId, String gameStatus) {
		try {
			Session session = getSession();
			String hql = "update GameStatus set finishTime=now(),gameStatus=:gameStatus where gameId=:gameId";
			Query query = session.createQuery(hql);
			query.setString("gameStatus", gameStatus);
			query.setInteger("gameId", gameId);
			query.executeUpdate();
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	@Override
	public void updateInfo(int gameId, String set) {
		try {
			Session session = getSession();
			String hql = "update GameStatus set gameId=gameId" + set
					+ " where gameId=:gameId";
			Query query = session.createQuery(hql);
			query.setInteger("gameId", gameId);
			query.executeUpdate();
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	/*
	 * 2016/9/8 修改游戏状态
	 */
	@Override
	public void update(int gameId, String gameStatus) {
		try {
			Session session = getSession();
			String hql = "update GameStatus set gameStatus=:gameStatus where gameId=:gameId";
			Query query = session.createQuery(hql);
			query.setString("gameStatus", gameStatus);
			query.setInteger("gameId", gameId);
			query.executeUpdate();
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JoinedStatus> getJoinedStatus(String userName, String joinStatus) {
		try {
			Session session = getSession();
			String hql = "From JoinedStatus where userName=:userName and joinStatus=:joinStatus";
			Query query = session.createQuery(hql);
			query.setString("userName", userName);
			query.setString("joinStatus", joinStatus);
			return query.list();
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@Override
	public void insert(JoinedStatus joinedStatus) {
		try {
			Session session = getSession();
			session.save(joinedStatus);
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	@Override
	public void update(int gameId, String userName, char gameScore) {
		try {
			Session session = getSession();
			String hql = "update JoinedStatus set gameScore=:gameScore,isScored=:isScored where gameId=:gameId and userName=:userName";
			Query query = session.createQuery(hql);
			query.setCharacter("gameScore", gameScore);
			query.setCharacter("isScored", C.def.YES);
			query.setInteger("gameId", gameId);
			query.setString("userName", userName);
			query.executeUpdate();
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	@Override
	public void update(int gameId, String userName, String joinStatus) {
		try {
			Session session = getSession();
			String hql = "update JoinedStatus set joinStatus=:joinStatus where gameId=:gameId and userName=:userName";
			Query query = session.createQuery(hql);
			query.setString("joinStatus", joinStatus);
			query.setInteger("gameId", gameId);
			query.setString("userName", userName);
			query.executeUpdate();
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JoinedStatus> getJoinedUser(int gameId, String joinStatus) {
		try {
			Session session = getSession();
			String hql = "From JoinedStatus where gameId=:gameId and joinStatus=:joinStatus";
			Query query = session.createQuery(hql);
			query.setInteger("gameId", gameId);
			query.setString("joinStatus", joinStatus);
			return query.list();
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GameStatus> getGameStatus(String userName, List<String> status,
			boolean equalOrNot) {
		try {
			Session session = getSession();
			String hql = null;
			if (equalOrNot) {
				hql = "From GameStatus where gameFounder=:userName and gameStatus in (:status)";
			} else {
				hql = "From GameStatus where gameFounder=:userName and gameStatus not in (:status)";
			}
			Query query = session.createQuery(hql);
			query.setString("userName", userName);
			query.setParameterList("status", status);
			return query.list();
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GameStatus> getGameStatus(List<String> status,
			boolean equalOrNot) {
		try {
			Session session = getSession();
			String hql = null;
			if (equalOrNot) {
				hql = "From GameStatus where gameStatus in (:status)";
			} else {
				hql = "From GameStatus where gameStatus not in (:status)";
			}
			Query query = session.createQuery(hql);
			query.setParameterList("status", status);
			return query.list();
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@Override
	public String queryGameStatus(int gameId) {
		try {
			Session session = getSession();
			String hql = "select gameStatus from GameStatus where gameId=:gameId";
			Query query = session.createQuery(hql);
			query.setInteger("gameId", gameId);
			return (String) query.uniqueResult();
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JoinedStatus> getJoinedStatus(int gameId,
			List<String> status, boolean equalOrNot) {
		try {
			Session session = getSession();
			String hql = null;
			if (equalOrNot) {
				hql = "From JoinedStatus where gameId=:gameId and joinStatus in (:status)";
			} else {
				hql = "From JoinedStatus where gameId=:gameId and joinStatus not in (:status)";
			}
			Query query = session.createQuery(hql);
			query.setInteger("gameId", gameId);
			query.setParameterList("status", status);
			return query.list();
		} catch (Exception e) {
			logger.error(e, e);
			return null;
		}
	}
}
