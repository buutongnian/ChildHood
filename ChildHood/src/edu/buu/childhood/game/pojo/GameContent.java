package edu.buu.childhood.game.pojo;

import edu.buu.childhood.common.C;

/**
 * 2016/6/16
 * @Author Joe
 * 映射T_GAMERULE_CONTENT表
 */
public class GameContent {
	
	private int gameCode;
	private String gameContent;
	private char enable=C.def.ENABLED;//默认设置enable状态为'Y'
	
	public int getGameCode() {
		return gameCode;
	}
	public void setGameCode(int gameCode) {
		this.gameCode = gameCode;
	}
	public String getGameContent() {
		return gameContent;
	}
	public void setGameContent(String gameContent) {
		this.gameContent = gameContent;
	}
	public char getEnable() {
		return enable;
	}
	public void setEnable(char enable) {
		this.enable = enable;
	}
}
