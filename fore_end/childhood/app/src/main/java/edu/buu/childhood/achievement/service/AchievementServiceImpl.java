package edu.buu.childhood.achievement.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.buu.childhood.achievement.pojo.LikeDetail;
import edu.buu.childhood.achievement.pojo.UserEventList;
import edu.buu.childhood.achievement.pojo.UserHistoryGameList;
import edu.buu.childhood.achievement.pojo.UserKPI;
import edu.buu.childhood.achievement.pojo.UserMedalList;
import edu.buu.childhood.baidumap.pojo.InfoList;
import edu.buu.childhood.baidumap.pojo.MemberInfo;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.Message;
import edu.buu.childhood.common.Page;

/**
 * Created by lcc on 2016/7/15.
 */
public class AchievementServiceImpl implements AchievementService {
    Gson json = new Gson();

    public CallBackPage<UserKPI> getUserKPIHeadInf(String result) {
        Message messageCode = json.fromJson(result, new TypeToken<Message>() {
        }.getType());
        if (C.achvmt.GET_USER_KPI_SUCCESS.equals(messageCode.getMessageCode())) {
            Message<UserKPI> message = json.fromJson(result, new TypeToken<Message<UserKPI>>() {
            }.getType());
            CallBackPage<UserKPI> callBackPage = new CallBackPage<UserKPI>();
            List<UserKPI> list = new ArrayList<UserKPI>();
            UserKPI page = (UserKPI) message.getMessageContent();
            list.add(page);
            callBackPage.setDatalist(list);
            return callBackPage;
        }
        return null;
    }

    public CallBackPage<UserEventList> getUserFootPrintHeadInf(String result) {
        Message messageCode = json.fromJson(result, new TypeToken<Message>() {
        }.getType());
        if (C.achvmt.GET_USER_FOOT_PRINT_SUCCESS.equals(messageCode.getMessageCode())) {
            Message<Page<UserEventList>> message = json.fromJson(result, new TypeToken<Message<Page<UserEventList>>>() {
            }.getType());
            CallBackPage<UserEventList> callBackPage = new CallBackPage<UserEventList>();
            List<UserEventList> list = new ArrayList<UserEventList>();
            Page<UserEventList> page = message.getMessageContent();
            Iterator iter = page.getDataList().iterator();
            while (iter.hasNext()) {
                UserEventList userEventList = (UserEventList) iter.next();
                list.add(userEventList);
            }
            callBackPage.setPageSize(page.getPageSize());
            callBackPage.setDatalist(list);
            callBackPage.setTotalPage(page.getTotalPage());
            callBackPage.setCurrentPage(page.getCurrentPage());
            return callBackPage;
        }
        if (C.achvmt.GET_USER_FOOT_PRINT_UNSUCCESS.equals(messageCode.getMessageCode())) {

        }
        return null;

    }

    public CallBackPage<UserMedalList> getUserMedalListHeadInf(String result) {
        Message messageCode = json.fromJson(result, new TypeToken<Message>() {
        }.getType());
        if (C.achvmt.GET_USER_MEDAL_LIST_SUCCESS.equals(messageCode.getMessageCode())) {
            Message<List<UserMedalList>> message = json.fromJson(result, new TypeToken<Message<List<UserMedalList>>>() {
            }.getType());
            CallBackPage<UserMedalList> callBackPage = new CallBackPage<UserMedalList>();
            List<UserMedalList> list = new ArrayList<UserMedalList>();
            list = (List<UserMedalList>) message.getMessageContent();
            callBackPage.setDatalist(list);
            return callBackPage;
        }
        return null;
    }

    @Override
    public CallBackPage<UserHistoryGameList> getUserHistoryGameList(String result) {
        Message messageCode = json.fromJson(result, new TypeToken<Message>() {
        }.getType());
        if (C.achvmt.GET_USER_GAME_LIST_SUCCESS.equals(messageCode.getMessageCode())) {
            Message<Page<UserHistoryGameList>> message = json.fromJson(result, new TypeToken<Message<Page<UserHistoryGameList>>>() {
            }.getType());
            CallBackPage<UserHistoryGameList> callBackPage = new CallBackPage<UserHistoryGameList>();
            List<UserHistoryGameList> list = new ArrayList<UserHistoryGameList>();
            Page<UserHistoryGameList> page = message.getMessageContent();
            Iterator iter = page.getDataList().iterator();
            while (iter.hasNext()) {
                UserHistoryGameList userHistoryGameList = (UserHistoryGameList) iter.next();
                list.add(userHistoryGameList);
            }
            callBackPage.setFalg(C.achvmt.GET_USER_GAME_LIST_SUCCESS);
            callBackPage.setPageSize(page.getPageSize());
            callBackPage.setDatalist(list);
            callBackPage.setCurrentPage(page.getCurrentPage());
            callBackPage.setTotalPage(page.getTotalPage());
            return callBackPage;
        }
        return null;
    }

    @Override
    public CallBackPage<LikeDetail> getUserLikeList(String result) {
        Message messageCode = json.fromJson(result, new TypeToken<Message>() {
        }.getType());
        if (C.rank.LIKE_LIST_QUERY_SUCCESS.equals(messageCode.getMessageCode())) {
            Message<Page<LikeDetail>> message = json.fromJson(result, new TypeToken<Message<Page<LikeDetail>>>() {
            }.getType());
            Page<LikeDetail> page = message.getMessageContent();
            Iterator iterator = page.getDataList().iterator();
            List<LikeDetail> list = new ArrayList<>();
            while (iterator.hasNext()) {
                LikeDetail likeDetail = (LikeDetail) iterator.next();
                list.add(likeDetail);
            }
            CallBackPage<LikeDetail> callBackPage = new CallBackPage();
            callBackPage.setDatalist(list);
            callBackPage.setCurrentPage(page.getCurrentPage());
            callBackPage.setTotalPage(page.getTotalPage());
            callBackPage.setPageSize(page.getPageSize());
            return callBackPage;
        }
        return null;
    }

    @Override
    public CallBackPage<MemberInfo> getMemberInfo(String result) {
        Message messageCode = json.fromJson(result, new TypeToken<Message>() {
        }.getType());
        if (C.convene.GET_TEAM_MEMBERS_SUCCESS.equals(messageCode.getMessageCode())) {
            Message<InfoList<MemberInfo>> message = json.fromJson(result, new TypeToken<Message<InfoList<MemberInfo>>>() {
            }.getType());
            InfoList<MemberInfo> infoList = message.getMessageContent();
            CallBackPage<MemberInfo> callBackPage = new CallBackPage<>();
            callBackPage.setDatalist(infoList.getDataList());
            callBackPage.setFalg(C.convene.GET_TEAM_MEMBERS_SUCCESS);
            return callBackPage;
        }
        return null;
    }

    @Override
    public CallBackPage likeUser(String result) {
        Message<String> message = json.fromJson(result, new TypeToken<Message>() {
        }.getType());
        if (C.rank.LIKE_USER_SUCCESS.equals(message.getMessageCode())) {
            CallBackPage callBackPage = new CallBackPage();
            callBackPage.setFalg(C.rank.LIKE_USER_SUCCESS);
            return callBackPage;
        } else {
            CallBackPage callBackPage = new CallBackPage();
            callBackPage.setFalg(C.rank.LIKE_USER_UNSUCCESS);
            return callBackPage;
        }
    }

    @Override
    public CallBackPage gameSore(String result) {
        Message<String> message = json.fromJson(result, new TypeToken<Message>() {
        }.getType());
        if(C.onekey.SCORE_SUCCESS.equals(message.getMessageCode())){
            CallBackPage callBackPage = new CallBackPage();
            callBackPage.setFalg(C.onekey.SCORE_SUCCESS);
            return callBackPage;
        }
        return null;
    }


}