package edu.buu.childhood.rank.service;

//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import edu.buu.childhood.achvmt.service.EventSupport;
import edu.buu.childhood.achvmt.service.TriggerService;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.Page;
import edu.buu.childhood.my.pojo.User;
import edu.buu.childhood.rank.dao.RankDao;
import edu.buu.childhood.rank.pojo.GameRank;
import edu.buu.childhood.rank.pojo.LikeDetail;
import edu.buu.childhood.rank.pojo.RankList;
import edu.buu.childhood.rank.pojo.StatusUserRankDetail;
//import edu.buu.childhood.status.pojo.MemberInfo;
import edu.buu.childhood.rank.pojo.UserRank;

/**
 * 2016/06/28
 * 
 * @author tt
 * @note 游戏排行以及孩子王排行业务操作
 * 
 *
 */

public class RankServiceImpl implements RankService, EventSupport {

	@Override
	public String moduleName() {
		return "rank";
	}

	@Autowired
	private RankDao rankDao;

	private TriggerService triggerService;

	public RankServiceImpl() {
	}

	public RankServiceImpl(TriggerService triggerService) {
		this.triggerService = triggerService;
		triggerService.registerModule(this);
	}

	/**
	 * 
	 * 2016/06/28
	 *
	 * @author tt
	 * @note 查询用户注册所在区域的游戏排行
	 * @param pageNum
	 *            当前页的页码
	 * 
	 */
	// @Override
	// @Transactional
	// public Page<GameRank> getGameRank(String userName, int pageNum) {
	// User userFromDB = rankDao.getUserInfo(userName);
	// if (userFromDB != null) {
	// Page<GameRank> gameRank = rankDao.getGameRank(
	// userFromDB.getBelongingArea(), pageNum);
	// return gameRank;
	// } else {
	// return null;
	// }
	// }

	@Override
	@Transactional
	public List<GameRank> getGameRank(String userName) {
		User userFromDB = rankDao.getUserInfo(userName);
		if (userFromDB != null) {
			List<GameRank> gameRank = rankDao.getGameRank(userFromDB
					.getBelongingArea());
			return gameRank;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * 2016/06/28
	 * 
	 * @author tt
	 * @note 查询用户注册所在地区孩子王排行榜
	 * 
	 *       2016/9/27
	 * 
	 * @modify 去除UserRank类，改为返回USER类。数据库中去除V_RANK_USER视图
	 * @by joe
	 */
	// @Override
	// @Transactional
	// public Page<User> getUserRank(String userName, int pageNum) {
	// User userFromDB = rankDao.getUserInfo(userName);
	// if (userFromDB != null) {
	// Page<User> user = rankDao.getUserRank(
	// userFromDB.getBelongingArea(), pageNum);
	// return user;
	// } else {
	// return null;
	// }
	// }

	// @Override
	// @Transactional
	// public List<User> getUserRank(String userName) {
	// User userFromDB = rankDao.getUserInfo(userName);
	// if (userFromDB != null) {
	// List<User> user = rankDao
	// .getUserRank(userFromDB.getBelongingArea());
	// return user;
	// } else {
	// return null;
	// }
	// }

	// @Override
	// @Transactional
	// public List<MemberInfo> getUserRank(String userName) {
	// User userFromDB = rankDao.getUserInfo(userName);
	// if (userFromDB != null) {
	// List<User> userList = rankDao.getUserRank(userFromDB
	// .getBelongingArea());
	// List<MemberInfo> memberInfoList = new ArrayList<MemberInfo>();
	// for (User user : userList) {
	// MemberInfo memberInfo = new MemberInfo();
	// memberInfo.setUserName(user.getUserName());
	// memberInfo.setUsernNickname(user.getUserNickname());
	// memberInfo.setUserHeadImage(user.getUserHeadImage());
	// List<StatusUserRankDetail> list = rankDao.queryTodayLikeDetail(
	// userName, user.getUserName(), C.def.ACCESS_RANKINGLIKE);
	// memberInfo.setCouldLike((list == null || list.size() == 0)
	// && (!user.getUserName().equals(userName)) ? true
	// : false);
	// memberInfoList.add(memberInfo);
	// }
	// return memberInfoList;
	// } else {
	// return null;
	// }
	// }

	@Override
	@Transactional
	public RankList getUserRank(String userName) {
		RankList rankList = rankDao.callUserRank(userName, C.def.TOP_N);
		List<UserRank> list = rankList.getRankList();
		List<UserRank> tempList = new ArrayList<UserRank>();
		for (UserRank userRank : list) {
			List<StatusUserRankDetail> detailList = rankDao
					.queryTodayLikeDetail(userName, userRank.getUserName(),
							C.def.ACCESS_RANKINGLIST);
			userRank.setCouldLike((detailList == null || detailList.size() == 0)
					&& (!userRank.getUserName().equals(userName)) ? true
					: false);
			tempList.add(userRank);
		}
		rankList.setRankList(tempList);
		return rankList;
	}

	/**
	 * 2016/9/28
	 * 
	 * @author joe
	 * 
	 *         给其他用户点赞，被点赞用户增加被点赞量，并记录点赞详情，然后触发事件系统
	 */
	@Override
	@Transactional
	public boolean likeUser(String userName, String likeUser, String access) {
		List<StatusUserRankDetail> list = rankDao.queryTodayLikeDetail(
				userName, likeUser, access);
		if (list == null || list.size() == 0) {
			rankDao.update(likeUser);
			rankDao.insert(new StatusUserRankDetail(userName, likeUser, access));
			triggerService.triggerEvent(this, likeUser);
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean likeUser(String userName, String likeUser, String access,
			int gameId) {
		List<StatusUserRankDetail> list = rankDao.queryTodayLikeDetail(
				userName, likeUser, access, gameId);
		if (C.def.ACCESS_GAMEOVER.equals(access)
				&& (list == null || list.size() == 0) && (userName != likeUser)) {
			rankDao.update(likeUser);
			rankDao.insert(new StatusUserRankDetail(userName, likeUser, access,
					gameId));
			triggerService.triggerEvent(this, likeUser);
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public Page<LikeDetail> getUserLikeDetailList(String userName, int pageNum) {
		return rankDao.queryLikeDetailList(userName, pageNum, C.def.PAGE_SIZE);
	}
}
