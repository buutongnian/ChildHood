package edu.buu.childhood.onekey.pojo;

import edu.buu.childhood.game.pojo.GameHead;
import edu.buu.childhood.status.pojo.UserInfo;

public class GameInfo {

	private int count;
	private UserInfo userInfo;
	private GameStatus gameStatus;
	private GameHead gameHead;

	public GameInfo(int count, UserInfo userInfo, GameStatus gameStatus,
			GameHead gameHead) {
		this.count = count;
		this.userInfo = userInfo;
		this.gameStatus = gameStatus;
		this.gameHead = gameHead;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public GameStatus getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}

	public GameHead getGameHead() {
		return gameHead;
	}

	public void setGameHead(GameHead gameHead) {
		this.gameHead = gameHead;
	}

}
