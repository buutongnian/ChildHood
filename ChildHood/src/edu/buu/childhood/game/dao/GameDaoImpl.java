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
/**
 * 2016/6/28
 * 游戏规则请求数据库操作接口
 * @author joe
 *
 */
@Repository
public class GameDaoImpl implements GameDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	Logger logger=Logger.getLogger(getClass());

	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * 分页查询游戏头信息
	 * @param where 查询筛选条件子句，由Service层生成并传入，作为筛选条件
	 * @param pageNum 请求页码，分页使用
	 * @param pageSize 分页大小，由C.java中系统常量定义
	 * @return 含有分页信息的游戏头信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Page<GameHead> queryGameHeadInf(String where, int pageNum, int pageSize) {
		try{
				Session session=getSession();
				String hql="From GameHead where enable=:enable"+where;
				Query query=session.createQuery(hql);
				query.setCharacter("enable", C.def.ENABLED);
				query.setFirstResult((pageNum-1)*pageSize);
				query.setMaxResults(pageSize);
				List<GameHead> pageList=query.list();
				String countHql="select count(*) from GameHead where enable=:enable"+where;
				Query countQuery=session.createQuery(countHql);
				countQuery.setCharacter("enable", C.def.ENABLED);
				int recordsCount=((Long) countQuery.uniqueResult()).intValue();
				return new Page<GameHead>(recordsCount,pageNum,pageSize,pageList);
		}catch(Exception e){
			logger.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 查询游戏详细内容信息
	 * @param gameCode 游戏编号
	 * @return 游戏详细内容信息
	 */
	@Override
	public GameContent queryGameContentById(int gameCode) {
		try{
			Session session=getSession();
			String hql="From GameContent where gameCode=:gameCode and enable=:enable";
			Query query=session.createQuery(hql);
			query.setInteger("gameCode", gameCode);
			query.setCharacter("enable", C.def.ENABLED);
			return (GameContent) query.uniqueResult();
		}catch(Exception e){
			logger.error(e.getMessage());
			return null;
		}
	}

}
