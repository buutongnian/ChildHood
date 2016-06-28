package edu.buu.childhood.game.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Page;
import edu.buu.childhood.game.pojo.GameContent;
import edu.buu.childhood.game.pojo.GameHead;

@Repository
public class GameDaoImpl implements GameDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	Logger logger=Logger.getLogger(getClass());

	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Page<GameHead> queryGameHeadInf(String where, int pageNum, int pageSize) {
		try{
				Session session=getSession();
				String hql="From GameHead where enable=:enable"+where;
				Query query=session.createQuery(hql);
				query.setCharacter("enable", C.def.ENABLED);
				List<GameHead> pageList=query.list();
				String countHql="select count(*) from GameHead where enable=:enable"+where;
				Query countQuery=session.createQuery(countHql);
				countQuery.setCharacter("enable", C.def.ENABLED);
				int recordsCount=(int) countQuery.uniqueResult();
				return new Page<GameHead>(recordsCount,pageNum,pageSize,pageList);
		}catch(Exception e){
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public GameContent queryGameContentById(int gameCode) {
		try{
			Session session=getSession();
			String hql="From GameContent where gameCode=:gameCode and enable=:enable";
			Query query=session.createQuery(hql);
			query.setCharacter("enable", C.def.ENABLED);
			return (GameContent) query.uniqueResult();
		}catch(Exception e){
			logger.error(e.getMessage());
			return null;
		}
	}

}
