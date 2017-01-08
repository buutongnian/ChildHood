package edu.buu.childhood.my.pojo;

/**
 * Created by lcc on 2016/7/19.
 */
public class BackItem {
    private Boolean isInGame;
    private String isright;
    private int gameId;
    private String gameFounder;
    private String gameStatus;
    private User user;
    public String getGameFounder() {
        return gameFounder;
    }

    public void setGameFounder(String gameFounder) {
        this.gameFounder = gameFounder;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public Boolean getInGame() {
        return isInGame;
    }

    public void setInGame(Boolean inGame) {
        isInGame = inGame;
    }

    public String getIsright() {
        return isright;
    }

    public void setIsright(String isright) {
        this.isright = isright;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
