package edu.buu.childhood.status.pojo;

import java.util.Date;

/*
 * 2016/6/17
 * @Author Joe
 * 映射V_STATUS_GAME_CANJOIN视图
 */
public class GameCanJoin {
	
	private int gameId;
	private int gameCode;
	private String gameFounder;
	private Date foundTime;
	private Date startTime;
	private double gameLatitude;
	private double gameLongitude;
	private String gatherPlace;
	private String customInf;
	private int customCount;
	private int joinedCount;
	
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
	public Date getFoundTime() {
		return foundTime;
	}
	public void setFoundTime(Date foundTime) {
		this.foundTime = foundTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
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
	public int getCustomCount() {
		return customCount;
	}
	public void setCustomCount(int customCount) {
		this.customCount = customCount;
	}
	public int getJoinedCount() {
		return joinedCount;
	}
	public void setJoinedCount(int joinedCount) {
		this.joinedCount = joinedCount;
	}
}
