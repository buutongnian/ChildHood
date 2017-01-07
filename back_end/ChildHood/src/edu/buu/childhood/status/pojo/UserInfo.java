package edu.buu.childhood.status.pojo;

import java.util.List;
import edu.buu.childhood.my.pojo.ChildInf;
import edu.buu.childhood.my.pojo.User;

public class UserInfo {
	private String userName;
	private String userHeadImage;
	private String userNickname;
	private int belongingArea;
	private int belongingProvince;
	private int belongingCity;
	private int belongingDistrict;
	private String community;
	private String detailAddr;
	private String email;
	private int achievementPoint;
	private int userLevel;
	private int likeCount;
	private List<ChildInf> childInf;

	public UserInfo(User user, List<ChildInf> childInf) {
		this.userName = user.getUserName();
		this.userHeadImage = user.getUserHeadImage();
		this.userNickname = user.getUserNickname();
		this.belongingArea = user.getBelongingArea();
		this.belongingProvince = user.getBelongingProvince();
		this.belongingCity = user.getBelongingCity();
		this.belongingDistrict = user.getBelongingDistrict();
		this.community = user.getCommunity();
		this.detailAddr = user.getDetailAddr();
		this.email = user.getEmail();
		this.achievementPoint = user.getAchievementPoint();
		this.userLevel = user.getUserLevel();
		this.childInf = childInf;
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

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public int getBelongingArea() {
		return belongingArea;
	}

	public void setBelongingArea(int belongingArea) {
		this.belongingArea = belongingArea;
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

	public int getBelongingDistrict() {
		return belongingDistrict;
	}

	public void setBelongingDistrict(int belongingDistrict) {
		this.belongingDistrict = belongingDistrict;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public String getDetailAddr() {
		return detailAddr;
	}

	public void setDetailAddr(String detailAddr) {
		this.detailAddr = detailAddr;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public List<ChildInf> getChildInf() {
		return childInf;
	}

	public void setChildInf(List<ChildInf> childInf) {
		this.childInf = childInf;
	}

}
