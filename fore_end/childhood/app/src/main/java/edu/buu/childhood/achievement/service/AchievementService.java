package edu.buu.childhood.achievement.service;

import edu.buu.childhood.achievement.pojo.LikeDetail;
import edu.buu.childhood.achievement.pojo.UserEventList;
import edu.buu.childhood.achievement.pojo.UserHistoryGameList;
import edu.buu.childhood.achievement.pojo.UserKPI;
import edu.buu.childhood.achievement.pojo.UserMedalList;
import edu.buu.childhood.baidumap.pojo.MemberInfo;
import edu.buu.childhood.common.CallBackPage;

/**
 * Created by lcc on 2016/7/15.
 */
public interface AchievementService {
    public CallBackPage<UserKPI> getUserKPIHeadInf(String result);
    public CallBackPage<UserEventList> getUserFootPrintHeadInf(String result);
    public CallBackPage<UserMedalList> getUserMedalListHeadInf(String result);
    public CallBackPage<UserHistoryGameList> getUserHistoryGameList(String result);
    public CallBackPage<LikeDetail> getUserLikeList(String result);
    public CallBackPage<MemberInfo> getMemberInfo(String result);
    public CallBackPage likeUser(String result);
    public CallBackPage gameSore(String result);
}