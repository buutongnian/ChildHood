package edu.buu.childhood.common.service;

import edu.buu.childhood.common.CallBackPage;
import edu.buu.childhood.common.UserPersonalPage;

/**
 * Created by Administrator on 2016/10/6.
 */
public interface UserInfoService {
    public CallBackPage<edu.buu.childhood.my.pojo.UserInfo> getUserInfo(String result);
    public CallBackPage<UserPersonalPage> getUserPersionPage(String result);
}
