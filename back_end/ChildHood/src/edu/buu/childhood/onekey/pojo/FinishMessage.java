package edu.buu.childhood.onekey.pojo;

public class FinishMessage {
	private int gameId;
	private int userLevel;

	public FinishMessage(int gameId, int userLevel) {
		this.gameId = gameId;
		this.userLevel = userLevel;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public int getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(int userLevel) {
		this.userLevel = userLevel;
	}

}
