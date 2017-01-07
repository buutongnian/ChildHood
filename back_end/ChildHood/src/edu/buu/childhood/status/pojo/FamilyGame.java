package edu.buu.childhood.status.pojo;

import java.util.Date;

/**
 * yingshe T_STATUS_FAMILY_GAME
 * 
 * @author joe
 *
 */
public class FamilyGame {
	private int gameId;
	private String userName;
	private int gameCode;
	private Date gameTime;
	private Date insertTime;
	private String gameNote;

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

	public int getGameCode() {
		return gameCode;
	}

	public void setGameCode(int gameCode) {
		this.gameCode = gameCode;
	}

	public Date getGameTime() {
		return gameTime;
	}

	public void setGameTime(Date gameTime) {
		this.gameTime = gameTime;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getGameNote() {
		return gameNote;
	}

	public void setGameNote(String gameNote) {
		this.gameNote = gameNote;
	}

}
