package edu.buu.childhood.common;

/**
 * 2016/6/17
 * @Author Joe
 * 映射T_PUB_GAME_AGE表
 */
public class GameAge {
	
	private int ageCode;
	private String ageInterval;//游戏适合年龄段，系统常量类中规定好
	
	public int getAgeCode() {
		return ageCode;
	}
	public void setAgeCode(int ageCode) {
		this.ageCode = ageCode;
	}
	public String getAgeInterval() {
		return ageInterval;
	}
	public void setAgeInterval(String ageInterval) {
		this.ageInterval = ageInterval;
	}
}
