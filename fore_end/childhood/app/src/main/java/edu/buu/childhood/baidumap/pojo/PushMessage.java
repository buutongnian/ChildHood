package edu.buu.childhood.baidumap.pojo;

/**
 * Created by Administrator on 2016/9/4.
 */
public class PushMessage {
    private String userName;
    private int gameId;
    private String gameTitle;
    private String gameFounder;
    private String founderNickName;
    private String foundTime;
    private String gatherPlace;
    private String customInf;
    private String gameIcon;
    private int customCount;
    private int isRead;

    public String getGameIcon() {
        return gameIcon;
    }

    public void setGameIcon(String gameIcon) {
        this.gameIcon = gameIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getGameFounder() {
        return gameFounder;
    }

    public void setGameFounder(String gameFounder) {
        this.gameFounder = gameFounder;
    }

    public String getFounderNickName() {
        return founderNickName;
    }

    public void setFounderNickName(String founderNickName) {
        this.founderNickName = founderNickName;
    }

    public String getFoundTime() {
        return foundTime;
    }

    public void setFoundTime(String foundTime) {
        this.foundTime = foundTime;
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

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }
}
