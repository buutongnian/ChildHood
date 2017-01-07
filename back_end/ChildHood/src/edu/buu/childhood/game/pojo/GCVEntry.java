package edu.buu.childhood.game.pojo;

/**
 * 映射T_GAMERULE_GCV
 * @author joe
 *
 */
public class GCVEntry {
	private int gameCode;
	private String GCVjson;

	public int getGameCode() {
		return gameCode;
	}

	public void setGameCode(int gameCode) {
		this.gameCode = gameCode;
	}

	public String getGCVjson() {
		return GCVjson;
	}

	public void setGCVjson(String gCVjson) {
		GCVjson = gCVjson;
	}

	@Override
	public String toString() {
		return "GCVEntry [gameCode=" + gameCode + ", GCVjson=" + GCVjson + "]";
	}

}
