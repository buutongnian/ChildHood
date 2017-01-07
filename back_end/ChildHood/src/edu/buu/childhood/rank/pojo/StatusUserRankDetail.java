package edu.buu.childhood.rank.pojo;

import java.util.Date;

/**
 * 2016/9/10
 * 
 * @Author Joe
 * @note 映射T_STATUS_USER_RANK_DETAIL表
 */
public class StatusUserRankDetail {
	private int detailId;
	private String userName;
	private String likeUser;
	private Date likeTime;
	private String access;
	private int gameId;

	public StatusUserRankDetail() {
		super();
	}

	public StatusUserRankDetail(String userName, String likeUser, String access) {
		this.userName = userName;
		this.likeUser = likeUser;
		this.access = access;
	}

	public StatusUserRankDetail(String userName, String likeUser,
			String access, int gameId) {
		this.userName = userName;
		this.likeUser = likeUser;
		this.access = access;
		this.gameId = gameId;
	}

	public int getDetailId() {
		return detailId;
	}

	public void setDetailId(int detailId) {
		this.detailId = detailId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLikeUser() {
		return likeUser;
	}

	public void setLikeUser(String likeUser) {
		this.likeUser = likeUser;
	}

	public Date getLikeTime() {
		return likeTime;
	}

	public void setLikeTime(Date likeTime) {
		this.likeTime = likeTime;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

}
