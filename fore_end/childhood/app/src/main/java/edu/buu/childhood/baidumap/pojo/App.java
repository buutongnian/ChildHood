package edu.buu.childhood.baidumap.pojo;

/**
 * Created by lcc on 2016/7/2.
 */
public class App {
    private String userName;
    private int aIcon;
    private String aName;
    private String imageUrl;



    public App(String userName, int aIcon, String aName, String imageUrl) {
        this.aIcon = aIcon;
        this.aName = aName;
        this.userName = userName;
        this.imageUrl = imageUrl;
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
        return aIcon;
    }

    public String getaName() {
        return aName;
    }

    public void setaIcon(int aIcon) {
        this.aIcon = aIcon;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }
}
