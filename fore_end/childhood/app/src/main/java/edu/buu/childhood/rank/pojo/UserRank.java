package edu.buu.childhood.rank.pojo;

public class UserRank {
	private int rank;
	private String userName;
	private String userHeadImage;
	private String usernNickname;
	private int achievementPoint;
	private int likeCount;
	private boolean couldLike;

	public UserRank() {
		super();
	}

	public UserRank(int rank, String userName, String userHeadImage,
			String usernNickname, int achievementPoint, int likeCount) {
		super();
		this.rank = rank;
		this.userName = userName;
		this.userHeadImage = userHeadImage;
		this.usernNickname = usernNickname;
		this.achievementPoint = achievementPoint;
		this.likeCount = likeCount;
	}

	public UserRank(int rank, String userName, String userHeadImage,
			String usernNickname, int achievementPoint, int likeCount,
			boolean couldLike) {
		super();
		this.rank = rank;
		this.userName = userName;
		this.userHeadImage = userHeadImage;
		this.usernNickname = usernNickname;
		this.achievementPoint = achievementPoint;
		this.likeCount = likeCount;
		this.couldLike = couldLike;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserHeadImage() {
		return userHeadImage;
	}

	public void setUserHeadImage(String userHeadImage) {
		this.userHeadImage = userHeadImage;
	}

	public String getUsernNickname() {
		return usernNickname;
	}

	public void setUsernNickname(String usernNickname) {
		this.usernNickname = usernNickname;
	}

	public int getAchievementPoint() {
		return achievementPoint;
	}

	public void setAchievementPoint(int achievementPoint) {
		this.achievementPoint = achievementPoint;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public boolean isCouldLike() {
		return couldLike;
	}

	public void setCouldLike(boolean couldLike) {
		this.couldLike = couldLike;
	}

}
