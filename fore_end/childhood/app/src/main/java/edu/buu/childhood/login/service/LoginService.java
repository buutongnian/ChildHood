package edu.buu.childhood.login.service;

import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.my.pojo.BackItem;
import edu.buu.childhood.my.pojo.UserInfo;

/**
 * Created by lcc on 2016/7/15.
 */
public interface LoginService {
    public CallBackPage<BackItem> getLoginHeadInf(String result);
    public String getLoginContentInf();
    public CallBackPage<UserInfo> getUserInfo(String result);
}