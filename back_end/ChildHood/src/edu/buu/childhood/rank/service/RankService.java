package edu.buu.childhood.rank.service;

import java.util.List;

import edu.buu.childhood.common.Page;
//import edu.buu.childhood.my.pojo.User;
import edu.buu.childhood.rank.pojo.GameRank;
import edu.buu.childhood.rank.pojo.LikeDetail;
import edu.buu.childhood.rank.pojo.RankList;
//import edu.buu.childhood.status.pojo.MemberInfo;

public interface RankService {
	public List<GameRank> getGameRank(String userName);

	// public List<User> getUserRank(String userName);
//	public List<MemberInfo> getUserRank(String userName);
	public RankList getUserRank(String userName);
	

	public boolean likeUser(String userName, String likeUser, String access);

	public boolean likeUser(String userName, String likeUser, String access,
			int gameId);

	public Page<LikeDetail> getUserLikeDetailList(String userName, int pageNum);
}
