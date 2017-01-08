
package edu.buu.childhood.chat.bean;


public class User {
    private String userName;
    private int unreadMsgCount;
    private String usernick;
    private String tel;
    private String avatar;
    private String sign;
    private String userImage;
    private int achievementPoint;
    private int userLevel;
    private int currentLevelPoint;
    private int nextLevelPoint;
    private int totalGameCount;
    private int totalLikeCount;
    private String levelName;
    private String province;
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUsernick() {
        return usernick;
    }

    public void setUsernick(String usernick) {
        this.usernick = usernick;
    }


    public int getUnreadMsgCount() {
        return unreadMsgCount;
    }

    public void setUnreadMsgCount(int unreadMsgCount) {
        this.unreadMsgCount = unreadMsgCount;
    }

    public void setNick(String usernick) {
        this.usernick = usernick;
    }

    public String getNick() {
        return usernick;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTel() {
        return tel;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign() {
        return sign;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public int getAchievementPoint() {
        return achievementPoint;
    }

    public void setAchievementPoint(int achievementPoint) {
        this.achievementPoint = achievementPoint;
    }

    public int getCurrentLevelPoint() {
        return currentLevelPoint;
    }

    public void setCurrentLevelPoint(int currentLevelPoint) {
        this.currentLevelPoint = currentLevelPoint;
    }

    public int getNextLevelPoint() {
        return nextLevelPoint;
    }

    public void setNextLevelPoint(int nextLevelPoint) {
        this.nextLevelPoint = nextLevelPoint;
    }

    public int getTotalGameCount() {
        return totalGameCount;
    }

    public void setTotalGameCount(int totalGameCount) {
        this.totalGameCount = totalGameCount;
    }

    public int getTotalLikeCount() {
        return totalLikeCount;
    }

    public void setTotalLikeCount(int totalLikeCount) {
        this.totalLikeCount = totalLikeCount;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
