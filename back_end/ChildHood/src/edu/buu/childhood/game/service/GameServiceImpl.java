package edu.buu.childhood.game.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import edu.buu.childhood.algorithms.RLS;
import edu.buu.childhood.algorithms.UCVE;
import edu.buu.childhood.algorithms.VectorCanNotMultiplyException;
import edu.buu.childhood.algorithms.VectorUtil;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Page;
import edu.buu.childhood.game.dao.GameDao;
import edu.buu.childhood.game.pojo.GCVEntry;
import edu.buu.childhood.game.pojo.GameContent;
import edu.buu.childhood.game.pojo.GameHead;
import edu.buu.childhood.game.pojo.UCVEntry;

/**
 * 2016/6/28 游戏规则获取服务实现类
 * 
 * @author joe
 */
public class GameServiceImpl implements GameService {

	private static Map<Integer, GameHead> gameCache = new HashMap<Integer, GameHead>();

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private GameDao gameDao;

	@Override
	public GameHead getGameHead(int gameCode) {
		return gameDao.queryGameHeadInf(gameCode);
	}

	/**
	 * 获取游戏简介，用于App端游戏列表显示
	 * 
	 * @param gameArea
	 *            游戏所属大区，查询筛选条件（可选）
	 * @param ageCode
	 *            游戏适合年龄段，查询筛选条件（可选）
	 * @param memNumCode
	 *            游戏适合人数段，查询筛选条件（可选）
	 * @param pageNum
	 *            请求页码，用于分页
	 * @return 当前页信息+游戏头信息
	 */
	@Override
	@Transactional
	public Page<GameHead> getGameHeadPage(String userName, int gameArea,
			int ageCode, int memNumCode, int pageNum) {
		StringBuffer where = new StringBuffer("");
		if (gameArea != 0) {
			where.append(" and gameArea=" + gameArea);
		}
		if (ageCode != 0) {
			where.append(" and ageCode=" + ageCode);
		}
		if (memNumCode != 0) {
			where.append(" and memNumCode=" + memNumCode);
		}
		if (where.toString().equals("") && !C.def.ANONYMOUS.equals(userName)) {
			String UCVjson = gameDao.queryUserUCV(userName).getUCVjson();
			List<GCVEntry> GCVList = gameDao.queryGameGCV();
			RLS RLSinstance = new RLS(GCVList);
			List<GameHead> gameList = new ArrayList<GameHead>();
			int recordCount = 0;
			try {
				List<Integer> gameCodeList = RLSinstance.getGameList(UCVjson);
				recordCount = gameCodeList.size();
				if ((pageNum - 1) * C.def.PAGE_SIZE > gameCodeList.size()) {
					return new Page<GameHead>(recordCount, pageNum,
							C.def.PAGE_SIZE, null);
				}
				for (int i = (pageNum - 1) * C.def.PAGE_SIZE; i < pageNum
						* C.def.PAGE_SIZE; i++) {
					if (i >= gameCodeList.size()) {
						break;
					}
					if (gameCache.containsKey(gameCodeList.get(i))) {
						gameList.add(gameCache.get(gameCodeList.get(i)));
					} else {
						GameHead gameHead = gameDao
								.queryGameHeadInf(gameCodeList.get(i));
						gameCache.put(gameCodeList.get(i), gameHead);
						gameList.add(gameHead);
					}
				}
			} catch (VectorCanNotMultiplyException e) {
				logger.error(e, e);
				for (Map.Entry<Integer, GameHead> entry : gameCache.entrySet()) {
					if (gameList.size() >= C.def.PAGE_SIZE) {
						break;
					}
					gameList.add(entry.getValue());
				}
			}
			return new Page<GameHead>(recordCount, pageNum, C.def.PAGE_SIZE,
					gameList);
		} else {
			return gameDao.queryGameHeadInf(where.toString(), pageNum,
					C.def.PAGE_SIZE);
		}
	}

	/**
	 * @param gameCode
	 *            游戏编码，在游戏详情页面通过游戏编码请求游戏详细内容
	 * @return 游戏详细内容
	 */
	@Override
	@Transactional
	public GameContent getGameConntent(String userName, int gameCode) {
		if (!C.def.ANONYMOUS.equals(userName)) {
			logger.info("游戏编码：[" + gameCode + "]被访问详情");
			GCVEntry GCVinstance = gameDao.queryGameGCV(gameCode);
			UCVEntry UCVinstance = gameDao.queryUserUCV(userName);
			UCVE UCVEinstance = new UCVE();
			UCVEinstance.setRate(0.1f);
			String newUCVjson = null;
			try {
				newUCVjson = VectorUtil.parseVectorToJson(UCVEinstance
						.getNextGeneration(GCVinstance.getGCVjson(),
								UCVinstance.getUCVjson()));
			} catch (VectorCanNotMultiplyException e) {
				logger.error(e, e);
				e.printStackTrace();
			}
			UCVEntry newUCVinstance = new UCVEntry();
			newUCVinstance.setUserName(userName);
			newUCVinstance.setUCVjson(newUCVjson);
			gameDao.update(newUCVinstance);
		}
		gameDao.updateWatchTimes(gameCode);
		return gameDao.queryGameContentById(gameCode);
	}
}
