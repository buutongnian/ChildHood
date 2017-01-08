package edu.buu.childhood.baidumap.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 2016/6/17
 * 
 * @Author Joe
 * @note 映射V_STATUS_GAME_CANJOIN视图
 */
public class GameCanJoin implements Serializable {

	/**
	 * GameCanJoin类序列化唯一编码
	 */
	private static final long serialVersionUID = -3318971272206281858L;

	private int gameId;
	private int gameCode;
	private String gameIcon;
	private String gameTitle;
	private String gameFounder;
	private String founderNickname;
	private Date foundTime;
	private Date startTime;
	private double gameLatitude;
	private double gameLongitude;
	private String gatherPlace;
	private String customInf;
	private int customCount;
	private int joinedCount;

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

	public String getGameIcon() {
		return gameIcon;
	}

	public void setGameIcon(String gameIcon) {
		this.gameIcon = gameIcon;
	}

	public String getGameTitle() {
		return gameTitle;
	}

	public void setGameTitle(String gameTitle) {
		this.gameTitle = gameTitle;
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

	public Date getFoundTime() {
		return foundTime;
	}

	public void setFoundTime(Date foundTime) {
		this.foundTime = foundTime;
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

	public int getJoinedCount() {
		return joinedCount;
	}

	public void setJoinedCount(int joinedCount) {
		this.joinedCount = joinedCount;
	}

}
