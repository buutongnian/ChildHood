package edu.buu.childhood.rank.pojo;

/**
 * 2016/9/10
 * 
 * @Author Joe
 * @note 映射T_STATUS_USER_RANK表
 */
public class StatusUserRank {
	private String userName;
	private int likeCount;

	public StatusUserRank() {
	}

	public StatusUserRank(String userName, int likeCount) {
		this.userName = userName;
		this.likeCount = likeCount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
}
