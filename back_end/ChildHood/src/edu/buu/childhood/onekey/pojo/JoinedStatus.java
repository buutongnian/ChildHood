package edu.buu.childhood.onekey.pojo;

import edu.buu.childhood.common.C;

/**
 * 2016/6/17
 * 
 * @Author Joe
 * @note 映射T_STATUS_JOINED表
 */
public class JoinedStatus {

	private int joinedId;
	private int gameId;
	private String userName;
	private char gameScore = C.score.FIVESTARS;
	private String joinStatus;
	private char isScored;

	public int getJoinedId() {
		return joinedId;
	}

	public void setJoinedId(int joinedId) {
		this.joinedId = joinedId;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public char getGameScore() {
		return gameScore;
	}

	public void setGameScore(char gameScore) {
		this.gameScore = gameScore;
	}

	public String getJoinStatus() {
		return joinStatus;
	}

	public void setJoinStatus(String joinStatus) {
		this.joinStatus = joinStatus;
	}

	public char getIsScored() {
		return isScored;
	}

	public void setIsScored(char isScored) {
		this.isScored = isScored;
	}

}
