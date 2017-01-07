package edu.buu.childhood.rank.pojo;

import java.util.List;

public class RankList {
	private UserRank selfRank;
	private List<UserRank> rankList;

	public RankList() {
		super();
	}

	public RankList(UserRank selfRank, List<UserRank> rankList) {
		super();
		this.selfRank = selfRank;
		this.rankList = rankList;
	}

	public UserRank getSelfRank() {
		return selfRank;
	}

	public void setSelfRank(UserRank selfRank) {
		this.selfRank = selfRank;
	}

	public List<UserRank> getRankList() {
		return rankList;
	}

	public void setRankList(List<UserRank> rankList) {
		this.rankList = rankList;
	}
}
