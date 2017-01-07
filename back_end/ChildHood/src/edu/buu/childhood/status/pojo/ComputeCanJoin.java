package edu.buu.childhood.status.pojo;

import java.util.Date;

public class ComputeCanJoin {
	private int gameId;
	private int gameCode;
	private String gameTitle;
	private String gameIcon;
	private String userNickname;
	private Date startTime;
	private double gameLatitude;
	private double gameLongitude;
	private String gatherPlace;
	private String customInf;
	private int customCount;
	private String gameStatus;
	private int joinedCount;
	private int distance;

	public ComputeCanJoin() {

	}

	public ComputeCanJoin(int gameId, int gameCode, String gameTitle,
			String gameIcon, String userNickname, Date startTime,
			double gameLatitude, double gameLongitude, String gatherPlace,
			String customInf, int customCount, String gameStatus,int joinedCount, int distance) {
		this.gameId = gameId;
		this.gameCode = gameCode;
		this.gameTitle = gameTitle;
		this.gameIcon = gameIcon;
		this.userNickname = userNickname;
		this.startTime = startTime;
		this.gameLatitude = gameLatitude;
		this.gameLongitude = gameLongitude;
		this.gatherPlace = gatherPlace;
		this.customInf = customInf;
		this.customCount = customCount;
		this.gameStatus = gameStatus;
		this.joinedCount = joinedCount;
		this.distance = distance;
	}

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

	public String getGameTitle() {
		return gameTitle;
	}

	public void setGameTitle(String gameTitle) {
		this.gameTitle = gameTitle;
	}

	public String getGameIcon() {
		return gameIcon;
	}

	public void setGameIcon(String gameIcon) {
		this.gameIcon = gameIcon;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
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

	public String getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(String gameStatus) {
		this.gameStatus = gameStatus;
	}

	public int getJoinedCount() {
		return joinedCount;
	}

	public void setJoinedCount(int joinedCount) {
		this.joinedCount = joinedCount;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
}
