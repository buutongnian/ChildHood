package edu.buu.childhood.achvmt.medal.pojo;

/*
 * 2016/9/27
 * @Author Joe
 * 
 * 映射T_ACHVMT_MEDAL表
 */
public class Medal {
	private int medalId;
	private String medalName;
	private String medalDescribe;
	private int achievementPoint;

	public int getMedalId() {
		return medalId;
	}

	public void setMedalId(int medalId) {
		this.medalId = medalId;
	}

	public String getMedalName() {
		return medalName;
	}

	public void setMedalName(String medalName) {
		this.medalName = medalName;
	}

	public String getMedalDescribe() {
		return medalDescribe;
	}

	public void setMedalDescribe(String medalDescribe) {
		this.medalDescribe = medalDescribe;
	}

	public int getAchievementPoint() {
		return achievementPoint;
	}

	public void setAchievementPoint(int achievementPoint) {
		this.achievementPoint = achievementPoint;
	}

}
