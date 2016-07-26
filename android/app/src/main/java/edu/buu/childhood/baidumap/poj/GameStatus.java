package edu.buu.childhood.baidumap.poj;

import java.util.Date;

/**
 * 2016/6/17
 * @Author Joe
 * @note 映射T_STATUS_GAME表
 */
public class GameStatus {
	
	private int gameId;
	private int gameCode;
	private String gameFounder;
	private Date startTime;
	private Date finishTime;
	private double gameLatitude;
	private double gameLongitude;
	private String gatherPlace;
	private String customInf;
	private Integer customCount;
	private String gameStatus;
	// TODO 2016/7/4 此处可能需要做级联查询GameHead，推送信息目前只有GameCode没有GameTitle
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public int getGameCode() {
		return gameCode;
	}
	public void setGameCode(int gameCode) {
		this.gameCode = gameCode;
	}
	public String getGameFounder() {
		return gameFounder;
	}
	public void setGameFounder(String gameFounder) {
		this.gameFounder = gameFounder;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public double getGameLatitude() {
		return gameLatitude;
	}
	public void setGameLatitude(double gameLatitude) {
		this.gameLatitude = gameLatitude;
	}
	public double getGameLongitude() {
		return gameLongitude;
	}
	public void setGameLongitude(double gameLongitude) {
		this.gameLongitude = gameLongitude;
	}
	public String getGatherPlace() {
		return gatherPlace;
	}
	public void setGatherPlace(String gatherPlace) {
		this.gatherPlace = gatherPlace;
	}
	public String getCustomInf() {
		return customInf;
	}
	public void setCustomInf(String customInf) {
		this.customInf = customInf;
	}
	public Integer getCustomCount() {
		return customCount;
	}
	public void setCustomCount(Integer customCount) {
		this.customCount = customCount;
	}
	public String getGameStatus() {
		return gameStatus;
	}
	public void setGameStatus(String gameStatus) {
		this.gameStatus = gameStatus;
	}
}
