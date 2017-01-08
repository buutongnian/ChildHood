package edu.buu.childhood.baidumap.pojo;

/**
 * Created by lcc on 2016/7/2.
 */

public class Book {
    private String userName;
    private int bIcon;
    private String bName;
    private String imageUrl;

    public Book() {
    }

    public Book(String userName,int aIcon, String aName,String imageUrl) {
        this.bIcon = aIcon;
        this.bName = aName;
        this.userName=userName;
        this.imageUrl=imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public int getaIcon() {
        return bIcon;
    }

    public String getaName() {
        return bName;
    }

    public void setaIcon(int aIcon) {
        this.bIcon = aIcon;
    }

    public void setaName(String aName) {
        this.bName = aName;
    }
}