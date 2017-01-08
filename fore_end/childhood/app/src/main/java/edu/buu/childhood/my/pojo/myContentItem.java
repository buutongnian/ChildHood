package edu.buu.childhood.my.pojo;

import java.util.Date;

/**
 * Created by lcc on 2016/7/15.
 */
public class myContentItem {
    private int userIcon;
    private String userNiname;
    private String userGender;
    private String liveCommunity;
    private Date userBirthday;
    private String select;
    private int childId;
    private String imageUrl;

    public int getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(int userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserNiname() {
        return userNiname;
    }

    public void setUserNiname(String userNiname) {
        this.userNiname = userNiname;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getLiveCommunity() {
        return liveCommunity;
    }

    public void setLiveCommunity(String liveCommunity) {
        this.liveCommunity = liveCommunity;
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
