package edu.buu.childhood.achvmt.pojo;

import java.io.Serializable;
import java.util.Date;

public class UserHistoryGameList implements Serializable {
	private static final long serialVersionUID = 3314776270328042626L;
	private String userName;
	private int gameId;
	private int gameCode;
	private String gameTitle;
	private String gameIcon;
	private String gameFounder;
	private String founderNickname;
	private Date startTime;
	private Date finishTime;
	private String gatherPlace;
	private String customInf;
	private char gameScore;
	private char isScored;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getGameFounder() {
		return gameFounder;
	}

	public void setGameFounder(String gameFounder) {
		this.gameFounder = gameFounder;
	}

	public String getFounderNickname() {
		return founderNickname;
	}

	public void setFounderNickname(String founderNickname) {
		this.founderNickname = founderNickname;
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

	public char getGameScore() {
		return gameScore;
	}

	public void setGameScore(char gameScore) {
		this.gameScore = gameScore;
	}

	public char getIsScored() {
		return isScored;
	}

	public void setIsScored(char isScored) {
		this.isScored = isScored;
	}

}
