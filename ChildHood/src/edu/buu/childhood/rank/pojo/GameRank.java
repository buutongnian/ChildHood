package edu.buu.childhood.rank.pojo;

import java.io.Serializable;

import edu.buu.childhood.game.pojo.GameHead;

/**
 * 2016/6/17
 * @Author Joe
 * @note 映射V_RANK_GAME视图
 */
public class GameRank implements Serializable{

	
	/**
	 *GameRank类序列化唯一编码 
	 */
	private static final long serialVersionUID = 1461563658441718625L;
	
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