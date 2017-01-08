package edu.buu.childhood.baidumap.service;

import edu.buu.childhood.baidumap.pojo.GameInfo;
import edu.buu.childhood.baidumap.pojo.InfoList;
import edu.buu.childhood.baidumap.pojo.JoinedStatus;
import edu.buu.childhood.baidumap.pojo.MemberInfo;
import edu.buu.childhood.common.CallBackPage;

/**
 * Created by Administrator on 2016/9/2.
 */
public interface OnekeyService {
    public CallBackPage<JoinedStatus> isInGame(String result);
    public CallBackPage<GameInfo> getGameInfo(String result);
    public CallBackPage<InfoList<MemberInfo>> getMembersInfo(String result);
    public CallBackPage<String> gameScore(String result);
}
