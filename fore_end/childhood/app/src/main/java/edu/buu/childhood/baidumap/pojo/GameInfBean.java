package edu.buu.childhood.baidumap.pojo;

/**
 * Created by Administrator on 2016/9/10.
 */
public class GameInfBean {
    private int gameId;
    private String gameFounderName;
    private String gameName;
    private String gameFounderNickName;
    private String gamePlace;
    private String gameSynopsis;
    private int recommendCount;
    private int currGameCount;
    private String gameStatus;

    public String getGameFounderName() {
        return gameFounderName;
    }

    public void setGameFounderName(String gameFounderName) {
        this.gameFounderName = gameFounderName;
    }

    public String getGameFounderNickName() {
        return gameFounderNickName;
    }

    public void setGameFounderNickName(String gameFounderNickName) {
        this.gameFounderNickName = gameFounderNickName;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGamePlace() {
        return gamePlace;
    }

    public void setGamePlace(String gamePlace) {
        this.gamePlace = gamePlace;
    }

    public String getGameSynopsis() {
        return gameSynopsis;
    }

    public void setGameSynopsis(String gameSynopsis) {
        this.gameSynopsis = gameSynopsis;
    }

    public int getRecommendCount() {
        return recommendCount;
    }

    public void setRecommendCount(int recommendCount) {
        this.recommendCount = recommendCount;
    }

    public int getCurrGameCount() {
        return currGameCount;
    }

    public void setCurrGameCount(int currGameCount) {
        this.currGameCount = currGameCount;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }
}
