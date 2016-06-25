package edu.buu.childhood.common;

/**
 * 2016/6/17
 * @Author Joe
 * @note 映射T_PUB_GAME_MEMNUM表
 */
public class GameMemNum {
	
	private int memNumCode;
	private String memNumInterval;//游戏适合人数段，系统常量类中规定好
	
	public int getMemNumCode() {
		return memNumCode;
	}
	public void setMemNumCode(int memNumCode) {
		this.memNumCode = memNumCode;
	}
	public String getMemNumInterval() {
		return memNumInterval;
	}
	public void setMemNumInterval(String memNumInterval) {
		this.memNumInterval = memNumInterval;
	}
}
