package edu.buu.childhood.game.pojo;

import android.graphics.drawable.BitmapDrawable;

/**
 * 初始数据
 * Created by lcc on 2016/6/16.
 */
public class ItemBean {
    public char Image;
    public BitmapDrawable background;
    public String memNumSize;
    public String ageRank;
    public String  gameSynopsis;

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

    public ItemBean(){

    }

}

