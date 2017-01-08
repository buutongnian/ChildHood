package edu.buu.childhood.rank.pojo;

import java.util.Date;

import edu.buu.childhood.common.C;

/**
 * 2016/9/26
 *
 * @Author Joe
 * @note 映射T_SYS_USER表
 */
public class User {

    private String userName; //用户名，现版本中同手机号
    private String userId; //用户ID（现版本中未启用）
    private String userTelNum; //用户手机号
    private Date regDate; //注册日期
    private double regLatitude; //注册纬度
    private double regLongitude; //注册经度
    private String userPwd; //密码
    private String userHeadImage; //头像
    private String userNickname; //昵称
    private double lastLatitude; //上次（如现在在线即当前）登录纬度
    private double lastLongitude; //上次（如现在在线即当前）登录经度
    private int belongingArea; //所属地区
    private int belongingProvince; //所属省
    private int belongingCity; //所属市
    private int belongingDistrict; //所属区
    private String community; //小区
    private String detailAddr; //详细地址
    private String email; //电子邮箱地址
    private int achievementPoint; //成就点
    private int userLevel; //成就等级
    private int likeCount; //被点赞数量

    private String loginStatus = C.status.OFFLINE;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserTelNum() {
        return userTelNum;
    }

    public void setUserTelNum(String userTelNum) {
        this.userTelNum = userTelNum;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public double getRegLatitude() {
        return regLatitude;
    }

    public void setRegLatitude(double regLatitude) {
        this.regLatitude = regLatitude;
    }

    public double getRegLongitude() {
        return regLongitude;
    }

    public void setRegLongitude(double regLongitude) {
        this.regLongitude = regLongitude;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserHeadImage() {
        return userHeadImage;
    }

    public void setUserHeadImage(String userHeadImage) {
        this.userHeadImage = userHeadImage;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public double getLastLatitude() {
        return lastLatitude;
    }

    public void setLastLatitude(double lastLatitude) {
        this.lastLatitude = lastLatitude;
    }

    public double getLastLongitude() {
        return lastLongitude;
    }

    public void setLastLongitude(double lastLongitude) {
        this.lastLongitude = lastLongitude;
    }

    public int getBelongingArea() {
        return belongingArea;
    }

    public void setBelongingArea(int belongingArea) {
        this.belongingArea = belongingArea;
    }

    public int getBelongingProvince() {
        return belongingProvince;
    }

    public void setBelongingProvince(int belongingProvince) {
        this.belongingProvince = belongingProvince;
    }

    public int getBelongingCity() {
        return belongingCity;
    }

    public void setBelongingCity(int belongingCity) {
        this.belongingCity = belongingCity;
    }

    public int getBelongingDistrict() {
        return belongingDistrict;
    }

    public void setBelongingDistrict(int belongingDistrict) {
        this.belongingDistrict = belongingDistrict;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getDetailAddr() {
        return detailAddr;
    }

    public void setDetailAddr(String detailAddr) {
        this.detailAddr = detailAddr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    public int getAchievementPoint() {
        return achievementPoint;
    }

    public void setAchievementPoint(int achievementPoint) {
        this.achievementPoint = achievementPoint;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

}
