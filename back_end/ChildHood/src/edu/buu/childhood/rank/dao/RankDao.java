package edu.buu.childhood.rank.dao;

import java.util.List;

import edu.buu.childhood.common.Page;
import edu.buu.childhood.my.pojo.User;
import edu.buu.childhood.rank.pojo.GameRank;
import edu.buu.childhood.rank.pojo.LikeDetail;
import edu.buu.childhood.rank.pojo.RankList;
import edu.buu.childhood.rank.pojo.StatusUserRank;
import edu.buu.childhood.rank.pojo.StatusUserRankDetail;

public interface RankDao {
	public List<StatusUserRankDetail> queryTodayLikeDetail(String userName,
			String likeUser, String access);

	public List<StatusUserRankDetail> queryTodayLikeDetail(String userName,
			String likeUser, String access, int gameId);

	public void update(String userName);

	public void insert(StatusUserRankDetail statusUserRankDetail);

	public User getUserInfo(String likeUser);

	// public Page<GameRank> getGameRank(int belongingArea,int pageNum);
	public List<GameRank> getGameRank(int belongingArea);

	// public Page<User> getUserRank(int belongingArea,int pageNum);
	public List<User> getUserRank(int belongingArea);

	public RankList callUserRank(String userName, int maxRow);

	public StatusUserRank uniqueUserRank(String userName);

	public Page<LikeDetail> queryLikeDetailList(String userName, int pageNum,
			int pageSize);
}
