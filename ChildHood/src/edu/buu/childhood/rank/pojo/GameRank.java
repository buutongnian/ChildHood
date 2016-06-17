package edu.buu.childhood.rank.pojo;

import edu.buu.childhood.game.pojo.GameHead;

/*
 * 2016/6/17
 * @Author Joe
 * 映射V_RANK_GAME视图
 */
public class GameRank {
	
	private int gameCode;
	private int gameHeat;
	
	private GameHead gameHead;

	public int getGameCode() {
		return gameCode;
	}

	public void setGameCode(int gameCode) {
		this.gameCode = gameCode;
	}

	public int getGameHeat() {
		return gameHeat;
	}

	public void setGameHeat(int gameHeat) {
		this.gameHeat = gameHeat;
	}

	public GameHead getGameHead() {
		return gameHead;
	}

	public void setGameHead(GameHead gameHead) {
		this.gameHead = gameHead;
	}
}
