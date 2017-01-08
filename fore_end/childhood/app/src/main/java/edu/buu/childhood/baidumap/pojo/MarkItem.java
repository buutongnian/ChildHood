package edu.buu.childhood.baidumap.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lcc on 2016/7/24.
 */
public class MarkItem implements Serializable {
    private int gameId;
    private String gameContent;
    private int recommendCount;
    private String gameTitle;
    private double gameLatitude;
    private double gameLongitude;
    private String gatherPlace;
    private Integer customCount;
    private Date startTime;
    private int gameCode;
    private String userNickname;
    private double lastLatitude;
    private double lastLongitude;
    private int distance;
    private String select;
//选择游戏变量
    private String selectGameTitle;
    private int gameSelectCode;

    public String getSelectGameTitle() {
        return selectGameTitle;
    }

    public void setSelectGameTitle(String selectGameTitle) {
        this.selectGameTitle = selectGameTitle;
    }

    public int getGameSelectCode() {
        return gameSelectCode;
    }

    public void setGameSelectCode(int gameSelectCode) {
        this.gameSelectCode = gameSelectCode;
    }



    public String getGameContent() {
        return gameContent;
    }

    public void setGameContent(String gameContent) {
        this.gameContent = gameContent;
    }

    public int getRecommendCount() {
        return recommendCount;
    }

    public void setRecommendCount(int recommendCount) {
        this.recommendCount = recommendCount;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
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

    public Integer getCustomCount() {
        return customCount;
    }

    public void setCustomCount(Integer customCount) {
        this.customCount = customCount;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getGameCode() {
        return gameCode;
    }

    public void setGameCode(int gameCode) {
        this.gameCode = gameCode;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public double getLastLatitude() {
        return lastLatitude;
    }

    public void setLastLatitude(double lastLatitude) {
        this.lastLatitude = lastLatitude;
    }

    public double getLastLongitude() {
        return lastLongitude;
    }

    public void setLastLongitude(double lastLongitude) {
        this.lastLongitude = lastLongitude;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }
}
