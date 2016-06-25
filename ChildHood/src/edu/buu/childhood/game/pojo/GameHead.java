package edu.buu.childhood.game.pojo;

import edu.buu.childhood.common.C;

/**
 * 2016/6/16
 * @Author Joe
 * @note 映射T_GAMERULE_HEAD表
 */
public class GameHead {
	
	private int gameCode;
	private String gametTitle;
	private String gameIcon;
	private int gameArea;
	private int gameType;
	private String gameSynopsis;
	private int ageCode;
	private int memNumCode;
	private int recommendCount;
	private int leastCount;
	private int topCount;
	private String gameContributor;
	private char enable=C.def.ENABLED;//默认设置enable状态为'Y'
	
	public int getGameCode() {
		return gameCode;
	}
	public void setGameCode(int gameCode) {
		this.gameCode = gameCode;
	}
	public String getGametTitle() {
		return gametTitle;
	}
	public void setGametTitle(String gametTitle) {
		this.gametTitle = gametTitle;
	}
	public String getGameIcon() {
		return gameIcon;
	}
	public void setGameIcon(String gameIcon) {
		this.gameIcon = gameIcon;
	}
	public int getGameArea() {
		return gameArea;
	}
	public void setGameArea(int gameArea) {
		this.gameArea = gameArea;
	}
	public int getGameType() {
		return gameType;
	}
	public void setGameType(int gameType) {
		this.gameType = gameType;
	}
	public String getGameSynopsis() {
		return gameSynopsis;
	}
	public void setGameSynopsis(String gameSynopsis) {
		this.gameSynopsis = gameSynopsis;
	}
	public int getAgeCode() {
		return ageCode;
	}
	public void setAgeCode(int ageCode) {
		this.ageCode = ageCode;
	}
	public int getMemNumCode() {
		return memNumCode;
	}
	public void setMemNumCode(int memNumCode) {
		this.memNumCode = memNumCode;
	}
	public int getRecommendCount() {
		return recommendCount;
	}
	public void setRecommendCount(int recommendCount) {
		this.recommendCount = recommendCount;
	}
	public int getLeastCount() {
		return leastCount;
	}
	public void setLeastCount(int leastCount) {
		this.leastCount = leastCount;
	}
	public int getTopCount() {
		return topCount;
	}
	public void setTopCount(int topCount) {
		this.topCount = topCount;
	}
	public String getGameContributor() {
		return gameContributor;
	}
	public void setGameContributor(String gameContributor) {
		this.gameContributor = gameContributor;
	}
	public char getEnable() {
		return enable;
	}
	public void setEnable(char enable) {
		this.enable = enable;
	}
}
