package edu.buu.childhood.baidumap.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import edu.buu.childhood.baidumap.pojo.GameInfBean;
import edu.buu.childhood.baidumap.pojo.GameInfo;
import edu.buu.childhood.baidumap.pojo.InfoList;
import edu.buu.childhood.baidumap.pojo.JoinedStatus;
import edu.buu.childhood.baidumap.pojo.MemberInfo;
import edu.buu.childhood.common.C;
import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.Message;

/**
 * Created by Administrator on 2016/9/2.
 */
public class OnekeyServiceImpl implements OnekeyService {
    @Override
    public CallBackPage<JoinedStatus> isInGame(String result) {
        Gson json = new Gson();
        Message message = json.fromJson(result, new TypeToken<Message>() {
        }.getType());
        if(C.def.USER_IN_GAME.equals(message.getMessageCode())){
            Message<JoinedStatus> message1 = json.fromJson(result, new TypeToken<Message<JoinedStatus>>() {
            }.getType());
            JoinedStatus status= (JoinedStatus) message1.getMessageContent();
            CallBackPage<JoinedStatus> callBackPage=new CallBackPage<JoinedStatus>();
            List list=new ArrayList();
            list.add(status);
            callBackPage.setSuccess(true);
            callBackPage.setDatalist(list);
            return callBackPage;
        }
        return null;
    }

    @Override
    public CallBackPage<GameInfo> getGameInfo(String result) {
        Gson json = new Gson();
        Message message = json.fromJson(result, new TypeToken<Message>() {
        }.getType());
        if(C.onekey.GET_GAME_INFO_SUCCESS.equals(message.getMessageCode())){
            CallBackPage<GameInfo> callBackPage=new CallBackPage<GameInfo>();
            Message<GameInfo> messageGameInf = json.fromJson(result, new TypeToken<Message<GameInfo>>() {
            }.getType());
            GameInfo gameInfo= (GameInfo) messageGameInf.getMessageContent();
            List list=new ArrayList();
            GameInfBean gameInfBean=new GameInfBean();
            gameInfBean.setGameId(gameInfo.getGameStatus().getGameId());
            gameInfBean.setGameFounderNickName(gameInfo.getUserInfo().getUserNickname());
            gameInfBean.setGameFounderName(gameInfo.getGameStatus().getGameFounder());
            gameInfBean.setGameName(gameInfo.getGameHead().getGameTitle());
            gameInfBean.setGamePlace(gameInfo.getGameStatus().getGatherPlace());
            gameInfBean.setGameSynopsis(gameInfo.getGameHead().getGameSynopsis());
            gameInfBean.setRecommendCount(gameInfo.getGameHead().getRecommendCount());
            gameInfBean.setGameStatus(gameInfo.getGameStatus().getGameStatus());
            list.add(gameInfBean);
            callBackPage.setFalg("gameInfo");
            callBackPage.setDatalist(list);
            return callBackPage;
        }
        return null;
    }

    @Override
    public CallBackPage<InfoList<MemberInfo>> getMembersInfo(String result) {
        Gson json=new Gson();
        Message message=json.fromJson(result, new TypeToken<Message>() {}.getType());
        if(C.convene.GET_TEAM_MEMBERS_SUCCESS.equals(message.getMessageCode())){
            Message<InfoList<MemberInfo>> membersMessage=json.fromJson(result, new TypeToken<Message<InfoList<MemberInfo>>>() {}.getType());
            InfoList<MemberInfo> infoList= (InfoList<MemberInfo>) membersMessage.getMessageContent();
           if(((InfoList<MemberInfo>)membersMessage.getMessageContent()).count>0){
               CallBackPage<InfoList<MemberInfo>> callBackPage=new CallBackPage<>();
               List list=new ArrayList();
               list.add(membersMessage.getMessageContent());
               callBackPage.setDatalist(list);
                return callBackPage;
            }
        }
        return null;
    }

    @Override
    public CallBackPage<String> gameScore(String result) {
        Gson json=new Gson();
        Message message=json.fromJson(result, new TypeToken<Message>() {}.getType());
        if(C.onekey.SCORE_SUCCESS.equals(message.getMessageCode())){
            CallBackPage<String > callBackPage=new CallBackPage<>();
            callBackPage.setFalg("scoreSuccess");
            return callBackPage;
        }
        return null;
    }
}
