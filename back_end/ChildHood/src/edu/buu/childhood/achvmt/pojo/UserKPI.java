package edu.buu.childhood.achvmt.pojo;

import java.io.Serializable;

public class UserKPI implements Serializable {
	private static final long serialVersionUID = 4365880735601164664L;
	private String userName;
	private String userHeadImage;
	private String userNickname;
	private int belongingProvince;
	private int belongingCity;
	private int achievementPoint;
	private int userLevel;
	private String levelName;
	private int currentLevelPoint;
	private int nextLevelPoint;
	private int totalGameCount;
	private int totalLikeCount;
	private int totalFoundGameCount;
	private int totalKindGameCount;

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

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public int getBelongingProvince() {
		return belongingProvince;
	}

	public void setBelongingProvince(int belongingProvince) {
		this.belongingProvince = belongingProvince;
	}

	public int getBelongingCity() {
		return belongingCity;
	}

	public void setBelongingCity(int belongingCity) {
		this.belongingCity = belongingCity;
	}

	public int getAchievementPoint() {
		return achievementPoint;
	}

	public void setAchievementPoint(int achievementPoint) {
		this.achievementPoint = achievementPoint;
	}

	public int getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(int userLevel) {
		this.userLevel = userLevel;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public int getCurrentLevelPoint() {
		return currentLevelPoint;
	}

	public void setCurrentLevelPoint(int currentLevelPoint) {
		this.currentLevelPoint = currentLevelPoint;
	}

	public int getNextLevelPoint() {
		return nextLevelPoint;
	}

	public void setNextLevelPoint(int nextLevelPoint) {
		this.nextLevelPoint = nextLevelPoint;
	}

	public int getTotalGameCount() {
		return totalGameCount;
	}

	public void setTotalGameCount(int totalGameCount) {
		this.totalGameCount = totalGameCount;
	}

	public int getTotalLikeCount() {
		return totalLikeCount;
	}

	public void setTotalLikeCount(int totalLikeCount) {
		this.totalLikeCount = totalLikeCount;
	}

	public int getTotalFoundGameCount() {
		return totalFoundGameCount;
	}

	public void setTotalFoundGameCount(int totalFoundGameCount) {
		this.totalFoundGameCount = totalFoundGameCount;
	}

	public int getTotalKindGameCount() {
		return totalKindGameCount;
	}

	public void setTotalKindGameCount(int totalKindGameCount) {
		this.totalKindGameCount = totalKindGameCount;
	}

}
