package edu.buu.childhood.baidumap.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lcc on 2016/7/8.
 */
public class baiduItemBean implements Serializable {

    private double gameLatitude;
    private double gameLongitude;
    private String gatherPlace;
    private Integer customCount;
    private Date startTime;
    private int gameCode;
    private String userNickname;
    private String select;

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
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
}