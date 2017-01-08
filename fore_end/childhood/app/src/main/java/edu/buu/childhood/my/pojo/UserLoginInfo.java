package edu.buu.childhood.my.pojo;

/**
 * Created by lcc on 2016/7/29.
 */

import java.io.Serializable;

public class UserLoginInfo implements Serializable {

    private String id;
    private String token;
    private String tokenSecret;
    private String userName;
    private String userPassword;
    private String myLatitude;
    private String myLongitude;
    private int isPerfect;
    private int isLogin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMyLatitude() {
        return myLatitude;
    }

    public void setMyLatitude(String myLatitude) {
        this.myLatitude = myLatitude;
    }

    public String getMyLongitude() {
        return myLongitude;
    }

    public void setMyLongitude(String myLongitude) {
        this.myLongitude = myLongitude;
    }

    public int getIsPerfect() {
        return isPerfect;
    }

    public void setIsPerfect(int isPerfect) {
        this.isPerfect = isPerfect;
    }

    public int getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(int isLogin) {
        this.isLogin = isLogin;
    }
}