package edu.buu.childhood.baidumap.pojo;

/**
 * Created by lcc on 2016/7/2.
 */

public class NewMessage_ItemBean {
    public int Image;
    private int gameId;
    public String gameTitle;
    public String gameFounder;
    public String gameSynopsis;
    public String join = "加入";
    private String gameIcoUrl;


    public NewMessage_ItemBean(int gameId, String gameIcoUrl, String gameTitle, String gameFounder, String gameSynopsis) {
        this.gameId = gameId;
        this.gameIcoUrl = gameIcoUrl;
        this.gameTitle = gameTitle;
        this.gameFounder = "来自" + gameFounder + "的游戏邀请";
        this.gameSynopsis = gameSynopsis;

    }

    public NewMessage_ItemBean(int gameId,String gameTitle, String gameFounder, String gameSynopsis) {
        this.gameId=gameId;
        this.gameTitle = gameTitle;
        this.gameFounder = gameFounder;
        this.gameSynopsis = gameSynopsis;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameIcoUrl() {
        return gameIcoUrl;
    }

    public void setGameIcoUrl(String gameIcoUrl) {
        this.gameIcoUrl = gameIcoUrl;
    }
}
