package edu.buu.childhood.game.pojo;

import android.graphics.drawable.BitmapDrawable;

/**
 * 初始数据
 * Created by lcc on 2016/6/16.
 */
public class ItemBean {
    private char Image;
    private BitmapDrawable background;
    private String memNumSize;
    private String ageRank;
    private String gameSynopsis;
    private String imageUrl;
    private int gameCode;
    private String gameTitle;
    private String gameContent;
    private String select;

    public String getGameContent() {
        return gameContent;
    }

    public void setGameContent(String gameContent) {
        this.gameContent = gameContent;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public int getGameCode() {
        return gameCode;
    }

    public void setGameCode(int gameCode) {
        this.gameCode = gameCode;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public char getImage() {
        return Image;
    }

    public void setImage(char image) {
        Image = image;
    }

    public BitmapDrawable getBackground() {
        return background;
    }

    public void setBackground(BitmapDrawable background) {
        this.background = background;
    }

    public String getMemNumSize() {
        return memNumSize;
    }

    public void setMemNumSize(String memNumSize) {
        this.memNumSize = memNumSize;
    }

    public String getAgeRank() {
        return ageRank;
    }

    public void setAgeRank(String ageRank) {
        this.ageRank = ageRank;
    }

    public String getGameSynopsis() {
        return gameSynopsis;
    }

    public void setGameSynopsis(String gameSynopsis) {
        this.gameSynopsis = gameSynopsis;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public ItemBean() {

    }

}

