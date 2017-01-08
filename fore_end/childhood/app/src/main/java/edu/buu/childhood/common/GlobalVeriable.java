package edu.buu.childhood.common;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;

import edu.buu.childhood.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Administrator on 2016/8/30.
 */
public class GlobalVeriable extends Application {

    //用户是否已完善信息，如未完善，则会提示，使用默认密码登录
    private boolean isPerfectInfo;
    //是否有未读消息
    private boolean haveMessage;

    private Boolean isLogin;//是否登录
    private Boolean isRunnigMainBaidu;//百度chatRoom是否在运行
    private Boolean isRunningNewMessage;//推送newMessage消息的界面是否运行
    private Boolean isGameFounder;//是否是创建者
    private Boolean isReceiveMessage;//是否能接受消息
    private Boolean isGameRunning;//游戏是否进行中
    private Boolean isShield;//是否屏蔽一键呼唤按钮
    private String userName;
    private String password;
    private int gameId;
    private Boolean isMySelf; //加入游戏后有给自己推送消息，自己的消息不用管
    private GlobalVeriable instance;
    private Boolean isStartChatBroad;//是否启动聊天广播的监听
    private String toChatingUser;//正在聊天的用户
    private int userLevel;//用户等级
    private Boolean isFirstReceiveChatRoomMessage;

    public Boolean getFirstReceiveChatRoomMessage() {
        return isFirstReceiveChatRoomMessage;
    }

    public void setFirstReceiveChatRoomMessage(Boolean firstReceiveChatRoomMessage) {
        isFirstReceiveChatRoomMessage = firstReceiveChatRoomMessage;
    }

    public GlobalVeriable getInstance() {
        return instance;
    }

    public boolean isPerfectInfo() {
        return isPerfectInfo;
    }

    public void setPerfectInfo(boolean perfectInfo) {
        isPerfectInfo = perfectInfo;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getGameRunning() {
        return isGameRunning;
    }

    public void setGameRunning(Boolean gameRunning) {
        isGameRunning = gameRunning;
    }

    public Boolean getLogin() {
        return isLogin;
    }

    public void setLogin(Boolean login) {
        isLogin = login;
    }

    public Boolean getRunnigMainBaidu() {
        return isRunnigMainBaidu;
    }

    public void setRunnigMainBaidu(Boolean runnigMainBaidu) {
        isRunnigMainBaidu = runnigMainBaidu;
    }

    public Boolean getGameFounder() {
        return isGameFounder;
    }

    public void setGameFounder(Boolean gameFounder) {
        isGameFounder = gameFounder;
    }

    public Boolean getReceiveMessage() {
        return isReceiveMessage;
    }

    public void setReceiveMessage(Boolean receiveMessage) {
        isReceiveMessage = receiveMessage;
    }

    public Boolean getShield() {
        return isShield;
    }

    public void setShield(Boolean shield) {
        isShield = shield;
    }

    public Boolean getMySelf() {
        return isMySelf;
    }

    public void setMySelf(Boolean mySelf) {
        isMySelf = mySelf;
    }

    public Boolean getRunningNewMessage() {
        return isRunningNewMessage;
    }

    public void setRunningNewMessage(Boolean runningNewMessage) {
        isRunningNewMessage = runningNewMessage;
    }

    public String getToChatingUser() {
        return toChatingUser;
    }

    public void setToChatingUser(String toChatingUser) {
        this.toChatingUser = toChatingUser;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public boolean isHaveMessage() {
        return haveMessage;
    }

    public void setHaveMessage(boolean haveMessage) {
        this.haveMessage = haveMessage;
    }

    @Override
    public void onCreate() {
        isLogin = false;
        isRunnigMainBaidu = false;
        isGameFounder = false;
        isReceiveMessage = false;
        isGameRunning = false;
        isRunningNewMessage = false;
        instance = this;
        isShield = true;
        isMySelf = false;
        isFirstReceiveChatRoomMessage = true;
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/upright_circle.TTF")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        getIsShield();
    }


    public void getIsShield() {
        SharedPreferences sp = getSharedPreferences("shield", Activity.MODE_PRIVATE);
        Boolean shield = sp.getBoolean("isShield", true);
        if (!shield) {
            isShield = false;
        }
    }

}
